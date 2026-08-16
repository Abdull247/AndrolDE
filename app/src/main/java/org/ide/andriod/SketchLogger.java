package org.ide.andriod;

import android.content.Intent;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.apk.builder.ApplicationLoader;

public class SketchLogger {
    private static Thread loggerThread = new Thread() {
        @Override
        public void run() {
            isRunning = true;

            try {
                Runtime.getRuntime().exec("logcat -c");
                Process process = Runtime.getRuntime().exec("logcat");

                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String logTxt = bufferedReader.readLine();
                    do {
                        broadcastLog(logTxt);
                    } while (isRunning && ((logTxt = bufferedReader.readLine()) != null));

                    if (isRunning) {
                        broadcastLog("Logger got killed. Restarting.");
                        startLogging();
                    } else {
                        broadcastLog("Logger stopped.");
                    }
                }
            } catch (Exception e) {
                broadcastLog(e.toString());
            }
        }
    };

    private static volatile boolean isRunning = false;

    public static void startLogging() {
        if (!isRunning) {
            loggerThread.start();
        } else {
            throw new IllegalStateException("Logger already running");
        }
    }

    public static void broadcastLog(String log) {
        Context context = ApplicationLoader.applicationContext;

        if (context == null) {
            Log.e("SketchLogger", "Context is null, cannot broadcast log.");
            return;  // Exit early if context is null to avoid NullPointerException
        }

        Intent intent = new Intent();
        intent.setAction("com.sketchware.remod.ACTION_NEW_DEBUG_LOG");
        intent.putExtra("log", log);
        intent.putExtra("packageName", context.getPackageName());

        context.sendBroadcast(intent);
    }

    public static void stopLogging() {
        if (isRunning) {
            isRunning = false;
            broadcastLog("Stopping logger by user request.");
        } else {
            throw new IllegalStateException("Logger not running");
        }
    }
}