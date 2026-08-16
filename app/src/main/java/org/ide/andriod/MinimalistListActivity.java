package org.ide.andriod;

import android.Manifest;
import android.animation.*;
import android.animation.ObjectAnimator;
import android.app.*;
import android.app.Activity;
import android.content.*;
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
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.webkit.*;
import android.widget.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.*;
import java.io.*;
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;
import org.json.*;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;


public class MinimalistListActivity extends AppCompatActivity {
	
	private ExtendedFloatingActionButton _fab;
	private String folder = "";
	private double pos = 0;
	private String information = "";
	private String Sfolder = "";
	private String targetPath = "";
	
	private ArrayList<String> listString = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> listMap = new ArrayList<>();
	
	private LinearLayout linear1;
	private TextView textview1;
	private TextView textview3;
	private LinearLayout linear2;
	private ListView listview1;
	private TextView textview4;
	private ImageView imageview1;
	private LinearLayout linear3;
	private TextView textview5;
	private TextView textview2;
	
	private ObjectAnimator obj = new ObjectAnimator();
	private Intent i = new Intent();
	private SharedPreferences s;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.minimalist_list);
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
		_fab = findViewById(R.id._fab);
		linear1 = findViewById(R.id.linear1);
		textview1 = findViewById(R.id.textview1);
		textview3 = findViewById(R.id.textview3);
		linear2 = findViewById(R.id.linear2);
		listview1 = findViewById(R.id.listview1);
		textview4 = findViewById(R.id.textview4);
		imageview1 = findViewById(R.id.imageview1);
		linear3 = findViewById(R.id.linear3);
		textview5 = findViewById(R.id.textview5);
		textview2 = findViewById(R.id.textview2);
		s = getSharedPreferences("s", Activity.MODE_PRIVATE);
		
		linear2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (folder.equals(FileUtil.getExternalStorageDir())) {
					finish();
				} else {
					Sfolder = folder.substring((int)(0), (int)(folder.lastIndexOf("/")));
					folder = Sfolder;
					_refreshFileManager();
					_refreshOnClick();
				}
			}
		});
		
		listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				if (FileUtil.isDirectory(listString.get((int)(_position)))) {
					folder = listString.get((int)(_position));
					_refreshFileManager();
					_refreshOnClick();
				} else {
					
				}
			}
		});
		
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), EditorActivity.class);
				i.putExtra("path", folder);
				i.putExtra("name", Uri.parse(folder).getLastPathSegment());
				s.edit().putString("zz", folder).commit();
				startActivity(i);
			}
		});
	}
	
	private void initializeLogic() {
		folder = FileUtil.getExternalStorageDir().concat("/.androIDE/mysc/");
		getWindow().setNavigationBarColor(Color.TRANSPARENT);
		getWindow().setStatusBarColor(Color.TRANSPARENT);
	}
	
	
	@Override
	public void onStart() {
		super.onStart();
		_refreshFileManager();
		_refreshOnClick();
	}
	
	@Override
	public void onBackPressed() {
		if (folder.equals(FileUtil.getExternalStorageDir())) {
			finish();
		} else {
			Sfolder = folder.substring((int)(0), (int)(folder.lastIndexOf("/")));
			folder = Sfolder;
			_refreshFileManager();
			_refreshOnClick();
		}
	}
	public void _refreshFileManager() {
		listview1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE); listview1.setItemsCanFocus(false);
		listMap.clear();
		FileUtil.listDir(folder, listString);
		Collections.sort(listString, String.CASE_INSENSITIVE_ORDER);
		pos = 0;
		for(int _repeat18 = 0; _repeat18 < (int)(listString.size()); _repeat18++) {
			{
				HashMap<String, Object> _item = new HashMap<>();
				_item.put("file", listString.get((int)(pos)));
				listMap.add(_item);
			}
			pos++;
		}
		if (listMap.size() == 0) {
			// If listview = 0
			
			textview4.setVisibility(View.VISIBLE);
			obj.setTarget(textview4);
			obj.setPropertyName("alpha");
			obj.setFloatValues((float)(0), (float)(1));
			obj.setDuration((int)(500));
			obj.start();
		} else {
			// If not listview = 0
			
			textview4.setVisibility(View.GONE);
			listview1.setAdapter(new Listview1Adapter(listMap));
			((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
			obj.setTarget(listview1);
			obj.setPropertyName("alpha");
			obj.setFloatValues((float)(0), (float)(1));
			obj.setDuration((int)(500));
			obj.start();
		}
		if (folder.contains("/.androIDE/mysc") || folder.contains("/.androIDE/mysc")) {
			textview3.setText("Project List");
		} else {
			if (folder.contains("/.androIDE/backups") || folder.contains("/.androIDE/backups")) {
				textview3.setText("Project backups");
			} else {
				if (folder.contains("/.androIDE/collection") || folder.contains("/.androIDE/collection")) {
					textview3.setText("Collections");
				} else {
					if (folder.contains("/.androIDE/libs") || folder.contains("/.androIDE/libs")) {
						textview3.setText("Libraries");
					} else {
						if (folder.contains("/.androIDE/resources") || folder.contains("/.androIDE/resources")) {
							textview3.setText("Resources");
						} else {
							if (folder.contains("/.androIDE/data") || folder.contains("/.androIDE/data")) {
								textview3.setText("Project Data");
							} else {
								if (folder.contains("/.androIDE/system") || folder.contains("/.androIDE/system")) {
									textview3.setText("System configurations");
								} else {
									if (folder.contains("/.androIDE/logs") || folder.contains("/.androIDE/logs")) {
										textview3.setText("Saved Logs");
									} else {
										if (folder.contains("/.androIDE/temp") || folder.contains("/.androIDE/temp")) {
											textview3.setText("Temporal system data");
										} else {
											if (folder.contains("/.androIDE") || folder.contains("/.androIDE")) {
												textview3.setText("App directory");
											} else {
												textview3.setText("Internal Storage");
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	
	public void _refreshOnClick() {
		try {
			targetPath = folder; // Assigns your native Sketchware string variable to our safe local pointer
			
			if (FileUtil.isExistFile(targetPath + "/app")) {
				_fab.extend();
				_fab.setEnabled(true);
			} else {
				_fab.shrink();
				_fab.setEnabled(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public class Listview1Adapter extends BaseAdapter {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Listview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		
		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = getLayoutInflater();
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.all_list, null);
			}
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final ImageView imageview1 = _view.findViewById(R.id.imageview1);
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final TextView textview1 = _view.findViewById(R.id.textview1);
			final TextView textview2 = _view.findViewById(R.id.textview2);
			
			textview1.setText(Uri.parse(listString.get((int)(_position))).getLastPathSegment());
			if (FileUtil.isDirectory(listString.get((int)(_position)))) {
				imageview1.setImageResource(R.drawable.out_folder);
				textview2.setText(folder);
			} else {
				information = listString.get((int)(_position));
				final java.io.File file1 = new java.io.File(information);
				try{
					long length = file1.length();
					length = length/1024;
					textview2.setText("File size : " + length +" KB");
				}catch(Exception b){
					showMessage("File not found : " + b.getMessage() + b);
				}
				imageview1.setImageResource(R.drawable.file);
			}
			
			return _view;
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
