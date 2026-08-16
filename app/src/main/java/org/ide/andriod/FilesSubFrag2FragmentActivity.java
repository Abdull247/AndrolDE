package org.ide.andriod;

import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.Typeface;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.HorizontalScrollView;
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
import androidx.core.widget.NestedScrollView;
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
import com.google.android.material.card.*;
import com.google.gson.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;
import org.json.*;

public class FilesSubFrag2FragmentActivity extends Fragment {
	
	private String path = "";
	private String name = "";
	private String currentPath = "";
	private Recyclerview1Adapter recyclerview1Adapter;
	private EditorBridge bridge;
	
	private ArrayList<HashMap<String, Object>> files_listmap = new ArrayList<>();
	private ArrayList<String> pathStack = new ArrayList<>();
	
	private LinearLayout linear1;
	private NestedScrollView nestedScrollView1;
	private LinearLayout linear2;
	private HorizontalScrollView hscroll1;
	private MaterialCardView back_trail_top;
	private RecyclerView recyclerview1;
	private LinearLayout top_indicator;
	private TextView path_trail_txt;
	private LinearLayout back_trail_top_linear;
	private ImageView imageview1;
	private LinearLayout linear4;
	private TextView textview5;
	private TextView textview2;
	
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.files_sub_frag2_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		linear1 = _view.findViewById(R.id.linear1);
		nestedScrollView1 = _view.findViewById(R.id.nestedScrollView1);
		linear2 = _view.findViewById(R.id.linear2);
		hscroll1 = _view.findViewById(R.id.hscroll1);
		back_trail_top = _view.findViewById(R.id.back_trail_top);
		recyclerview1 = _view.findViewById(R.id.recyclerview1);
		top_indicator = _view.findViewById(R.id.top_indicator);
		path_trail_txt = _view.findViewById(R.id.path_trail_txt);
		back_trail_top_linear = _view.findViewById(R.id.back_trail_top_linear);
		imageview1 = _view.findViewById(R.id.imageview1);
		linear4 = _view.findViewById(R.id.linear4);
		textview5 = _view.findViewById(R.id.textview5);
		textview2 = _view.findViewById(R.id.textview2);
	}
	
	private void initializeLogic() {
		path_trail_txt.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/enbold.ttf"), 1);
		hscroll1.setHorizontalScrollBarEnabled(false);
		hscroll1.setVerticalScrollBarEnabled(false);
		hscroll1.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
		path = getArguments() != null ? getArguments().getString("path") : "";
		name = getArguments() != null ? getArguments().getString("name") : "";
		currentPath = path;
		pathStack.clear();
		pathStack.add(path);
		bridge = ((EditorActivity) getActivity()).getBridge();
		recyclerview1.setLayoutManager(new LinearLayoutManager(getContext()));
		recyclerview1Adapter = new Recyclerview1Adapter(files_listmap);
		recyclerview1.setAdapter(recyclerview1Adapter);
		_loadDirectory(currentPath);
		back_trail_top.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (pathStack.size() > 1) {
					pathStack.remove(pathStack.size() - 1);
					currentPath = pathStack.get(pathStack.size() - 1);
					_loadDirectory(currentPath);
				}
			}
		});
	}
	
	public void _anchor() {
		
	}
   
   private void _loadDirectory(final String _dirPath) {
	files_listmap.clear();

	ArrayList<String> _entries = new ArrayList<>();
	FileUtil.listDir(_dirPath, _entries);

	for (String _entry : _entries) {
		HashMap<String, Object> _item = new HashMap<>();
		_item.put("path", _entry);
		_item.put("name", Uri.parse(_entry).getLastPathSegment());
		_item.put("isDirectory", FileUtil.isDirectory(_entry));
		files_listmap.add(_item);
	}

	recyclerview1Adapter.notifyDataSetChanged();

	if (pathStack.size() <= 1) {
		back_trail_top.setVisibility(View.GONE);
	} else {
		back_trail_top.setVisibility(View.VISIBLE);
	}

	StringBuilder _trail = new StringBuilder();
	for (int ii = 0; ii < pathStack.size(); ii++) {
		String _segName = Uri.parse(pathStack.get(ii)).getLastPathSegment();
		_trail.append(_segName);
		if (ii < pathStack.size() - 1) {
			_trail.append(" > ");
		}
	}
	path_trail_txt.setText(_trail.toString());
}  

public void _onFolderItemClick(String _folderPath) {
	pathStack.add(_folderPath);
	currentPath = _folderPath;
	_loadDirectory(currentPath);
}

public String _getFileSize(String _path) {
	try {
		java.io.File _f = new java.io.File(_path);
		long _bytes = _f.length();
		if (_bytes < 1024) return _bytes + " B";
		if (_bytes < 1024 * 1024) return (_bytes / 1024) + " KB";
		return (_bytes / (1024 * 1024)) + " MB";
	} catch (Exception e) {
		return "";
	}
}
	
	
	public void _call_snackbar(final String _text) {
		com.google.android.material.snackbar.Snackbar.make(linear1, _text, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("Ok", new View.OnClickListener(){
			@Override
			public void onClick(View _view) {
				
			}
		}).show();
	}
	
	public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getActivity().getLayoutInflater();
			View _v = _inflater.inflate(R.layout.files_sub_item, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final com.google.android.material.card.MaterialCardView item_bg_cd = _view.findViewById(R.id.item_bg_cd);
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final ImageView imageview1 = _view.findViewById(R.id.imageview1);
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final TextView textview1 = _view.findViewById(R.id.textview1);
			final TextView textview2 = _view.findViewById(R.id.textview2);
			
			final HashMap<String, Object> _item = _data.get(_position);
			final String _path = (String) _item.get("path");
			final String _name = (String) _item.get("name");
			final boolean _isDirectory = (boolean) _item.get("isDirectory");
			
			textview1.setText(_name);
			
			if (_isDirectory) {
				textview2.setVisibility(View.GONE);
				imageview1.setImageResource(R.drawable.folder_closed_icon);
			} else {
				textview2.setVisibility(View.VISIBLE);
				textview2.setText(_getFileSize(_path));
				
				String _fileName = _name != null ? _name.toLowerCase() : "";
				
				if (_fileName.endsWith(".html")) {
					imageview1.setImageResource(R.drawable.html_icon);
				} else if (_fileName.endsWith(".css")) {
					imageview1.setImageResource(R.drawable.css_icon);
				} else if (_fileName.endsWith(".gradle")) {
					imageview1.setImageResource(R.drawable.gradle_icon);
				} else if (_fileName.endsWith(".java")) {
					imageview1.setImageResource(R.drawable.java_icon);
				} else if (_fileName.endsWith(".js")) {
					imageview1.setImageResource(R.drawable.js_icon);
				} else if (_fileName.endsWith(".json")) {
					imageview1.setImageResource(R.drawable.json_icon);
				} else if (_fileName.endsWith(".xml")) {
					imageview1.setImageResource(R.drawable.xml_icon);
				} else if (_fileName.endsWith(".png") || _fileName.endsWith(".jpg") || _fileName.endsWith(".jpeg")) {
					imageview1.setImageResource(R.drawable.image_icon);
				} else {
					imageview1.setImageResource(R.drawable.file_icon);
				}
			}
			
			item_bg_cd.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					if (_isDirectory) {
						_onFolderItemClick(_path);
					} else {
						bridge._openFile(_path);
						_call_snackbar("Opened file in editor");
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
}
