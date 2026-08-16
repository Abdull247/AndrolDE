package org.ide.andriod;

import android.Manifest;
import android.animation.*;
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
import com.google.android.material.button.*;
import com.google.android.material.card.*;
import com.google.gson.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;
import org.json.*;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;


public class ImageManagerActivity extends AppCompatActivity {
	
	public final int REQ_CD_FP = 101;
	
	private String path = "";
	private boolean is_selection_mode = false;
	private String import_path = "";
	private String import_file_name = "";
	
	private ArrayList<HashMap<String, Object>> drawable_lsmap = new ArrayList<>();
	private ArrayList<String> selected_paths = new ArrayList<>();
	
	private LinearLayout linear2;
	private RelativeLayout relativelayout1;
	private ImageView imageview1;
	private TextView textview1;
	private LinearLayout linear3;
	private ImageView imageview3;
	private RecyclerView list_rec;
	private LinearLayout linear4;
	private MaterialCardView materialCardView1;
	private LinearLayout linear5;
	private MaterialButton btn_s1;
	private MaterialButton btn_s2;
	
	private AlertDialog.Builder dd;
	private Intent fp = new Intent(Intent.ACTION_GET_CONTENT);
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.image_manager);
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
		linear2 = findViewById(R.id.linear2);
		relativelayout1 = findViewById(R.id.relativelayout1);
		imageview1 = findViewById(R.id.imageview1);
		textview1 = findViewById(R.id.textview1);
		linear3 = findViewById(R.id.linear3);
		imageview3 = findViewById(R.id.imageview3);
		list_rec = findViewById(R.id.list_rec);
		linear4 = findViewById(R.id.linear4);
		materialCardView1 = findViewById(R.id.materialCardView1);
		linear5 = findViewById(R.id.linear5);
		btn_s1 = findViewById(R.id.btn_s1);
		btn_s2 = findViewById(R.id.btn_s2);
		dd = new AlertDialog.Builder(this);
		fp.setType("image/*");
		fp.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				onBackPressed();
			}
		});
		
		btn_s1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		btn_s2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
	}
	
	private void initializeLogic() {
		if (getIntent().hasExtra("drawable_path")) {
			path = getIntent().getStringExtra("drawable_path");
			
			File directory = new File(path);
			File[] files = directory.listFiles();
			
			if (files != null) {
				for (File file : files) {
					if (file.isFile()) {
						String fileName = file.getName().toLowerCase();
						if (fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".webp")) {
							HashMap<String, Object> listmap = new HashMap<>();
							listmap.put("path", file.getAbsolutePath());
							listmap.put("name", file.getName());
							drawable_lsmap.add(listmap);
						}
					}
				}
			}
		}
		
		list_rec.setAdapter(new List_recAdapter(drawable_lsmap));
		GridLayoutManager gridLayoutManager2 = new GridLayoutManager(ImageManagerActivity.this, 3, GridLayoutManager.VERTICAL, false);
		list_rec.setLayoutManager(gridLayoutManager2);
		btn_s1.setVisibility(View.GONE);
		btn_s1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				selected_paths.clear();
				is_selection_mode = false;
				list_rec.getAdapter().notifyDataSetChanged();
				updateSelectionUI();
			}
		});
		btn_s2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (is_selection_mode && !selected_paths.isEmpty()) {
					StringBuilder fileListBuilder = new StringBuilder();
					for (String selectedPath : selected_paths) {
						File f = new File(selectedPath);
						fileListBuilder.append(f.getName()).append("\n");
					}
					
					MaterialAlertDialogBuilder dd = new MaterialAlertDialogBuilder(ImageManagerActivity.this);
					dd.setTitle("Delete Resource?");
					dd.setMessage("Are you sure you wanna delete these images resources from your project?\n\n" + fileListBuilder.toString() + "\nTotal of " + selected_paths.size() + " images...");
					dd.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							for (String selectedPath : selected_paths) {
								File fileToDelete = new File(selectedPath);
								if (fileToDelete.exists()) {
									fileToDelete.delete();
								}
							}
							
							Iterator<HashMap<String, Object>> iterator = drawable_lsmap.iterator();
							while (iterator.hasNext()) {
								HashMap<String, Object> item = iterator.next();
								if (selected_paths.contains(item.get("path").toString())) {
									iterator.remove();
								}
							}
							
							selected_paths.clear();
							is_selection_mode = false;
							list_rec.getAdapter().notifyDataSetChanged();
							updateSelectionUI();
							_dialog.dismiss();
						}
					});
					dd.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							_dialog.dismiss();
						}
					});
					dd.create().show();
				} else {
					startActivityForResult(fp, REQ_CD_FP);
				}
			}
		});
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
					if (import_file_name.endsWith(".png") || (import_file_name.endsWith(".jpg") || (import_file_name.endsWith(".jpeg") || import_file_name.endsWith(".webp")))) {
						String newFilePath = path.concat("/".concat(import_file_name));
						FileUtil.copyFile(import_path, newFilePath);
						
						HashMap<String, Object> listmap = new HashMap<>();
						listmap.put("path", newFilePath);
						listmap.put("name", import_file_name);
						drawable_lsmap.add(0, listmap);
						
						list_rec.getAdapter().notifyItemInserted(0);
						list_rec.scrollToPosition(0);
						
						com.google.android.material.snackbar.Snackbar.make(linear2, "Imported ".concat(import_file_name.concat(" successfully")), com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("OK", new View.OnClickListener(){
							@Override
							public void onClick(View _view) {
								
							}
						}).show();
					} else {
						com.google.android.material.snackbar.Snackbar.make(linear2, "Image files only", com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("", new View.OnClickListener(){
							@Override
							public void onClick(View _view) {
								
							}
						}).show();
					}
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
	public void onBackPressed() {
		if (is_selection_mode) {
			selected_paths.clear();
			is_selection_mode = false;
			list_rec.getAdapter().notifyDataSetChanged();
			updateSelectionUI();
		} else {
			finish();
		}
	}
	public void _anchore() {
		
	}
    
    
    public void updateSelectionUI() {
    if (selected_paths.isEmpty()) {
        is_selection_mode = false;
        btn_s1.setVisibility(View.GONE);
        btn_s2.setText("Add image");
    } else {
        btn_s1.setVisibility(View.VISIBLE);
        btn_s2.setText("Delete (" + selected_paths.size() + ")");
    }
}
	
	public class List_recAdapter extends RecyclerView.Adapter<List_recAdapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public List_recAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getLayoutInflater();
			View _v = _inflater.inflate(R.layout.image_cus_item, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final com.google.android.material.card.MaterialCardView item_cd = _view.findViewById(R.id.item_cd);
			final RelativeLayout relativelayout1 = _view.findViewById(R.id.relativelayout1);
			final ImageView image_icon = _view.findViewById(R.id.image_icon);
			final LinearLayout bottom_overlap = _view.findViewById(R.id.bottom_overlap);
			final LinearLayout top_overlap = _view.findViewById(R.id.top_overlap);
			final TextView imageTitle = _view.findViewById(R.id.imageTitle);
			final LinearLayout linear3 = _view.findViewById(R.id.linear3);
			final com.google.android.material.card.MaterialCardView delete_state_bg = _view.findViewById(R.id.delete_state_bg);
			final LinearLayout delete_state_inner_bg = _view.findViewById(R.id.delete_state_inner_bg);
			final ImageView delete_state_icon = _view.findViewById(R.id.delete_state_icon);
			
			if (_data.get((int)_position).containsKey("path")) {
				image_icon.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(_data.get((int)_position).get("path").toString(), 1024, 1024));
			}
			if (_data.get((int)_position).containsKey("name")) {
				imageTitle.setText(_data.get((int)_position).get("name").toString());
			}
			
			final String currentPath = _data.get((int)_position).get("path").toString();
			
			if (is_selection_mode) {
				delete_state_bg.setVisibility(View.VISIBLE);
				if (selected_paths.contains(currentPath)) {
					delete_state_icon.setImageResource(R.drawable.icon_done_round);
					delete_state_icon.clearColorFilter();
				} else {
					delete_state_icon.setImageResource(R.drawable.icon_delete_outline_round);
					delete_state_icon.setColorFilter(Color.parseColor("#F44336"));
				}
			} else {
				delete_state_bg.setVisibility(View.GONE);
			}
			
			item_cd.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					if (!is_selection_mode) {
						is_selection_mode = true;
					}
					if (!selected_paths.contains(currentPath)) {
						selected_paths.add(currentPath);
					}
					notifyDataSetChanged();
					updateSelectionUI();
					return true;
				}
			});
			
			item_cd.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (is_selection_mode) {
						if (selected_paths.contains(currentPath)) {
							selected_paths.remove(currentPath);
						} else {
							selected_paths.add(currentPath);
						}
						notifyDataSetChanged();
						updateSelectionUI();
					}
					// else branch (normal tap when not in selection mode) - implement later
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
