package com.apk.builder.logger;

import android.text.style.ForegroundColorSpan;
import android.text.Spannable;
import android.text.SpannableString;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.os.Looper;
import org.json.JSONArray;
import org.json.JSONObject;

public class Logger {
	
	private LogAdapter adapter;
	private LinearLayoutManager layoutManager;
	private List<Log> data = new ArrayList<>();
	private List<Log> error_log = new ArrayList<>();
    
	private RecyclerView mRecyclerView;
	private boolean mAttached;
	private String tag_tag;
	private String message_message;

	private final List<OnLogLineListener> lineListeners = new ArrayList<>();
	private final Handler mainHandler = new Handler(Looper.getMainLooper());
	
	public interface OnLogLineListener {
		void onLogLine(Log log);
	}
	
	public interface OnLogDataReadyListener {
		void onReady(String logDataJson);
	}
	
	public void attach(RecyclerView view) {
		mRecyclerView = view;
		init();
	}
	
	public void detach() {
		mRecyclerView = null;
		mAttached = false;
		adapter = null;
	}
	
	private void init() {
		adapter = new LogAdapter(data);
	    layoutManager = new LinearLayoutManager(mRecyclerView.getContext());
		layoutManager.setStackFromEnd(true);
		mRecyclerView.setLayoutManager(layoutManager);
		mRecyclerView.setAdapter(adapter);
		mAttached = true;
	}
	
	public void addLogLineListener(OnLogLineListener listener) {
		synchronized (lineListeners) {
			if (!lineListeners.contains(listener)) {
				lineListeners.add(listener);
			}
		}
	}
	
	public void removeLogLineListener(OnLogLineListener listener) {
		synchronized (lineListeners) {
			lineListeners.remove(listener);
		}
	}
	
	public void d(String tag, String message) {
		final Log log = new Log(tag, message);
		synchronized (data) {
			data.add(log);
		}
		postToRecycler(log);
		notifyLineListeners(log);
	}
	
	public void e(String tag,  String message) {
		Spannable messageSpan = new SpannableString(message);
		messageSpan.setSpan(new ForegroundColorSpan(0xffff0000), 0, message.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		final Log log = new Log(tag, messageSpan);
		synchronized (data) {
			data.add(log);
		}
		postToRecycler(log);
		notifyLineListeners(log);
	}
	
	public void w(String tag,  String message) {
		Spannable messageSpan = new SpannableString(message);
		messageSpan.setSpan(new ForegroundColorSpan(0xffff7043), 0, message.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		final Log log = new Log(tag, messageSpan);
		synchronized (data) {
			data.add(log);
			error_log.add(log);
		}
		postToRecycler(log);
		notifyLineListeners(log);
	}
	
	private void postToRecycler(final Log log) {
		if (mRecyclerView == null || adapter == null) {
			return;
		}
		mRecyclerView.post(() -> {
			adapter.notifyItemInserted(data.size() - 1);
			scroll();
		});
	}
	
	private void notifyLineListeners(final Log log) {
		synchronized (lineListeners) {
			for (final OnLogLineListener l : lineListeners) {
				mainHandler.post(() -> l.onLogLine(log));
			}
		}
	}
	
	private void scroll() {
		if (mRecyclerView != null) {
			mRecyclerView.smoothScrollToPosition(data.size() - 1);
		}
	}
	
	public String getCurrentTag() {
		return tag_tag;
	}

	public String getCurrentMessage() {
		return message_message;
	}

	public List<Log> getLogList() {
		synchronized (data) {
			return new ArrayList<>(data);
		}
	}

	public String getLogData() {
		return buildCleanJson();
	}

	public String getErrorLogData() {
		return buildCleanJson(error_log);
	}

	/**
	 * Posts onto the main looper queue, guaranteeing this runs after every
	 * log line already queued at call time. Use this instead of getLogData()
	 * when you need a complete, ordered snapshot right after a build finishes.
	 */
	public void getLogDataWhenReady(OnLogDataReadyListener listener) {
		mainHandler.post(() -> {
			listener.onReady(buildCleanJson());
		});
	}

	/**
	 * Serializes the logs to a clean [{tag,message},...] array so that
	 * saved log files can be parsed and re-rendered with highlighting later.
	 */
	private String buildCleanJson() {
		return buildCleanJson(data);
	}

	private String buildCleanJson(List<Log> source) {
		try {
			JSONArray arr = new JSONArray();
			List<Log> snapshot;
			synchronized (data) {
				snapshot = new ArrayList<>(source);
			}
			for (Log l : snapshot) {
				JSONObject o = new JSONObject();
				o.put("tag", l.getTag() == null ? "" : l.getTag());
				o.put("message", l.getMessage() == null ? "" : l.getMessage().toString());
				arr.put(o);
			}
			return arr.toString();
		} catch (Exception e) {
			return "[]";
		}
	}

	public void clearLog() {
		synchronized (data) {
			data.clear();
		}
	}

	public void clearErrorLog() {
		synchronized (error_log) {
			error_log.clear();
		}
	}
}
