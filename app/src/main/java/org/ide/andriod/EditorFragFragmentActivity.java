package org.ide.andriod;

import android.animation.*;
import android.app.*;
import android.content.*;
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
import com.google.android.material.card.*;
import com.google.android.material.divider.MaterialDivider;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;
import com.google.gson.*;
import io.github.rosemoe.sora.widget.CodeEditor;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.EditorColorScheme;
import io.github.rosemoe.sora.widget.schemes.HTMLScheme;
import io.github.rosemoe.sora.widget.schemes.SchemeDarcula;
import io.github.rosemoe.sora.widget.schemes.SchemeEclipse;
import io.github.rosemoe.sora.widget.schemes.SchemeGitHub;
import io.github.rosemoe.sora.widget.schemes.SchemeNotepadXX;
import io.github.rosemoe.sora.widget.schemes.SchemeVS2019;
import io.github.rosemoe.sora.langs.java.JavaLanguage;
import android.net.Uri;
import java.util.Timer;
import java.util.TimerTask;
import com.apk.builder.FileUtil;
import com.google.gson.reflect.TypeToken;
import io.github.rosemoe.sora.text.Cursor;


public class EditorFragFragmentActivity extends Fragment {
	
	private Timer _timer = new Timer();
	
	private HashMap<String, Object> config = new HashMap<>();
	private String minSdk = "";
	private String maxSdk = "";
	private String versionCode = "";
	private String versionName = "";
	private double tabNum = 0;
	private String path = "";
	private String name = "";
	private EditorBridge bridge;
	private String dataPath = "";
	
	private LinearLayout main;
	private LinearProgressIndicator progressbar1;
	private TabLayout tablayout1;
	private CodeEditor editor;
	private LinearLayout linear5;
	private LinearLayout SymbolBackGround;
	private ImageView imageview7;
	private TextView textview4;
	private MaterialDivider devider1;
	private LinearLayout linearBar;
	private HorizontalScrollView input_hscroll;
	private LinearLayout editor_input_inner_hscroll_L;
	private LinearLayout it1;
	private LinearLayout it2;
	private LinearLayout linear6;
	private LinearLayout linear8;
	private LinearLayout linear10;
	private LinearLayout linear12;
	private LinearLayout linear14;
	private LinearLayout linear16;
	private LinearLayout linear18;
	private LinearLayout linear20;
	private LinearLayout linear22;
	private LinearLayout linear24;
	private LinearLayout linear26;
	private LinearLayout linear28;
	private MaterialCardView editor_input_it_cd1;
	private LinearLayout linear9;
	private ImageView imageview8;
	private MaterialCardView editor_input_it_cd2;
	private LinearLayout linear7;
	private ImageView imageview9;
	private MaterialCardView editor_input_it_cd3;
	private LinearLayout linear11;
	private ImageView imageview10;
	private MaterialCardView editor_input_it_cd4;
	private LinearLayout linear13;
	private ImageView imageview5;
	private MaterialCardView editor_input_it_cd5;
	private LinearLayout linear15;
	private ImageView imageview11;
	private MaterialCardView editor_input_it_cd6;
	private LinearLayout linear17;
	private ImageView imageview12;
	private MaterialCardView editor_input_it_cd7;
	private LinearLayout linear19;
	private TextView textview6;
	private MaterialCardView editor_input_it_cd8;
	private LinearLayout linear21;
	private TextView textview2;
	private MaterialCardView editor_input_it_cd9;
	private LinearLayout linear23;
	private TextView textview3;
	private MaterialCardView editor_input_it_cd10;
	private LinearLayout linear25;
	private TextView textview5;
	private MaterialCardView editor_input_it_cd11;
	private LinearLayout linear27;
	private TextView textview7;
	private MaterialCardView editor_input_it_cd12;
	private LinearLayout linear29;
	private TextView textview8;
	private MaterialCardView editor_input_it_cd13;
	private LinearLayout linear30;
	private TextView textview9;
	private MaterialCardView editor_input_it_cd14;
	private LinearLayout linear31;
	private TextView textview10;
	
