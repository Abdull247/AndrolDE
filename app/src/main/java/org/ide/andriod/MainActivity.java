package org.ide.andriod;

import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.Intent;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.os.Bundle;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.*;
import androidx.appcompat.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.*;
import androidx.constraintlayout.widget.*;
import androidx.coordinatorlayout.*;
import androidx.core.*;
import androidx.cursoradapter.*;
import androidx.customview.*;
import androidx.documentfile.*;
import androidx.drawerlayout.*;
import androidx.fragment.*;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.interpolator.*;
import androidx.legacy.coreutils.*;
import androidx.lifecycle.*;
import androidx.lifecycle.livedata.*;
import androidx.lifecycle.livedata.core.*;
import androidx.lifecycle.viewmodel.*;
import androidx.loader.*;
import androidx.localbroadcastmanager.*;
import androidx.vectordrawable.*;
import androidx.versionedparcelable.*;
import androidx.viewpager.*;
import com.google.gson.*;
import java.io.*;
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;


public class MainActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	
	private LinearLayout linear1;
	private ImageView imageview1;
	
	private TimerTask t;
	private Intent i = new Intent();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		imageview1 = findViewById(R.id.imageview1);
	}
	
	private void initializeLogic() {
		getWindow().setStatusBarColor(Color.TRANSPARENT);
		getWindow().setNavigationBarColor(Color.TRANSPARENT);
		if (FileUtil.isExistFile("/storage/emulated/0/.androIDE/.nomedia")) {
			t = new TimerTask() {
				@Override
				public void run() {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							i.setClass(getApplicationContext(), MinimalMainActivity.class);
							startActivity(i);
							finish();
						}
					});
				}
			};
			_timer.schedule(t, 2000);
		} else {
			// Create the .nomedia file
			FileUtil.writeFile("/storage/emulated/0/.androIDE/.nomedia", "");
			
			// Ensure required folders exist
			String[] folders = {
				"/storage/emulated/0/.androIDE/backups",
				"/storage/emulated/0/.androIDE/collection",
				"/storage/emulated/0/.androIDE/data",
				"/storage/emulated/0/.androIDE/system",
				"/storage/emulated/0/.androIDE/logs",
				"/storage/emulated/0/.androIDE/mysc",
				"/storage/emulated/0/.androIDE/resources",
				"/storage/emulated/0/.androIDE/temp",
				"/storage/emulated/0/.androIDE/libs"
			};
			
			for (String path : folders) {
				if (!FileUtil.isExistFile(path)) {
					FileUtil.makeDir(path);
				}
			}
			
			// Build the setup dialog with an indeterminate progress indicator
			final com.google.android.material.progressindicator.LinearProgressIndicator _setupProgress =
			new com.google.android.material.progressindicator.LinearProgressIndicator(MainActivity.this);
			_setupProgress.setIndeterminate(true);
			
			int _pad = (int) (24 * getResources().getDisplayMetrics().density);
			_setupProgress.setPadding(_pad, _pad, _pad, _pad);
			
			final MaterialAlertDialogBuilder _setupDialogBuilder = new MaterialAlertDialogBuilder(MainActivity.this);
			_setupDialogBuilder.setTitle("Setting up resources");
			_setupDialogBuilder.setView(_setupProgress);
			_setupDialogBuilder.setCancelable(false);
			
			final androidx.appcompat.app.AlertDialog _setupDialog = _setupDialogBuilder.create();
			_setupDialog.show();
			
			// Run the copy off the main thread so the indicator can actually animate
			new Thread(new Runnable() {
				@Override
				public void run() {
					_copySharedLibsFromAssets();
					
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							_setupDialog.dismiss();
							
							t = new TimerTask() {
								@Override
								public void run() {
									runOnUiThread(new Runnable() {
										@Override
										public void run() {
											i.setClass(getApplicationContext(), MinimalMainActivity.class);
											startActivity(i);
											finish();
										}
									});
								}
							};
							_timer.schedule(t, 2000);
						}
					});
				}
			}).start();
		}
	}
	
	public void _anchore() {
		
	}
    
    /**
 * Copies every library folder from assets/libs/ into the shared
 * /storage/emulated/0/.androIDE/libs/ directory (once), then writes
 * libs_data.json describing each copied library's name + path.
 *
 * Uses AssetManager.list() to walk assets/libs/<lib_name>/... recursively,
 * since assets don't support File-style directory listing.
 */
		private void _copySharedLibsFromAssets() {
			try {
				final String sharedLibsRoot = "/storage/emulated/0/.androIDE/libs/";
				android.content.res.AssetManager assetManager = getAssets();
				
				String[] libNames = assetManager.list("libs");
				if (libNames == null || libNames.length == 0) {
					// Nothing bundled, still write an empty registry so downstream code doesn't crash
					FileUtil.writeFile(sharedLibsRoot + "libs_data.json", "[]");
					return;
				}
				
				org.json.JSONArray libsData = new org.json.JSONArray();
				
				for (String libName : libNames) {
					String assetLibPath = "libs/" + libName;
					String destLibPath = sharedLibsRoot + libName;
					
					// Skip if this library was already copied in a previous run
					if (!FileUtil.isExistFile(destLibPath)) {
						FileUtil.makeDir(destLibPath);
						_copyAssetFolderRecursive(assetManager, assetLibPath, destLibPath);
					}
					
					org.json.JSONObject entry = new org.json.JSONObject();
					entry.put("lib_name", libName);
					entry.put("lib_path", destLibPath);
					libsData.put(entry);
				}
				
				FileUtil.writeFile(sharedLibsRoot + "libs_data.json", libsData.toString());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/** Recursively copies an assets/ subfolder to a destination path on disk. */
		private void _copyAssetFolderRecursive(android.content.res.AssetManager assetManager, String assetPath, String destPath) throws java.io.IOException {
			String[] children = assetManager.list(assetPath);
			
			if (children == null || children.length == 0) {
				// It's a file, not a folder
				try (java.io.InputStream in = assetManager.open(assetPath);
				java.io.OutputStream out = new java.io.FileOutputStream(destPath)) {
					byte[] buffer = new byte[8096];
					int count;
					while ((count = in.read(buffer)) != -1) {
						out.write(buffer, 0, count);
					}
				}
				return;
			}
			
			FileUtil.makeDir(destPath);
			for (String child : children) {
				_copyAssetFolderRecursive(assetManager, assetPath + "/" + child, destPath + "/" + child);
			}
		}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}
