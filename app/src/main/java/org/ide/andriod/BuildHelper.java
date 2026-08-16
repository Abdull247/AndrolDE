package org.ide.andriod;

import android.content.Context;
import com.apk.builder.FileUtil;
import com.apk.builder.logger.Logger;
import com.apk.builder.model.Library;
import com.apk.builder.model.Project;
import com.apk.builder.SystemLogPrinter;
import com.google.gson.Gson;
import com.tyron.compiler.CompilerAsyncTask;
import java.io.File;
import java.util.HashMap;

public class BuildHelper {

	private static BuildHelper activeBuild;
	private static boolean buildRunning = false;
	private static final java.util.List<BuildStateListener> stateListeners = new java.util.ArrayList<>();

	public interface BuildStateListener {
		void onBuildFinished(boolean success, String message);
	}

	public static void addBuildStateListener(BuildStateListener l) {
		synchronized (stateListeners) {
			if (!stateListeners.contains(l)) {
				stateListeners.add(l);
			}
		}
	}

	public static void removeBuildStateListener(BuildStateListener l) {
		synchronized (stateListeners) {
			stateListeners.remove(l);
		}
	}

	private static void notifyBuildFinished(boolean success, String message) {
		synchronized (stateListeners) {
			for (BuildStateListener l : stateListeners) {
				try {
					l.onBuildFinished(success, message);
				} catch (Exception ignored) {
				}
			}
		}
	}

	public static BuildHelper getActiveBuild() {
		return activeBuild;
	}

	public static boolean isBuildRunning() {
		return buildRunning;
	}

	public interface BuildListener {
		void onBuildStarted();
		void onBuildSuccess(String apkPath);
		void onBuildFailed(String errorMessage);
	}

	private Context context;
	private String projectPath = "";
	private String projectDataPath = "";
	private Logger mLogger = new Logger();
	private BuildListener listener;
	private BuildLogger buildLogger;

	private String minSdk = "";
	private String maxSdk = "";
	private String versionCode = "";
	private String versionName = "";
	private String resPath = "";
	private String javaPath = "";
	private String manifestPath = "";
	private String assetsPath = "";
	private String nativeLibsPath = "";
	private String localLibsPath = "";
	private String et_output = "";
	private String packageName = "";

	public BuildHelper(Context _context, String _projectPath, String _projectDataPath) {
		context = _context;
		projectPath = _projectPath;
		projectDataPath = _projectDataPath;
		buildLogger = new BuildLogger(_projectDataPath);
	}

	public void setBuildListener(BuildListener _listener) {
		listener = _listener;
	}

	public Logger getLogger() {
		return mLogger;
	}

	public void startBuild() {
		activeBuild = this;
		buildRunning = true;
		if (listener != null) {
			listener.onBuildStarted();
		}

		FileUtil.deleteFile(projectPath.concat("/app/build/bin/"));
		FileUtil.deleteFile(projectPath.concat("/app/build/gen/"));

		_readAppConfig();

		if (minSdk.isEmpty() || maxSdk.isEmpty() || versionCode.isEmpty()) {
			if (listener != null) {
				listener.onBuildFailed("app_config.json missing or incomplete at " + projectDataPath.concat("app_config.json"));
			}
			return;
		}

		_resolveProjectPaths();
		_runCompiler();
	}

	private void _readAppConfig() {
		try {
			String _configPath = projectDataPath.concat("app_config.json");
			if (!FileUtil.isExistFile(_configPath)) {
				return;
			}

			String _jsonStr = FileUtil.readFile(_configPath);
			if (_jsonStr == null || _jsonStr.trim().isEmpty()) return;

			java.lang.reflect.Type _type = new com.google.gson.reflect.TypeToken<HashMap<String, Object>>(){}.getType();
			HashMap<String, Object> _config = new Gson().fromJson(_jsonStr, _type);

			if (_config == null) return;

			if (_config.containsKey("package") && _config.get("package") != null) {
				packageName = _config.get("package").toString();
			}
			if (_config.containsKey("minSdkVersion") && _config.get("minSdkVersion") != null) {
				minSdk = String.valueOf((long) Double.parseDouble(_config.get("minSdkVersion").toString()));
			}
			if (_config.containsKey("targetSdkVersion") && _config.get("targetSdkVersion") != null) {
				maxSdk = String.valueOf((long) Double.parseDouble(_config.get("targetSdkVersion").toString()));
			}
			if (_config.containsKey("versionCode") && _config.get("versionCode") != null) {
				versionCode = String.valueOf((long) Double.parseDouble(_config.get("versionCode").toString()));
			}
			if (_config.containsKey("versionName") && _config.get("versionName") != null) {
				versionName = _config.get("versionName").toString();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void _resolveProjectPaths() {
		if (FileUtil.isDirectory(projectPath.concat("/app/src/main/res/"))) {
			resPath = projectPath.concat("/app/src/main/res/");
		}
		if (FileUtil.isDirectory(projectPath.concat("/app/src/main/java/"))) {
			javaPath = projectPath.concat("/app/src/main/java/");
		}
		if (FileUtil.isFile(projectPath.concat("/app/src/main/AndroidManifest.xml"))) {
			manifestPath = projectPath.concat("/app/src/main/AndroidManifest.xml");
		}
		if (FileUtil.isDirectory(projectPath.concat("/app/src/main/assets/"))) {
			assetsPath = projectPath.concat("/app/src/main/assets/");
		}
		if (FileUtil.isDirectory(projectPath.concat("/app/src/main/jni/"))) {
			nativeLibsPath = projectPath.concat("/app/src/main/jni/");
		}
		localLibsPath = projectDataPath.concat("local_libs.json");
		et_output = projectPath.concat("/app/build/");
	}

	private void _runCompiler() {
		try {
			SystemLogPrinter.start(mLogger);

			Project _project = new Project();
			_project.setLibraries(Library.fromLibsJson(new File(localLibsPath)));
			_project.setResourcesFile(new File(resPath));
			_project.setOutputFile(new File(et_output));
			_project.setJavaFile(new File(javaPath));
			_project.setManifestFile(new File(manifestPath));

			if (!assetsPath.isEmpty()) {
				_project.setAssetsFile(new File(assetsPath));
			}

			_project.setVersionName(versionName);
			_project.setLogger(mLogger);
			_project.setMinSdk(Integer.parseInt(minSdk));
			_project.setTargetSdk(Integer.parseInt(maxSdk));
			_project.setVersionCode(Integer.parseInt(versionCode));

			CompilerAsyncTask _task = new CompilerAsyncTask(context);
			_task.setOnBuildCompleteListener(new CompilerAsyncTask.OnBuildCompleteListener() {
				@Override
				public void onBuildSuccess(String apkPath) {
					buildRunning = false;
					buildLogger.writeLog(mLogger, true, apkPath);
					notifyBuildFinished(true, apkPath);
					if (listener != null) {
						listener.onBuildSuccess(apkPath);
					}
				}

				@Override
				public void onBuildFailed(String errorMessage) {
					buildRunning = false;
					buildLogger.writeLog(mLogger, false, errorMessage);
					notifyBuildFinished(false, errorMessage);
					if (listener != null) {
						listener.onBuildFailed(errorMessage);
					}
				}
			});
			_task.execute(_project);

		} catch (Exception e) {
			e.printStackTrace();
			if (listener != null) {
				listener.onBuildFailed(e.getMessage());
			}
		}
	}
}