	private TimerTask t;
	
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.editor_frag_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		main = _view.findViewById(R.id.main);
		progressbar1 = _view.findViewById(R.id.progressbar1);
		tablayout1 = _view.findViewById(R.id.tablayout1);
		editor = _view.findViewById(R.id.editor);
		linear5 = _view.findViewById(R.id.linear5);
		SymbolBackGround = _view.findViewById(R.id.SymbolBackGround);
		imageview7 = _view.findViewById(R.id.imageview7);
		textview4 = _view.findViewById(R.id.textview4);
		devider1 = _view.findViewById(R.id.devider1);
		linearBar = _view.findViewById(R.id.linearBar);
		input_hscroll = _view.findViewById(R.id.input_hscroll);
		editor_input_inner_hscroll_L = _view.findViewById(R.id.editor_input_inner_hscroll_L);
		it1 = _view.findViewById(R.id.it1);
		it2 = _view.findViewById(R.id.it2);
		linear6 = _view.findViewById(R.id.linear6);
		linear8 = _view.findViewById(R.id.linear8);
		linear10 = _view.findViewById(R.id.linear10);
		linear12 = _view.findViewById(R.id.linear12);
		linear14 = _view.findViewById(R.id.linear14);
		linear16 = _view.findViewById(R.id.linear16);
		linear18 = _view.findViewById(R.id.linear18);
		linear20 = _view.findViewById(R.id.linear20);
		linear22 = _view.findViewById(R.id.linear22);
		linear24 = _view.findViewById(R.id.linear24);
		linear26 = _view.findViewById(R.id.linear26);
		linear28 = _view.findViewById(R.id.linear28);
		editor_input_it_cd1 = _view.findViewById(R.id.editor_input_it_cd1);
		linear9 = _view.findViewById(R.id.linear9);
		imageview8 = _view.findViewById(R.id.imageview8);
		editor_input_it_cd2 = _view.findViewById(R.id.editor_input_it_cd2);
		linear7 = _view.findViewById(R.id.linear7);
		imageview9 = _view.findViewById(R.id.imageview9);
		editor_input_it_cd3 = _view.findViewById(R.id.editor_input_it_cd3);
		linear11 = _view.findViewById(R.id.linear11);
		imageview10 = _view.findViewById(R.id.imageview10);
		editor_input_it_cd4 = _view.findViewById(R.id.editor_input_it_cd4);
		linear13 = _view.findViewById(R.id.linear13);
		imageview5 = _view.findViewById(R.id.imageview5);
		editor_input_it_cd5 = _view.findViewById(R.id.editor_input_it_cd5);
		linear15 = _view.findViewById(R.id.linear15);
		imageview11 = _view.findViewById(R.id.imageview11);
		editor_input_it_cd6 = _view.findViewById(R.id.editor_input_it_cd6);
		linear17 = _view.findViewById(R.id.linear17);
		imageview12 = _view.findViewById(R.id.imageview12);
		editor_input_it_cd7 = _view.findViewById(R.id.editor_input_it_cd7);
		linear19 = _view.findViewById(R.id.linear19);
		textview6 = _view.findViewById(R.id.textview6);
		editor_input_it_cd8 = _view.findViewById(R.id.editor_input_it_cd8);
		linear21 = _view.findViewById(R.id.linear21);
		textview2 = _view.findViewById(R.id.textview2);
		editor_input_it_cd9 = _view.findViewById(R.id.editor_input_it_cd9);
		linear23 = _view.findViewById(R.id.linear23);
		textview3 = _view.findViewById(R.id.textview3);
		editor_input_it_cd10 = _view.findViewById(R.id.editor_input_it_cd10);
		linear25 = _view.findViewById(R.id.linear25);
		textview5 = _view.findViewById(R.id.textview5);
		editor_input_it_cd11 = _view.findViewById(R.id.editor_input_it_cd11);
		linear27 = _view.findViewById(R.id.linear27);
		textview7 = _view.findViewById(R.id.textview7);
		editor_input_it_cd12 = _view.findViewById(R.id.editor_input_it_cd12);
		linear29 = _view.findViewById(R.id.linear29);
		textview8 = _view.findViewById(R.id.textview8);
		editor_input_it_cd13 = _view.findViewById(R.id.editor_input_it_cd13);
		linear30 = _view.findViewById(R.id.linear30);
		textview9 = _view.findViewById(R.id.textview9);
		editor_input_it_cd14 = _view.findViewById(R.id.editor_input_it_cd14);
		linear31 = _view.findViewById(R.id.linear31);
		textview10 = _view.findViewById(R.id.textview10);
		
