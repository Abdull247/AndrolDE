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
import android.net.Uri;
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
import com.airbnb.lottie.*;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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


public class JavActivity extends AppCompatActivity {
	
	public final int REQ_CD_FP = 101;
	
	private ExtendedFloatingActionButton _fab;
	private String folder = "";
	private double pos = 0;
	private String Sfolder = "";
	private double lastVisible = 0;
	private String information = "";
	private String file_path = "";
	private String import_file_name = "";
	private String import_path = "";
	
	private ArrayList<String> listString = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> listMap = new ArrayList<>();
	
	private LinearLayout linear2;
	private ListView listview1;
	private LottieAnimationView lottie1;
	private TextView textview2;
	private ImageView imageview1;
	private TextView textview1;
	private LinearLayout linear3;
	private ImageView imageview2;
	private ImageView imageview3;
	
	private AlertDialog.Builder d;
	private ObjectAnimator obj = new ObjectAnimator();
	private Intent fp = new Intent(Intent.ACTION_GET_CONTENT);
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.jav);
		initialize(_savedInstanceState);
		
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
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
		listview1 = findViewById(R.id.listview1);
		lottie1 = findViewById(R.id.lottie1);
		textview2 = findViewById(R.id.textview2);
		imageview1 = findViewById(R.id.imageview1);
		textview1 = findViewById(R.id.textview1);
		linear3 = findViewById(R.id.linear3);
		imageview2 = findViewById(R.id.imageview2);
		imageview3 = findViewById(R.id.imageview3);
		d = new AlertDialog.Builder(this);
		fp.setType("*/*");
		fp.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		
		listview1.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView abs, int _scrollState) {
				
			}
			
			@Override
			public void onScroll(AbsListView abs, int _firstVisibleItem, int _visibleItemCount, int _totalItemCount) {
				if (lastVisible < _firstVisibleItem) {
					_fab.extend();
				}
				if (lastVisible > _firstVisibleItem) {
					_fab.shrink();
				}
				lastVisible = _firstVisibleItem;
			}
		});
		
		listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				if (FileUtil.isDirectory(listString.get((int)(_position)))) {
					folder = listString.get((int)(_position));
					_refreshFileManager();
				} else {
					file_path = listString.get((int)(_position));
					PreviewOnlySheetBottomdialogFragmentActivity bottomSheet = new PreviewOnlySheetBottomdialogFragmentActivity();
					bottomSheet.setStyle(androidx.fragment.app.DialogFragment.STYLE_NORMAL, R.style.AttachBottomSheetTheme);
					
					Bundle args = new Bundle();
					args.putString("file_path", file_path);
					bottomSheet.setArguments(args);
					
					bottomSheet.show(getSupportFragmentManager(), "PreviewOnlySheetBottomSheet");
				}
			}
		});
		
		listview1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				MaterialAlertDialogBuilder d = new MaterialAlertDialogBuilder(JavActivity.this);
				
				d.setTitle("Delete Project?");
				d.setMessage("Are you sure you want to delete this project? This thing cannot be undone bro! Think twice!");
				d.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						FileUtil.deleteFile(listString.get((int)(_position)));
						_refreshFileManager();
					}
				});
				d.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						
					}
				});
				d.create().show();
				return true;
			}
		});
		
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (folder.equals(getIntent().getStringExtra("spath").concat("/app/src/main/java"))) {
					finish();
				} else {
					Sfolder = folder.substring((int)(0), (int)(folder.lastIndexOf("/")));
					folder = Sfolder;
					_refreshFileManager();
				}
			}
		});
		
		imageview3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				MaterialAlertDialogBuilder d = new MaterialAlertDialogBuilder(JavActivity.this);
				d.setTitle("What is it?");
				d.setMessage("Do you want to create a file or folder?");
				d.setPositiveButton("File", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						//layout xml
						
						View alert = getLayoutInflater().inflate(R.layout.edit, null);
						
						MaterialAlertDialogBuilder d = new MaterialAlertDialogBuilder(JavActivity.this);
						d.setTitle("Create a file");
						
						d.setView(alert);
						
						d.setMessage("File name: ");
						final TextInputEditText val = (TextInputEditText)alert.findViewById(R.id.edittext1);
						d.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface _dialog, int _which) {
								_refreshFileManager();
								com.google.android.material.snackbar.Snackbar.make(linear2, "File created successfully!", com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("OK", new View.OnClickListener(){
									@Override
									public void onClick(View _view) {
										
									}
								}).show();
							}
						});
						d.setNeutralButton("Import", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface _dialog, int _which) {
								startActivityForResult(fp, REQ_CD_FP);
							}
						});
						d.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface _dialog, int _which) {
								
							}
						});
						d.create().show();
					}
				});
				d.setNeutralButton("Folder", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						//layout xml
						
						View alert = getLayoutInflater().inflate(R.layout.edit, null);
						
						MaterialAlertDialogBuilder d = new MaterialAlertDialogBuilder(JavActivity.this);
						d.setTitle("Create a folder");
						
						d.setView(alert);
						
						d.setMessage("Folder name: ");
						final TextInputEditText val = (TextInputEditText)alert.findViewById(R.id.edittext1);
						d.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface _dialog, int _which) {
								FileUtil.makeDir(folder.concat("/".concat(val.getText().toString())));
								_refreshFileManager();
								com.google.android.material.snackbar.Snackbar.make(linear2, "Folder created successfully!", com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("OK", new View.OnClickListener(){
									@Override
									public void onClick(View _view) {
										
									}
								}).show();
							}
						});
						d.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface _dialog, int _which) {
								
							}
						});
						d.create().show();
					}
				});
				d.create().show();
			}
		});
	}
	
	private void initializeLogic() {
		folder = getIntent().getStringExtra("spath").concat("/app/src/main/java");
		getWindow().setNavigationBarColor(Color.TRANSPARENT);
		getWindow().setStatusBarColor(Color.TRANSPARENT);
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			case REQ_CD_FP:
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
				if (FileUtil.isFile(_filePath.get((int)(0)))) {
					import_path = _filePath.get((int)(0));
					import_file_name = Uri.parse(import_path).getLastPathSegment();
					FileUtil.copyFile(import_path, folder.concat("/".concat(import_file_name)));
					_refreshFileManager();
					com.google.android.material.snackbar.Snackbar.make(linear2, "File imported successfully!", com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("OK", new View.OnClickListener(){
						@Override
						public void onClick(View _view) {
							
						}
					}).show();
				} else {
					com.google.android.material.snackbar.Snackbar.make(linear2, "Can't import a folder", com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("OK", new View.OnClickListener(){
						@Override
						public void onClick(View _view) {
							
						}
					}).show();
				}
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
		// This will remove the highlight color & divider color for listview1. custom code by Yuuki
		
		listview1.setSelector(new ColorDrawable(Color.TRANSPARENT));
		lottie1.setAnimation("animations/hmm.json");
		_refreshFileManager();
	}
	
	@Override
	public void onBackPressed() {
		if (folder.equals(getIntent().getStringExtra("spath").concat("/app/src/main/java"))) {
			finish();
		} else {
			Sfolder = folder.substring((int)(0), (int)(folder.lastIndexOf("/")));
			folder = Sfolder;
			_refreshFileManager();
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
			
			textview2.setVisibility(View.VISIBLE);
			lottie1.setVisibility(View.VISIBLE);
			obj.setTarget(textview2);
			obj.setPropertyName("alpha");
			obj.setFloatValues((float)(0), (float)(1));
			obj.setDuration((int)(500));
			obj.start();
		} else {
			// If not listview = 0
			
			textview2.setVisibility(View.GONE);
			lottie1.setVisibility(View.GONE);
			listview1.setAdapter(new Listview1Adapter(listMap));
			((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
			obj.setTarget(listview1);
			obj.setPropertyName("alpha");
			obj.setFloatValues((float)(0), (float)(1));
			obj.setDuration((int)(500));
			obj.start();
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
