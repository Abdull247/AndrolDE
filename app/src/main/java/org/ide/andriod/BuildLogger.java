package org.ide.andriod;

import com.apk.builder.FileUtil;
import com.apk.builder.logger.Logger;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;

public class BuildLogger {

	private String projectDataPath;

	public BuildLogger(String _projectDataPath) {
		projectDataPath = _projectDataPath;
	}

	/**
	 * Writes a single build log entry once a build finishes. Waits for
	 * Logger's message queue to flush via getLogDataWhenReady() so the
	 * captured log is complete and in order, then writes:
	 *   - /data/<project>/logs/log_<id>  (raw JSON log + final result)
	 *   - an appended entry in logs_data.json (log_id, log_path, log_time)
	 */
	public void writeLog(Logger logger, final boolean success, final String resultMessage) {
		if (logger == null) {
			writeLogInternal("", success, resultMessage);
			return;
		}
		logger.getLogDataWhenReady(logDataJson -> {
			writeLogInternal(logDataJson, success, resultMessage);
		});
	}

	private void writeLogInternal(String logDataJson, boolean success, String resultMessage) {
		try {
			String logsDir = projectDataPath.concat("logs/");
			if (!FileUtil.isExistFile(logsDir)) {
				FileUtil.makeDir(logsDir);
			}

			String logId = generateLogId(logsDir);
			String logPath = logsDir.concat("log_").concat(logId);

			String fullLog = logDataJson
				.concat("\n\n--- Build Result ---\n")
				.concat(success ? "SUCCESS: " : "FAILED: ")
				.concat(resultMessage != null ? resultMessage : "");

			FileUtil.writeFile(logPath, fullLog);

			appendLogEntry(logId, logPath);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String generateLogId(String logsDir) {
		java.util.Random random = new java.util.Random();
		String id;
		do {
			int base = 10000000 + random.nextInt(90000000);
			id = String.valueOf(base);
		} while (FileUtil.isExistFile(logsDir.concat("log_").concat(id)));
		return id;
	}

	private void appendLogEntry(String logId, String logPath) {
		String registryPath = projectDataPath.concat("logs_data.json");

		ArrayList<HashMap<String, Object>> logs = readExistingLogs(registryPath);

		HashMap<String, Object> entry = new HashMap<>();
		entry.put("log_id", logId);
		entry.put("log_path", logPath);
		entry.put("log_time", System.currentTimeMillis());
		logs.add(entry);

		FileUtil.writeFile(registryPath, new Gson().toJson(logs));
	}

	private ArrayList<HashMap<String, Object>> readExistingLogs(String registryPath) {
		try {
			if (!FileUtil.isExistFile(registryPath)) {
				return new ArrayList<>();
			}
			String json = FileUtil.readFile(registryPath);
			if (json == null || json.trim().isEmpty()) {
				return new ArrayList<>();
			}
			java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType();
			ArrayList<HashMap<String, Object>> logs = new Gson().fromJson(json, type);
			return logs != null ? logs : new ArrayList<>();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
}