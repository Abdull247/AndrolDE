package org.ide.andriod;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apk.builder.FileUtil;
import com.apk.builder.logger.Log;
import com.apk.builder.logger.LogAdapter;
import com.apk.builder.logger.Logger;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BuildLogsActivity extends AppCompatActivity {

	private RecyclerView recyclerView;
	private LinearLayoutManager layoutManager;
	private final List<Log> logData = new ArrayList<>();
	private LogAdapter adapter;
	private View statusBar;
	private ImageView liveDot;
	private TextView emptyView;
	private ObjectAnimator pulseAnimator;

	private Logger logger;
	private final Logger.OnLogLineListener lineListener = log -> {
		runOnUiThread(() -> {
			if (adapter != null) {
				logData.add(log);
				adapter.notifyItemInserted(logData.size() - 1);
				recyclerView.smoothScrollToPosition(logData.size() - 1);
			}
		});
	};

	private final BuildHelper.BuildStateListener stateListener = (success, message) ->
		runOnUiThread(() -> hideLiveStatus());

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.build_logs);
		overridePendingTransition(R.anim.slide_up_bounce_sheet, R.anim.slide_in_enter);

		recyclerView = findViewById(R.id.build_logs_recycler);
		statusBar = findViewById(R.id.build_logs_status);
		liveDot = findViewById(R.id.build_logs_live_dot);
		emptyView = findViewById(R.id.build_logs_empty);

		layoutManager = new LinearLayoutManager(this);
		layoutManager.setStackFromEnd(true);
		recyclerView.setLayoutManager(layoutManager);
		adapter = new LogAdapter(logData);
		recyclerView.setAdapter(adapter);

		findViewById(R.id.build_logs_close).setOnClickListener(v -> finish());
		findViewById(R.id.build_logs_history).setOnClickListener(v -> showLogSelector());

		BuildHelper active = BuildHelper.getActiveBuild();
		if (active != null) {
			logger = active.getLogger();
			if (logger != null) {
				logData.addAll(logger.getLogList());
				adapter.notifyDataSetChanged();
			}
		}

		if (BuildHelper.isBuildRunning()) {
			showLiveStatus();
			if (logger != null) {
				logger.addLogLineListener(lineListener);
			}
		} else if (logData.isEmpty()) {
			loadLatestSavedLog();
		}

		BuildHelper.addBuildStateListener(stateListener);

		updateEmptyView();
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.slide_in_enter, R.anim.slide_down_sheet);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (pulseAnimator != null) {
			pulseAnimator.cancel();
		}
		if (logger != null) {
			logger.removeLogLineListener(lineListener);
		}
		BuildHelper.removeBuildStateListener(stateListener);
	}

	private void showLiveStatus() {
		statusBar.setVisibility(View.VISIBLE);
		if (pulseAnimator == null) {
			pulseAnimator = ObjectAnimator.ofFloat(liveDot, "alpha", 1f, 0.25f, 1f);
			pulseAnimator.setDuration(900);
			pulseAnimator.setRepeatCount(ObjectAnimator.INFINITE);
			pulseAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
		}
		if (!pulseAnimator.isStarted() && !pulseAnimator.isRunning()) {
			pulseAnimator.start();
		}
	}

	private void hideLiveStatus() {
		statusBar.setVisibility(View.GONE);
		if (pulseAnimator != null) {
			pulseAnimator.cancel();
		}
	}

	private void updateEmptyView() {
		emptyView.setVisibility(logData.isEmpty() ? View.VISIBLE : View.GONE);
	}

	private void loadLatestSavedLog() {
		List<SavedLog> logs = readSavedLogs();
		if (logs.isEmpty()) {
			return;
		}
		loadSavedLog(logs.get(0).logPath);
	}

	private List<SavedLog> readSavedLogs() {
		List<SavedLog> result = new ArrayList<>();
		String registryPath = getIntent().getStringExtra("data");
		if (registryPath == null) {
			registryPath = getProjectDataPath();
		}
		registryPath = registryPath.concat("logs_data.json");
		try {
			String json = FileUtil.readFile(registryPath);
			if (json == null || json.trim().isEmpty()) {
				return result;
			}
			JSONArray arr = new JSONArray(json);
			for (int i = 0; i < arr.length(); i++) {
				JSONObject o = arr.getJSONObject(i);
				SavedLog entry = new SavedLog();
				entry.logId = o.optString("log_id", "");
				entry.logPath = o.optString("log_path", "");
				entry.logTime = o.optLong("log_time", 0L);
				if (!entry.logPath.isEmpty() && FileUtil.isExistFile(entry.logPath)) {
					result.add(entry);
				}
			}
			Collections.sort(result, (a, b) -> Long.compare(b.logTime, a.logTime));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private String getProjectDataPath() {
		String data = getIntent().getStringExtra("data");
		if (data != null) {
			return data;
		}
		return "/storage/emulated/0/.androIDE/data/".concat(
				getIntent().getStringExtra("name") != null ? getIntent().getStringExtra("name") : "").concat("/");
	}

	private void loadSavedLog(String path) {
		if (path == null || !FileUtil.isExistFile(path)) {
			Toast.makeText(this, "Log file not found", Toast.LENGTH_SHORT).show();
			return;
		}
		List<Log> loaded = new ArrayList<>();
		try {
			String content = FileUtil.readFile(path);
			if (content == null) {
				return;
			}
			int marker = content.indexOf("\n\n--- Build Result ---\n");
			String jsonPart = marker >= 0 ? content.substring(0, marker) : content;
			JSONArray arr = new JSONArray(jsonPart.trim());
			for (int i = 0; i < arr.length(); i++) {
				JSONObject o = arr.getJSONObject(i);
				loaded.add(new Log(o.optString("tag", ""), o.optString("message", "")));
			}
			if (marker >= 0) {
				String footer = content.substring(marker + 2).trim();
				if (!footer.isEmpty()) {
					loaded.add(new Log("RESULT", footer));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logData.clear();
		logData.addAll(loaded);
		adapter.notifyDataSetChanged();
		updateEmptyView();
		if (!loaded.isEmpty()) {
			recyclerView.scrollToPosition(loaded.size() - 1);
		}
	}

	private void showLogSelector() {
		final List<SavedLog> saved = readSavedLogs();
		final boolean running = BuildHelper.isBuildRunning();

		if (!running && saved.isEmpty()) {
			Toast.makeText(this, "No saved build logs", Toast.LENGTH_SHORT).show();
			return;
		}

		final List<Object> items = new ArrayList<>();
		if (running) {
			items.add("CURRENT");
		}
		items.addAll(saved);

		final ListView listView = new ListView(this);
		listView.setDivider(null);
		listView.setDividerHeight(0);

		final BaseAdapter listAdapter = new BaseAdapter() {
			@Override
			public int getCount() {
				return items.size();
			}

			@Override
			public Object getItem(int position) {
				return items.get(position);
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View row = getLayoutInflater().inflate(R.layout.item_log_selector, parent, false);
				ImageView icon = row.findViewById(R.id.log_selector_icon);
				TextView title = row.findViewById(R.id.log_selector_title);
				TextView subtitle = row.findViewById(R.id.log_selector_subtitle);
				TextView status = row.findViewById(R.id.log_selector_status);

				Object item = items.get(position);
				if (item instanceof String) {
					icon.setImageResource(R.drawable.ic_live_dot);
					icon.setColorFilter(0xFF4CAF50);
					title.setText("Current build");
					subtitle.setText("Streaming live...");
					status.setText("");
				} else {
					SavedLog entry = (SavedLog) item;
					icon.setImageResource(R.drawable.ic_history);
					icon.clearColorFilter();
					title.setText("Log " + entry.logId);
					subtitle.setText(formatTime(entry.logTime));
					status.setText("");
				}
				return row;
			}
		};
		listView.setAdapter(listAdapter);

		listView.setOnItemClickListener((parent, view, position, id) -> {
			Object item = items.get(position);
			if (!(item instanceof String)) {
				loadSavedLog(((SavedLog) item).logPath);
			}
		});

		new MaterialAlertDialogBuilder(this)
				.setTitle("Build Logs")
				.setView(listView)
				.setPositiveButton("Close", null)
				.show();
	}

	private String formatTime(long millis) {
		if (millis <= 0) {
			return "";
		}
		return new SimpleDateFormat("MMM d, h:mm a", Locale.getDefault()).format(new Date(millis));
	}

	private static class SavedLog {
		String logId;
		String logPath;
		long logTime;
	}
}