		editor_input_it_cd1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				editor.undo();
			}
		});
		
		editor_input_it_cd2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				editor.redo();
			}
		});
		
		editor_input_it_cd3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		editor_input_it_cd4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		editor_input_it_cd5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		editor_input_it_cd6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		editor_input_it_cd7.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				insertAtCursor("(");
			}
		});
		
		editor_input_it_cd8.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				insertAtCursor(")"); 
			}
		});
		
		editor_input_it_cd9.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				insertAtCursor("{");
			}
		});
		
		editor_input_it_cd10.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				insertAtCursor("}"); 
			}
		});
		
		editor_input_it_cd11.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				insertAtCursor("<"); 
			}
		});
		
		editor_input_it_cd12.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				insertAtCursor(">"); 
			}
		});
		
		editor_input_it_cd13.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				insertAtCursor(","); 
			}
		});
		
		editor_input_it_cd14.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				insertAtCursor(";"); 
			}
		});
	}
	
	private void initializeLogic() {
		progressbar1.setVisibility(View.GONE);
		if (progressbar1.getVisibility() == View.GONE) {
			progressbar1.setIndeterminate(true);
			progressbar1.setVisibility(View.VISIBLE);
		}
		input_hscroll.setHorizontalScrollBarEnabled(false);
		input_hscroll.setVerticalScrollBarEnabled(false);
		input_hscroll.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
		path = getArguments() != null ? getArguments().getString("path") : "";
		name = getArguments() != null ? getArguments().getString("name") : "";
		dataPath = getArguments() != null ? getArguments().getString("dataPath") : "";
		if (dataPath == null || dataPath.equals("")) {
			dataPath = "/storage/emulated/0/.androIDE/data/".concat(name).concat("/");
		}
		bridge = ((EditorActivity) getActivity()).getBridge();
		
		final String _editorJsonPath = dataPath.concat("/editor/editorOpened.json");
		
		if (!FileUtil.isExistFile(_editorJsonPath)) {
			FileUtil.makeDir(dataPath.concat("/editor"));
			FileUtil.writeFile(_editorJsonPath, "[]");
		}
		
		if (FileUtil.isExistFile(_editorJsonPath) && !FileUtil.readFile(_editorJsonPath).equals("")) {
			t = new TimerTask() {
				@Override
				public void run() {
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							progressbar1.setVisibility(View.GONE);
							linear5.setVisibility(View.GONE);
							editor.setVisibility(View.VISIBLE);
						}
					});
				}
			};
			_timer.schedule(t, (int)(1000));
		} else {
			t = new TimerTask() {
				@Override
				public void run() {
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							linear5.setVisibility(View.VISIBLE);
							editor.setVisibility(View.GONE);
							progressbar1.setVisibility(View.GONE);
							com.google.android.material.snackbar.Snackbar.make(main, "editorOpened.json NOT FOUND", com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("OK", new View.OnClickListener(){
								@Override
								public void onClick(View _view) {
									
								}
							}).show();
						}
					});
				}
			};
			_timer.schedule(t, (int)(1000));
		}
		
		bridge._setFragmentListener(new EditorBridge.FragmentListener() {
			@Override
			public void _onTabsChanged(double _selectIndex) {
				_refreshTabItems(_selectIndex);
			}
			@Override
			public void _onFileSaved(String _path) {
				// currently no-op on the fragment side; hook for future use
			}
		});
		_editorSchematic();
		tablayout1.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				double tabPos = tab.getPosition();
				LinkedHashMap<String, HashMap<String, Object>> _listMap = bridge._getListMap();
				ArrayList<String> _tabKeys = new ArrayList<>(_listMap.keySet());
				if (tabPos >= 0 && tabPos < _tabKeys.size()) {
					String _key = _tabKeys.get((int) tabPos);
					String _path = _listMap.get(_key).get("path").toString();
					editor.setText(FileUtil.readFile(_path));
					tabNum = tabPos;
				}
			}
			@Override
			public void onTabUnselected(TabLayout.Tab tab) {
				LinkedHashMap<String, HashMap<String, Object>> _listMap = bridge._getListMap();
				ArrayList<String> _tabKeys = new ArrayList<>(_listMap.keySet());
				if (tabNum < 0 || tabNum >= _tabKeys.size()) return;
				String _key = _tabKeys.get((int) tabNum);
				final String _tabPath = _listMap.get(_key).get("path").toString();
				if (editor.getText().toString().equals(FileUtil.readFile(_tabPath))) {
					// dont do anything
				} else {
					final String _newCode = editor.getText().toString();
					new Thread(new Runnable() {
						@Override
						public void run() {
							FileUtil.writeFile(_tabPath, _newCode);
						}
					}).start();
					bridge._notifyFileSaved(_tabPath);
					com.google.android.material.snackbar.Snackbar.make(main, "Code was automatically saved!", com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("OK", new View.OnClickListener(){
						@Override
						public void onClick(View _view) {}
					}).show();
				}
			}
			@Override
			public void onTabReselected(TabLayout.Tab tab) {
				// no-op: tabs are now closed via long-press menu (_showTabOptionsMenu), not by reselecting
			}
		});
		linear5.setVisibility(View.GONE);
		_refreshTabItems(bridge._getListMap().size() - 1);
		boolean isDarkMode = (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
		
		View linearBar = getView() != null ? getView().findViewById(R.id.linearBar) : null;
		if (linearBar != null) {
			linearBar.setBackgroundColor(Color.parseColor(isDarkMode ? "#1C1B1F" : "#FFFBFE"));
		}
		
		// icon tints: reverse of the bar color (light icon on dark bar, dark icon on light bar)
		int iconTint = Color.parseColor(isDarkMode ? "#FFFBFE" : "#1C1B1F");
		
		int[] iconIds = new int[] {
			R.id.imageview8, R.id.imageview9, R.id.imageview10,
			R.id.imageview5, R.id.imageview11, R.id.imageview12
		};
		
		for (int id : iconIds) {
			ImageView iv = getView() != null ? getView().findViewById(id) : null;
			if (iv != null) {
				iv.setColorFilter(iconTint, PorterDuff.Mode.SRC_IN);
			}
		}
		EditorActivity activity = (EditorActivity) requireActivity();
		
		KeyboardVisibilityHelper helper = new KeyboardVisibilityHelper(requireActivity());
		helper.bindBottomHolder(requireActivity().findViewById(R.id.inner_run_holder));
		helper.bindEditor(editor);
		helper.setViewModeHidden(activity.isViewModeHidden());
		
		activity.setKeyboardHelper(helper);
		activity.getWordWrapHelper().bindEditor(editor);
		activity.getTextSizeHelper().bindEditor(editor);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
	}
	
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		bridge._clearFragmentListener();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		requireActivity().getWindow()
		.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
	}
	public void _jsonFileCheck() {
		try {
			String configPath = path.concat("/app_config.json");
			if (FileUtil.isExistFile(configPath)) {
				String jsonStr = FileUtil.readFile(configPath);
				if (jsonStr != null && !jsonStr.trim().isEmpty()) {
					java.lang.reflect.Type mapType = new com.google.gson.reflect.TypeToken<HashMap<String, Object>>(){}.getType();
					config = new com.google.gson.Gson().fromJson(jsonStr, mapType);
					
					if (config != null && !config.isEmpty()) {
						if (config.containsKey("minSdkVersion") && config.get("minSdkVersion") != null) {
							minSdk = String.valueOf((long)(Double.parseDouble(config.get("minSdkVersion").toString())));
						}
						if (config.containsKey("targetSdkVersion") && config.get("targetSdkVersion") != null) {
							maxSdk = String.valueOf((long)(Double.parseDouble(config.get("targetSdkVersion").toString())));
						}
						if (config.containsKey("versionCode") && config.get("versionCode") != null) {
							versionCode = String.valueOf((long)(Double.parseDouble(config.get("versionCode").toString())));
						}
						if (config.containsKey("versionName") && config.get("versionName") != null) {
							versionName = config.get("versionName").toString();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void _editorSchematic() {
		editor.setEditorLanguage(new JavaLanguage());
		editor.setTabWidth(4);
		editor.setLigatureEnabled(true);
		editor.showSoftInput();
		editor.setCursorWidth(4);
		editor.setPinLineNumber(true);
		editor.setNonPrintablePaintingFlags(
		CodeEditor.FLAG_DRAW_LINE_SEPARATOR |
		CodeEditor.FLAG_DRAW_WHITESPACE_LEADING
		);
		editor.setTypefaceText(Typeface.createFromAsset(getContext().getAssets(), "fonts/mono"+".ttf"));
		boolean isDarkMode = (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
		
		// Set the appropriate color scheme
		if (isDarkMode) {
			editor.setColorScheme(new org.ide.andriod.darkTheme());
		} else {
			editor.setColorScheme(new org.ide.andriod.theme());
		}
		editor.setOverScrollMode(View.OVER_SCROLL_NEVER);
		
		editor.setTextSize(16);
		editor.setTextSizePx(editor.getTextSizePx()); // freeze current pixel size
		
		editor.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return event.getPointerCount() > 1;
			}
		});
	}
	
	
	public void _refreshTabItems(final double _selectIndex) {
		tablayout1.removeAllTabs();
		
		LinkedHashMap<String, HashMap<String, Object>> _listMap = bridge._getListMap();
		ArrayList<String> _tabKeys = new ArrayList<>(_listMap.keySet());
		
		if (!_tabKeys.isEmpty()) {
			for (int ii = 0; ii < _tabKeys.size(); ii++) {
				tablayout1.addTab(tablayout1.newTab().setText(_tabKeys.get(ii)));
			}
			
			final ArrayList<String> _tabKeysFinal = _tabKeys;
			
			tablayout1.post(new Runnable() {
				@Override
				public void run() {
					android.view.ViewGroup _tabStrip = (android.view.ViewGroup) tablayout1.getChildAt(0);
					for (int ii = 0; ii < _tabStrip.getChildCount(); ii++) {
						final double _tabIndex = ii;
						final View _tabViewRef = _tabStrip.getChildAt(ii);
						
						// Force-reapply the label text. When all tabs were closed
						// and new ones are added right after, the TabView's internal
						// TextView sometimes doesn't pick up the text set via
						// newTab().setText(...), leaving the tab blank.
						if (_tabIndex < _tabKeysFinal.size()) {
							TabLayout.Tab _t = tablayout1.getTabAt((int) _tabIndex);
							if (_t != null) {
								_t.setText(_tabKeysFinal.get((int) _tabIndex));
							}
						}
						
						_tabViewRef.setOnLongClickListener(new View.OnLongClickListener() {
							@Override
							public boolean onLongClick(View _v) {
								_showTabOptionsMenu(_tabViewRef, _tabIndex);
								return true;
							}
						});
					}
				}
			});
			
			double _target = (_selectIndex >= 0 && _selectIndex < _tabKeys.size()) ? _selectIndex : _tabKeys.size() - 1;
			
			TabLayout.Tab _targetTab = tablayout1.getTabAt((int) _target);
			if (_targetTab != null) {
				_targetTab.select();
			}
			
			tabNum = _target;
			String _selectedKey = _tabKeys.get((int) _target);
			String _selectedPath = _listMap.get(_selectedKey).get("path").toString();
			editor.setText(FileUtil.readFile(_selectedPath));
		} else {
			editor.setText("");
			tabNum = 0;
		}
	}
	
	
	public void _showTabOptionsMenu(final View _tabView, final double _index) {
		try {
			android.widget.PopupMenu popupMenu = new android.widget.PopupMenu(getActivity(), _tabView);
			
			popupMenu.getMenu().add(android.view.Menu.NONE, 1, android.view.Menu.NONE, "Close Current");
			popupMenu.getMenu().add(android.view.Menu.NONE, 2, android.view.Menu.NONE, "Rename Current");
			popupMenu.getMenu().add(android.view.Menu.NONE, 3, android.view.Menu.NONE, "Delete Current");
			
			final double _menuIndex = _index;
			
			popupMenu.setOnMenuItemClickListener(new android.widget.PopupMenu.OnMenuItemClickListener() {
				@Override
				public boolean onMenuItemClick(android.view.MenuItem item) {
					int id = item.getItemId();
					
					if (id == 1) {
						_closeTabAnimated(_menuIndex);
						return true;
					}
					else if (id == 2) {
						// Rename Current - not implemented yet
						return true;
					}
					else if (id == 3) {
						// Delete Current - not implemented yet
						return true;
					}
					
					return false;
				}
			});
			
			popupMenu.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void _closeTabAnimated(final double _index) {
		if (_index < 0 || _index >= bridge._getListMap().size()) return;
		
		android.view.ViewGroup _tabStrip = (android.view.ViewGroup) tablayout1.getChildAt(0);
		final View _targetTabView = ((int)_index < _tabStrip.getChildCount()) ? _tabStrip.getChildAt((int)_index) : null;
		
		if (_targetTabView == null) {
			bridge._closeTab((int) _index);
			tablayout1.removeTabAt((int) _index);
			_refreshTabItems(_index - 1);
			return;
		}
		
		final double _closingIndex = _index;
		
		_targetTabView.animate()
		.alpha(0f)
		.scaleX(0.8f)
		.scaleY(0.8f)
		.setDuration(180)
		.withEndAction(new Runnable() {
			@Override
			public void run() {
				bridge._closeTab((int) _closingIndex);
				tablayout1.removeTabAt((int) _closingIndex);
				_refreshTabItems(_closingIndex - 1);
			}
		})
		.start();
	}
	
	
	public void _anchore() {
		
	}
    
    private void insertAtCursor(final String text) {
    Cursor cursor = editor.getCursor();
    int line = cursor.getLeftLine();
    int column = cursor.getLeftColumn();

    // If there's a selection, wrap it when an opener is typed
    if (cursor.isSelected() && getPairClose(text) != null) {
        wrapSelection(text, getPairClose(text));
        return;
    }

    // If typing a closer and the next char is already that closer, just skip over it
    if (isCloser(text) && !cursor.isSelected()) {
        String nextChar = getCharAt(line, column);
        if (text.equals(nextChar)) {
            editor.setSelection(line, column + 1);
            editor.requestFocus();
            return;
        }
    }

    String pairClose = getPairClose(text);

    if (pairClose != null) {
        String pair = text + pairClose;
        editor.getText().insert(line, column, pair);
        editor.setSelection(line, column + text.length());
    } else {
        editor.getText().insert(line, column, text);
        editor.setSelection(line, column + text.length());
    }

    editor.requestFocus();
}

private void wrapSelection(String open, String close) {
    Cursor cursor = editor.getCursor();
    int startLine = cursor.getLeftLine();
    int startColumn = cursor.getLeftColumn();
    int endLine = cursor.getRightLine();
    int endColumn = cursor.getRightColumn();

    editor.getText().insert(endLine, endColumn, close);
    editor.getText().insert(startLine, startColumn, open);

    int newStartColumn = startColumn + open.length();
    int newEndColumn = (startLine == endLine) ? endColumn + open.length() : endColumn;

    editor.setSelectionRegion(startLine, newStartColumn, endLine, newEndColumn);
    editor.requestFocus();
}

private String getCharAt(int line, int column) {
    CharSequence lineText = editor.getText().getLine(line);
    if (column < lineText.length()) {
        return String.valueOf(lineText.charAt(column));
    }
    return null;
}

private String getPairClose(String open) {
    switch (open) {
        case "{": return "}";
        case "(": return ")";
        case "[": return "]";
        case "<": return ">";
        default:  return null;
    }
}

private boolean isCloser(String text) {
    return text.equals("}") || text.equals(")") || text.equals("]") || text.equals(">");
}
	
}
