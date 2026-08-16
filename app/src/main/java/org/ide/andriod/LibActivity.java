package org.ide.andriod;

import android.Manifest;
import android.animation.*;
import android.animation.ObjectAnimator;
import android.app.*;
import android.app.AlertDialog;
import android.content.*;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.os.*;
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
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.vectordrawable.*;
import androidx.versionedparcelable.*;
import androidx.viewpager.*;
import com.airbnb.lottie.*;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;
import com.google.gson.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;
import org.json.*;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import android.view.LayoutInflater;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.reflect.TypeToken;


public class LibActivity extends AppCompatActivity {
	
	public final int REQ_CD_F = 101;
	
	private ExtendedFloatingActionButton _fab;
	private String folder = "";
	private double pos = 0;
	private String Sfolder = "";
	private String information = "";
	private double lastVisible = 0;
	private String localLibsJsonPath = "";
	
	private ArrayList<HashMap<String, Object>> libs_map = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> libs_map_all = new ArrayList<>();
	
	private LinearLayout linear2;
	private LinearLayout back;
	private ImageView imageview1;
	private TextView textview1;
	private LinearLayout linear3;
	private ImageView imageview2;
	private ImageView imageview3;
	private LinearProgressIndicator progressbar1;
	private TabLayout tablayout1;
	private RecyclerView recyclerview1;
	private LinearLayout empty_view;
	private LottieAnimationView lottie1;
	private TextView textview2;
	
	private AlertDialog.Builder d;
	private ObjectAnimator obj = new ObjectAnimator();
	private Intent f = new Intent(Intent.ACTION_GET_CONTENT);
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.lib);
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
		linear2 = findViewById(R.id.linear2);
		back = findViewById(R.id.back);
		imageview1 = findViewById(R.id.imageview1);
		textview1 = findViewById(R.id.textview1);
		linear3 = findViewById(R.id.linear3);
		imageview2 = findViewById(R.id.imageview2);
		imageview3 = findViewById(R.id.imageview3);
		progressbar1 = findViewById(R.id.progressbar1);
		tablayout1 = findViewById(R.id.tablayout1);
		recyclerview1 = findViewById(R.id.recyclerview1);
		empty_view = findViewById(R.id.empty_view);
		lottie1 = findViewById(R.id.lottie1);
		textview2 = findViewById(R.id.textview2);
		d = new AlertDialog.Builder(this);
		f.setType("*/*");
		f.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
		
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				MaterialAlertDialogBuilder d = new MaterialAlertDialogBuilder(LibActivity.this);
				d.setTitle("What is it?");
				d.setMessage("Do you want to create a file or folder?");
				d.setPositiveButton("Import a Lib/Dependency", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						startActivityForResult(f, REQ_CD_F);
					}
				});
				d.setNeutralButton("Download  Lib/Dependency", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						
					}
				});
				d.create().show();
			}
		});
	}
	
	private void initializeLogic() {
		getWindow().setNavigationBarColor(Color.TRANSPARENT);
		getWindow().setStatusBarColor(Color.TRANSPARENT);
		empty_view.setVisibility(View.GONE);
		tablayout1.addTab(tablayout1.newTab().setText("Downloaded libraries"));
		tablayout1.addTab(tablayout1.newTab().setText("Enabled libraries"));
		
		if (progressbar1.getVisibility() == View.GONE) {
			progressbar1.setIndeterminate(true);
			progressbar1.setVisibility(View.VISIBLE);
		}
		
		final String SHARED_LIBS_JSON_PATH = "/storage/emulated/0/.androIDE/libs/libs_data.json";
		
		folder = getIntent().getStringExtra("spath");
		
		// Derive project name from the spath (mysc path) to locate the data dir
		String _folderClean = folder.endsWith("/") ? folder.substring(0, folder.length() - 1) : folder;
		String _projectName = new File(_folderClean).getName();
		localLibsJsonPath = "/storage/emulated/0/.androIDE/data/".concat(_projectName).concat("/local_libs.json");
		
		libs_map_all = new ArrayList<>();
		
		if (FileUtil.isExistFile(SHARED_LIBS_JSON_PATH)) {
			String _sharedJson = FileUtil.readFile(SHARED_LIBS_JSON_PATH);
			
			if (_sharedJson != null && !_sharedJson.trim().isEmpty()) {
				
				ArrayList<HashMap<String, Object>> _sharedLibs = new Gson().fromJson(
				_sharedJson,
				new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType()
				);
				
				// Build a set of enabled lib_paths for this project
				HashMap<String, Boolean> _enabledPaths = new HashMap<>();
				if (FileUtil.isExistFile(localLibsJsonPath)) {
					String _localJson = FileUtil.readFile(localLibsJsonPath);
					if (_localJson != null && !_localJson.trim().isEmpty()) {
						ArrayList<HashMap<String, Object>> _localLibs = new Gson().fromJson(
						_localJson,
						new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType()
						);
						if (_localLibs != null) {
							for (HashMap<String, Object> _local : _localLibs) {
								if (_local.get("lib_path") != null) {
									_enabledPaths.put(_local.get("lib_path").toString(), true);
								}
							}
						}
					}
				}
				
				if (_sharedLibs != null) {
					for (HashMap<String, Object> _lib : _sharedLibs) {
						String _libName = _lib.get("lib_name") != null ? _lib.get("lib_name").toString() : "";
						String _libPath = _lib.get("lib_path") != null ? _lib.get("lib_path").toString() : "";
						
						HashMap<String, Object> _entry = new HashMap<>();
						_entry.put("lib_name", _libName);
						_entry.put("lib_path", _libPath);
						_entry.put("folder_size", FileUtil.getReadableFileOrDirectorySize(_libPath));
						_entry.put("is_enabled", _enabledPaths.containsKey(_libPath));
						
						libs_map_all.add(_entry);
					}
				}
			}
		}
		
		// Default view: "Downloaded libraries" tab shows everything
		libs_map = new ArrayList<>(libs_map_all);
		
		recyclerview1.setAdapter(new Recyclerview1Adapter(libs_map));
		recyclerview1.setLayoutManager(new LinearLayoutManager(this));
		
		tablayout1.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				refreshLibsForTab(tab.getPosition());
			}
			
			@Override
			public void onTabUnselected(TabLayout.Tab tab) {
			}
			
			@Override
			public void onTabReselected(TabLayout.Tab tab) {
			}
		});
		
		progressbar1.setVisibility(View.GONE);
	}
	
	
	@Override
	public void onStart() {
		super.onStart();
		lottie1.setAnimation("animations/hmm.json");
	}
	
	@Override
	public void onBackPressed() {
		finish();
	}
	public void _anchore() {
		
	}
    
    private void updateLocalLibsJson(HashMap<String, Object> _libEntry, boolean isChecked) {
	String _libName = _libEntry.get("lib_name") != null ? _libEntry.get("lib_name").toString() : "";
	String _libPath = _libEntry.get("lib_path") != null ? _libEntry.get("lib_path").toString() : "";

	ArrayList<HashMap<String, Object>> _localLibs = new ArrayList<>();

	if (FileUtil.isExistFile(localLibsJsonPath)) {
		String _localJson = FileUtil.readFile(localLibsJsonPath);
		if (_localJson != null && !_localJson.trim().isEmpty()) {
			ArrayList<HashMap<String, Object>> _parsed = new Gson().fromJson(
				_localJson,
				new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType()
			);
			if (_parsed != null) {
				_localLibs = _parsed;
			}
		}
	}

	if (isChecked) {
		boolean _alreadyExists = false;
		for (HashMap<String, Object> _entry : _localLibs) {
			if (_libPath.equals(_entry.get("lib_path"))) {
				_alreadyExists = true;
				break;
			}
		}
		if (!_alreadyExists) {
			HashMap<String, Object> _newEntry = new HashMap<>();
			_newEntry.put("lib_name", _libName);
			_newEntry.put("lib_path", _libPath);
			_localLibs.add(_newEntry);
		}
	} else {
		for (int i = _localLibs.size() - 1; i >= 0; i--) {
			if (_libPath.equals(_localLibs.get(i).get("lib_path"))) {
				_localLibs.remove(i);
			}
		}
	}

	FileUtil.writeFile(localLibsJsonPath, new Gson().toJson(_localLibs));
}

