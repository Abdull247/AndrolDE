package org.ide.andriod;

import android.Manifest;
import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.*;
import androidx.constraintlayout.widget.*;
import androidx.coordinatorlayout.*;
import androidx.core.*;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;


public class MinimalMainActivity extends AppCompatActivity {
	
	public final int REQ_CD_F = 101;
	
	private Timer _timer = new Timer();
	
	private double num = 0;
	
	private ArrayList<HashMap<String, Object>> darray = new ArrayList<>();
	
	private LinearLayout linear1;
	private TextView textview1;
	private LinearLayout linear2;
	private ImageView imageview1;
	private TextView textview2;
	private TextView textview3;
	private LinearLayout linear8;
	private LinearLayout linear10;
	private LinearLayout linear9;
	private LinearLayout linear11;
	private LinearLayout linear3;
	private LinearLayout linear5;
	private LinearLayout linear13;
	private LinearLayout linear6;
	private LinearLayout linear12;
	private LinearLayout linear7;
	private ImageView imageview2;
	private Button button1;
	private ImageView imageview3;
	private Button button2;
	private ImageView imageview7;
	private Button button6;
	private ImageView imageview4;
	private Button button3;
	private ImageView imageview6;
	private Button button5;
	private ImageView imageview5;
	private Button button4;
	