private void refreshLibsForTab(int _tabPosition) {
	libs_map.clear();

	if (_tabPosition == 1) {
		// Enabled libraries only
		for (HashMap<String, Object> _entry : libs_map_all) {
			if (_entry.get("is_enabled") != null && (Boolean) _entry.get("is_enabled")) {
				libs_map.add(_entry);
			}
		}
	} else {
		// Downloaded libraries (all)
		libs_map.addAll(libs_map_all);
	}

	recyclerview1.getAdapter().notifyDataSetChanged();
}
	
	public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getLayoutInflater();
			View _v = _inflater.inflate(R.layout.lib_cus_item, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final CheckBox item_checkbox = _view.findViewById(R.id.item_checkbox);
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final TextView lib_name = _view.findViewById(R.id.lib_name);
			final TextView lib_size = _view.findViewById(R.id.lib_size);
			
			final HashMap<String, Object> _data = libs_map.get(_position);
			
			String _libName = _data.get("lib_name") != null ? _data.get("lib_name").toString() : "";
			String _libSize = _data.get("folder_size") != null ? _data.get("folder_size").toString() : "";
			final boolean _isEnabled = _data.get("is_enabled") != null && (Boolean) _data.get("is_enabled");
			
			lib_name.setText(_libName);
			lib_size.setText(_libSize);
			
			// Clear previous listener before setting checked state to avoid
			// firing a stale onCheckedChanged during view recycling.
			item_checkbox.setOnCheckedChangeListener(null);
			item_checkbox.setChecked(_isEnabled);
			
			item_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					_data.put("is_enabled", isChecked);
					updateLocalLibsJson(_data, isChecked);
					if (tablayout1.getSelectedTabPosition() == 1) {
						refreshLibsForTab(1);
					}
				}
			});
		}
		
		@Override
		public int getItemCount() {
			return _data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder {
			public ViewHolder(View v) {
				super(v);
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