	private Intent i = new Intent();
	private TimerTask t;
	private AlertDialog.Builder d;
	private Intent f = new Intent(Intent.ACTION_GET_CONTENT);
	private SharedPreferences s;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.minimal_main);
		initialize(_savedInstanceState);
		
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
		} else {
			initializeLogic();
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		textview1 = findViewById(R.id.textview1);
		linear2 = findViewById(R.id.linear2);
		imageview1 = findViewById(R.id.imageview1);
		textview2 = findViewById(R.id.textview2);
		textview3 = findViewById(R.id.textview3);
		linear8 = findViewById(R.id.linear8);
		linear10 = findViewById(R.id.linear10);
		linear9 = findViewById(R.id.linear9);
		linear11 = findViewById(R.id.linear11);
		linear3 = findViewById(R.id.linear3);
		linear5 = findViewById(R.id.linear5);
		linear13 = findViewById(R.id.linear13);
		linear6 = findViewById(R.id.linear6);
		linear12 = findViewById(R.id.linear12);
		linear7 = findViewById(R.id.linear7);
		imageview2 = findViewById(R.id.imageview2);
		button1 = findViewById(R.id.button1);
		imageview3 = findViewById(R.id.imageview3);
		button2 = findViewById(R.id.button2);
		imageview7 = findViewById(R.id.imageview7);
		button6 = findViewById(R.id.button6);
		imageview4 = findViewById(R.id.imageview4);
		button3 = findViewById(R.id.button3);
		imageview6 = findViewById(R.id.imageview6);
		button5 = findViewById(R.id.button5);
		imageview5 = findViewById(R.id.imageview5);
		button4 = findViewById(R.id.button4);
		d = new AlertDialog.Builder(this);
		f.setType("*/*");
		f.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		s = getSharedPreferences("s", Activity.MODE_PRIVATE);
		
		linear3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				MaterialAlertDialogBuilder d = new MaterialAlertDialogBuilder(MinimalMainActivity.this);
				d.setTitle("Pick your template!");
				d.setPositiveButton("Activity", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						i.setClass(getApplicationContext(), CreateProjectActivity.class);
						i.putExtra("a", "x");
						startActivity(i);
					}
				});
				d.setNeutralButton("AppCompat", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						i.setClass(getApplicationContext(), CreateProjectActivity.class);
						i.putExtra("a", "y");
						startActivity(i);
					}
				});
				d.create().show();
			}
		});
		
		linear5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), MinimalistListActivity.class);
				startActivity(i);
			}
		});
		
		linear7.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finishAffinity();
			}
		});
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				MaterialAlertDialogBuilder d = new MaterialAlertDialogBuilder(MinimalMainActivity.this);
				d.setTitle("Pick your template!");
				d.setPositiveButton("Activity", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						i.setClass(getApplicationContext(), CreateProjectActivity.class);
						i.putExtra("a", "x");
						startActivity(i);
					}
				});
				d.setNeutralButton("AppCompat", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						i.setClass(getApplicationContext(), CreateProjectActivity.class);
						i.putExtra("a", "y");
						startActivity(i);
					}
				});
				d.create().show();
			}
		});
		
		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), MinimalistListActivity.class);
				startActivity(i);
			}
		});
		
		button3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), ApplicationPrefActivity.class);
				startActivity(i);
			}
		});
		
		button4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finishAffinity();
			}
		});
	}
	
	private void initializeLogic() {
		getWindow().setNavigationBarColor(Color.TRANSPARENT);
		getWindow().setStatusBarColor(Color.TRANSPARENT);
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			case REQ_CD_F:
			if (_resultCode == Activity.RESULT_OK) {
				ArrayList<String> _filePath = new ArrayList<>();
				if (_data != null) {
					if (_data.getClipData() != null) {
						for (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {
							ClipData.Item _item = _data.getClipData().getItemAt(_index);
							_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));
						}
					}
					else {
						_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));
					}
				}
				i.setClass(getApplicationContext(), EditorActivity.class);
				i.putExtra("path", _filePath.get((int)(0)));
				i.putExtra("name", Uri.parse(_filePath.get((int)(0))).getLastPathSegment());
				startActivity(i);
			}
			else {
				
			}
			break;
			default:
			break;
		}
	}
	
	
	@Override
	public void onStart() {
		super.onStart();
		if (s.contains("zz")) {
			if (s.contains("ask")) {
				d.setTitle("えっと。。");
				d.setMessage("Do you want to continue where you left off?");
				d.setPositiveButton("Ok", new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(android.content.DialogInterface _dialog, int _which) {
						if (s.contains("checked1")) {
							if (s.contains("zz")) {
								String savedPath = s.getString("zz", "");
								if (!savedPath.isEmpty()) {
									i.setClass(getApplicationContext(), EditorActivity.class);
									i.putExtra("path", savedPath);
									i.putExtra("name", android.net.Uri.parse(savedPath).getLastPathSegment());
									startActivity(i);
								}
							}
						}
					}
				});
				d.setNeutralButton("Cancel", new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(android.content.DialogInterface _dialog, int _which) {
						
					}
				});
				d.create().show();
				
			} else {
				if (s.contains("checked1")) {
					if (s.contains("zz")) {
						t = new TimerTask() {
							@Override
							public void run() {
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										i.setClass(getApplicationContext(), EditorActivity.class);
										i.putExtra("path", s.getString("zz", ""));
										i.putExtra("name", Uri.parse(s.getString("zz", "")).getLastPathSegment());
										startActivity(i);
									}
								});
							}
						};
						_timer.schedule(t, (int)(1000));
					}
				}
			}
		}
		switch((int)SketchwareUtil.getRandom((int)(0), (int)(10))) {
			case ((int)0): {
				textview3.setText("Coding is fun, right? Now get back to work!");
				break;
			}
			case ((int)1): {
				textview3.setText("Do you like the new UI?");
				break;
			}
			case ((int)2): {
				textview3.setText("Welcome back! Now get back to work!");
				break;
			}
			case ((int)3): {
				textview3.setText("\\⁠(⁠ϋ⁠)⁠/⁠♩");
				break;
			}
			case ((int)4): {
				textview3.setText("にゃ～🐱");
				break;
			}
			case ((int)5): {
				textview3.setText("🦌🦌🦌🦌🦌");
				break;
			}
			case ((int)6): {
				textview3.setText("Get back to work!");
				break;
			}
			case ((int)7): {
				textview3.setText("しかしか 🦌🦌🦌");
				break;
			}
			case ((int)8): {
				textview3.setText("₍⁠₍⁠◞⁠(⁠ ⁠•⁠௰⁠•⁠ ⁠)⁠◟⁠₎⁠₎⁽⁠⁽⁠◝⁠(⁠ ⁠•⁠௰⁠•⁠ ⁠)⁠◜⁠⁾⁠⁾");
				break;
			}
			case ((int)9): {
				textview3.setText("Did you know?... Uhhh");
				break;
			}
			case ((int)10): {
				textview3.setText("Did you find the easter egg? 🧺🐣");
				break;
			}
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
