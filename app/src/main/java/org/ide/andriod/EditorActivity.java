package org.ide.andriod;

import android.Manifest;
import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.*;
import android.graphics.*;
import android.graphics.Typeface;
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
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.*;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.arch.core.*;
import androidx.constraintlayout.widget.*;
import androidx.coordinatorlayout.*;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.*;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.cursoradapter.*;
import androidx.customview.*;
import androidx.documentfile.*;
import androidx.drawerlayout.*;
import androidx.drawerlayout.widget.DrawerLayout;
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
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.vectordrawable.*;
import androidx.versionedparcelable.*;
import androidx.viewpager.*;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.card.*;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.divider.MaterialDivider;
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
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import android.view.LayoutInflater;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import android.content.Context;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.apk.builder.*;
import java.net.URISyntaxException;
import java.net.URI;
import java.io.File;
import com.apk.builder.model.Project;
import com.apk.builder.model.Library;
import com.apk.builder.FileUtil;
import com.apk.builder.logger.*;
import com.apk.builder.SystemLogPrinter;
import com.tyron.compiler.CompilerAsyncTask;
import androidx.appcompat.app.AppCompatDelegate;


public class EditorActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private DrawerLayout _drawer;
	private double position = 0;
	private HashMap<String, Object> map = new HashMap<>();
	private String pathForDrawer = "";
	private boolean isDarkMode = false;
	private HashMap<String, Object> config = new HashMap<>();
	private String minSdk = "";
	private String maxSdk = "";
	private String versionCode = "";
	private String versionName = "";
	private String resPath = "";
	private String javaPath = "";
	private String manifestPath = "";
	private String assetsPath = "";
	private String nativeLibsPath = "";
	private String localLibsPath = "";
	private String et_output = "";
	private Logger mLogger;
	private org.json.JSONArray darray = new org.json.JSONArray();
	private EditorBridge bridge = new EditorBridge();
	private GitBridge gitBridge = new GitBridge();
	private KeyboardVisibilityHelper keyboardHelper;
	private BuildHelper buildHelper;
	private com.google.android.material.snackbar.Snackbar buildSnackbar;
	private boolean isViewModeHidden = false;
	private WordWrapHelper wordWrapHelper;
	private TextSizeHelper textSizeHelper;
	
	private ArrayList<HashMap<String, Object>> listMap = new ArrayList<>();
	
	private LinearLayout linear2;
	private FrameLayout appFrame;
	private MaterialDivider materialDivider8;
	private LinearLayout inner_run_holder;
	private ImageView imageview1;
	private LinearLayout linear7;
	private LinearLayout linear3;
	private TextView textview1;
	private TextView textview5;
	private ImageView toggle_view_mode;
	private ImageView run_ic;
	private ImageView imageview3;
	private MaterialCardView edit_cd;
	private MaterialCardView file_cd;
	private MaterialCardView run_cd;
	private MaterialCardView ai_gen_cd;
	private MaterialCardView terminal_cd;
	private LinearLayout linear12;
	private ImageView imageview7;
	private LinearLayout linear13;
	private ImageView imageview5;
	private LinearLayout linear14;
	private ImageView imageview8;
	private LinearLayout linear35;
	private ImageView imageview12;
	private LinearLayout linear15;
	private ImageView imageview9;
	private LinearLayout _drawer_linear2;
	private LinearLayout _drawer_linear4;
	private TextView _drawer_textview1;
	private HorizontalScrollView _drawer_hscroll1;
	private RecyclerView _drawer_recyclerView;
	
	private TimerTask t;
	private Intent i = new Intent();
	private AlertDialog.Builder d;
	private SharedPreferences s;
	private SharedPreferences sharedPref;
    private Fragment editorFragment;
private Fragment filesFragment;
private Fragment runFragment;
private Fragment terminalFragment;
private Fragment aiGenFragment;
private androidx.appcompat.widget.AppCompatImageView _selectedTabIcon;
private int _selectedColorArgb;
private com.google.android.material.card.MaterialCardView _selectedCard;
private boolean _isDarkModeGlobal;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.editor);
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
		_app_bar = findViewById(R.id._app_bar);
		_coordinator = findViewById(R.id._coordinator);
		_toolbar = findViewById(R.id._toolbar);
		setSupportActionBar(_toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) {
				onBackPressed();
			}
		});
		_drawer = findViewById(R.id._drawer);
		ActionBarDrawerToggle _toggle = new ActionBarDrawerToggle(EditorActivity.this, _drawer, _toolbar, R.string.app_name, R.string.app_name);
		_drawer.addDrawerListener(_toggle);
		_toggle.syncState();
		
		LinearLayout _nav_view = findViewById(R.id._nav_view);
		
		linear2 = findViewById(R.id.linear2);
		appFrame = findViewById(R.id.appFrame);
		materialDivider8 = findViewById(R.id.materialDivider8);
		inner_run_holder = findViewById(R.id.inner_run_holder);
		imageview1 = findViewById(R.id.imageview1);
		linear7 = findViewById(R.id.linear7);
		linear3 = findViewById(R.id.linear3);
		textview1 = findViewById(R.id.textview1);
		textview5 = findViewById(R.id.textview5);
		toggle_view_mode = findViewById(R.id.toggle_view_mode);
		run_ic = findViewById(R.id.run_ic);
		imageview3 = findViewById(R.id.imageview3);
		edit_cd = findViewById(R.id.edit_cd);
		file_cd = findViewById(R.id.file_cd);
		run_cd = findViewById(R.id.run_cd);
		ai_gen_cd = findViewById(R.id.ai_gen_cd);
		terminal_cd = findViewById(R.id.terminal_cd);
		linear12 = findViewById(R.id.linear12);
		imageview7 = findViewById(R.id.imageview7);
		linear13 = findViewById(R.id.linear13);
		imageview5 = findViewById(R.id.imageview5);
		linear14 = findViewById(R.id.linear14);
		imageview8 = findViewById(R.id.imageview8);
		linear35 = findViewById(R.id.linear35);
		imageview12 = findViewById(R.id.imageview12);
		linear15 = findViewById(R.id.linear15);
		imageview9 = findViewById(R.id.imageview9);
		_drawer_linear2 = _nav_view.findViewById(R.id.linear2);
		_drawer_linear4 = _nav_view.findViewById(R.id.linear4);
		_drawer_textview1 = _nav_view.findViewById(R.id.textview1);
		_drawer_hscroll1 = _nav_view.findViewById(R.id.hscroll1);
		_drawer_recyclerView = _nav_view.findViewById(R.id.recyclerView);
		d = new AlertDialog.Builder(this);
		s = getSharedPreferences("s", Activity.MODE_PRIVATE);
		sharedPref = getSharedPreferences("sharedPref", Activity.MODE_PRIVATE);
		
		imageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_drawer.openDrawer(GravityCompat.START);
			}
		});
		
		run_ic.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (BuildHelper.isBuildRunning()) {
					_showBuildSnackbar();
					return;
				}
				
				buildHelper = new BuildHelper(
				EditorActivity.this,
				getIntent().getStringExtra("path"),
				"/storage/emulated/0/.androIDE/data/".concat(getIntent().getStringExtra("name")).concat("/")
				);
				
				buildHelper.setBuildListener(new BuildHelper.BuildListener() {
					@Override
					public void onBuildStarted() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								_showBuildSnackbar();
							}
						});
					}
					
					@Override
					public void onBuildSuccess(final String apkPath) {
						final String _finalApkPath = apkPath;
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								_dismissBuildSnackbar();
								com.google.android.material.snackbar.Snackbar.make(linear2, "Build successful!", com.google.android.material.snackbar.Snackbar.LENGTH_LONG)
								.setAction("View Logs", new View.OnClickListener() {
									@Override
									public void onClick(View _view) {
										_openBuildLogs();
									}
								}).show();
								_installApk(_finalApkPath);
							}
						});
					}
					
					@Override
					public void onBuildFailed(final String errorMessage) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								_dismissBuildSnackbar();
								com.google.android.material.snackbar.Snackbar.make(linear2, "Build failed: " + errorMessage, com.google.android.material.snackbar.Snackbar.LENGTH_LONG)
								.setAction("View Logs", new View.OnClickListener() {
									@Override
									public void onClick(View _view) {
										_openBuildLogs();
									}
								}).show();
							}
						});
					}
				});
				
				buildHelper.startBuild();
			}
		});
		
		imageview3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_menu1(imageview3);
			}
		});
	}
	
	private void initializeLogic() {
		bridge._setProjectPath(getIntent().getStringExtra("path"));
		bridge._setProjectName(getIntent().getStringExtra("name"));
		bridge._setProjectDataPath("/storage/emulated/0/.androIDE/data/".concat(getIntent().getStringExtra("name")).concat("/"));
		
		gitBridge._setProjectPath(getIntent().getStringExtra("path"));
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				final String _projectPath = getIntent().getStringExtra("path");
				if (!gitBridge._isRepo()) {
					String _gitignorePath = _projectPath.concat("/.gitignore");
					if (!FileUtil.isExistFile(_gitignorePath)) {
						String _defaultIgnore =
						"*.iml\n" +
						".gradle\n" +
						"/local.properties\n" +
						"/.idea\n" +
						".DS_Store\n" +
						"/build\n" +
						"/captures\n" +
						".externalNativeBuild\n" +
						".cxx\n" +
						"local.properties\n";
						FileUtil.writeFile(_gitignorePath, _defaultIgnore);
					}
					gitBridge._init();
				}
			}
		}).start();
		AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
		_initializeCrashLogger();
		ActionBarDrawerToggle _toggle = new ActionBarDrawerToggle(this, _drawer, R.string.app_name, R.string.app_name) {
			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
				super.onDrawerSlide(drawerView, slideOffset);
				float slideX = drawerView.getWidth() * slideOffset;
				_coordinator.setTranslationX(slideX);
			}
		};
		
		_drawer.addDrawerListener(_toggle);
		getWindow().setNavigationBarColor(Color.TRANSPARENT);
		getWindow().setStatusBarColor(Color.TRANSPARENT);
		// TreeView
		
		pathForDrawer = getIntent().getStringExtra("path");
		loadFilesTotree(pathForDrawer, _drawer_recyclerView, Uri.parse(pathForDrawer).getLastPathSegment());
		final boolean _isDarkMode = (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
		final int _selectedColor = Color.parseColor(_isDarkMode ? "#374955" : "#D1E5F4");
		final int _transparentColor = Color.parseColor("#00000000");
		final int _iconColor = Color.parseColor(_isDarkMode ? "#D1E5F4" : "#091E28");
		imageview7.setColorFilter(_iconColor, PorterDuff.Mode.SRC_IN);
		imageview5.setColorFilter(_iconColor, PorterDuff.Mode.SRC_IN);
		imageview8.setColorFilter(_iconColor, PorterDuff.Mode.SRC_IN);
		imageview12.setColorFilter(_iconColor, PorterDuff.Mode.SRC_IN);
		imageview9.setColorFilter(_iconColor, PorterDuff.Mode.SRC_IN);
		editorFragment = new EditorFragFragmentActivity();
		filesFragment = new FilesFragFragmentActivity();
		runFragment = new RunFragFragmentActivity();
		terminalFragment = new TerminalFragFragmentActivity();
		aiGenFragment = new AiGenFragFragmentActivity();
		Bundle _tabArgs = new Bundle();
		_tabArgs.putString("path", getIntent().getStringExtra("path"));
		_tabArgs.putString("name", getIntent().getStringExtra("name"));
		editorFragment.setArguments(_tabArgs);
		filesFragment.setArguments(_tabArgs);
		runFragment.setArguments(_tabArgs);
		terminalFragment.setArguments(_tabArgs);
		aiGenFragment.setArguments(_tabArgs);
		getSupportFragmentManager().beginTransaction().add(R.id.appFrame, aiGenFragment, "ai_gen").hide(aiGenFragment).add(R.id.appFrame, terminalFragment, "terminal").hide(terminalFragment).add(R.id.appFrame, runFragment, "run").hide(runFragment).add(R.id.appFrame, filesFragment, "files").hide(filesFragment).add(R.id.appFrame, editorFragment, "editor").commit();
		edit_cd.setCardBackgroundColor(_selectedColor);
		file_cd.setCardBackgroundColor(_transparentColor);
		run_cd.setCardBackgroundColor(_transparentColor);
		ai_gen_cd.setCardBackgroundColor(_transparentColor);
		terminal_cd.setCardBackgroundColor(_transparentColor);
		_selectedCard = edit_cd;
		_isDarkModeGlobal = _isDarkMode;
		
		wordWrapHelper = new WordWrapHelper(this);
		
		isViewModeHidden = getSharedPreferences("KeyboardVisibilityHelperPrefs", Activity.MODE_PRIVATE)
		.getBoolean("view_mode_hidden", false);
		if (isViewModeHidden) {
			inner_run_holder.setVisibility(View.GONE);
			toggle_view_mode.setImageResource(R.drawable.icon_fullscreen_round);
		}
		
		
		textSizeHelper = new TextSizeHelper(this);
		
		edit_cd.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { _selectTab(edit_cd, editorFragment, "editor"); } });
		file_cd.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { _selectTab(file_cd, filesFragment, "files"); } });
		run_cd.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { _selectTab(run_cd, runFragment, "run"); } });
		ai_gen_cd.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { _selectTab(ai_gen_cd, aiGenFragment, "ai_gen"); } });
		terminal_cd.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { _selectTab(terminal_cd, terminalFragment, "terminal"); } });
		boolean isDarkMode = (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
		
		if (inner_run_holder != null) {
			inner_run_holder.setBackgroundColor(Color.parseColor(isDarkMode ? "#1C1B1F" : "#FFFBFE"));
		}
		toggle_view_mode.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				isViewModeHidden = !isViewModeHidden;
				if (keyboardHelper != null) {
					keyboardHelper.setViewModeHidden(isViewModeHidden);
				}
				if (isViewModeHidden) {
					inner_run_holder.setVisibility(View.GONE);
					toggle_view_mode.setImageResource(R.drawable.icon_fullscreen_round);
				} else {
					inner_run_holder.setVisibility(View.VISIBLE);
					toggle_view_mode.setImageResource(R.drawable.icon_fullscreen_exit_round);
				}
			}
		});
	}
	
	@Override
	public void onStart() {
		super.onStart();
		if (config.isEmpty()) {
			// dont do anything
			_toolbar.setVisibility(View.GONE);
		} else {
			_toolbar.setVisibility(View.GONE);
		}
		textview5.setText(getIntent().getStringExtra("name"));
	}
	
	
	@Override
	public void onBackPressed() {
		MaterialAlertDialogBuilder d = new MaterialAlertDialogBuilder(EditorActivity.this);
		d.setTitle("Leave Editor?");
		d.setMessage("Are you sure you want to leave the editor?");
		d.setPositiveButton("Ok", new android.content.DialogInterface.OnClickListener() {
			@Override
			public void onClick(android.content.DialogInterface _dialog, int _which) {
				s.edit().remove("zz").commit();
				i.setClass(getApplicationContext(), MinimalMainActivity.class);
				startActivity(i);
				finish();
			}
		});
		d.setNegativeButton("Cancel", new android.content.DialogInterface.OnClickListener() {
			@Override
			public void onClick(android.content.DialogInterface _dialog, int _which) {
				
			}
		});
		d.create().show();
		
	}
	public void _EditorActivity() {
		
	}
    
public EditorBridge getBridge() {
    return bridge;
}

public GitBridge getGitBridge() {
	return gitBridge;
}

	
	
	public void _DrawerFolderOnClick(final String _path) {
		
	}
	
	
	public void _DrawerFolderOnLongClicked(final String _path) {
		MaterialAlertDialogBuilder d = new MaterialAlertDialogBuilder(EditorActivity.this);
		d.setTitle("What is it?");
		d.setMessage("Do you want to create a file or s folder?");
		d.setPositiveButton("File", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				//layout xml
				
				View alert = getLayoutInflater().inflate(R.layout.edit, null);
				
				MaterialAlertDialogBuilder d = new MaterialAlertDialogBuilder(EditorActivity.this);
				d.setTitle("Create a file");
				
				d.setView(alert);
				
				d.setMessage("File name: ");
				final TextInputEditText val = (TextInputEditText)alert.findViewById(R.id.edittext1);
				d.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						SketchwareUtil.showMessage(getApplicationContext(), _path.concat("/".concat(val.getText().toString())));
						loadFilesTotree(pathForDrawer, _drawer_recyclerView, Uri.parse(pathForDrawer).getLastPathSegment());
						com.google.android.material.snackbar.Snackbar.make(linear2, "File created successfully!", com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("OK", new View.OnClickListener(){
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
		d.setNeutralButton("Folder", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				//layout xml
				
				View alert = getLayoutInflater().inflate(R.layout.edit, null);
				
				MaterialAlertDialogBuilder d = new MaterialAlertDialogBuilder(EditorActivity.this);
				d.setTitle("Create a folder");
				
				d.setView(alert);
				
				d.setMessage("Folder name: ");
				final TextInputEditText val = (TextInputEditText)alert.findViewById(R.id.edittext1);
				d.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						FileUtil.makeDir(_path.concat("/".concat(val.getText().toString())));
						loadFilesTotree(pathForDrawer, _drawer_recyclerView, Uri.parse(pathForDrawer).getLastPathSegment());
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
	
	
	public void _DrawerOnFileLongClick(final String _path) {
		MaterialAlertDialogBuilder d = new MaterialAlertDialogBuilder(EditorActivity.this);
		d.setTitle("What do you want to do now?");
		d.setMessage("Do you want to delete this file?");
		d.setPositiveButton("Yeah", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				FileUtil.deleteFile(_path);
				loadFilesTotree(pathForDrawer, _drawer_recyclerView, Uri.parse(pathForDrawer).getLastPathSegment());
				SketchwareUtil.showMessage(getApplicationContext(), "File deleted successfully!");
			}
		});
		d.setNeutralButton("Nah", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				
			}
		});
		d.create().show();
	}
	
	
	public void _DrawerFileOnClick(final String _path) {
		_drawer.closeDrawer(GravityCompat.START);
		
		int _result = bridge._openFile(_path);
		
		switch (_result) {
			case EditorBridge.RESULT_ALREADY_OPEN:
			com.google.android.material.snackbar.Snackbar.make(linear2, "ERROR 99: ALREADY OPENED FILE", com.google.android.material.snackbar.Snackbar.LENGTH_SHORT)
			.setAction("", new View.OnClickListener(){
				@Override
				public void onClick(View _view) {}
			}).show();
			break;
			case EditorBridge.RESULT_JSON_NOT_FOUND:
			com.google.android.material.snackbar.Snackbar.make(linear2, "ERROR 03: editorOpened.json NOT FOUND", com.google.android.material.snackbar.Snackbar.LENGTH_SHORT)
			.setAction("Ok", new View.OnClickListener(){
				@Override
				public void onClick(View _view) {}
			}).show();
			break;
			case EditorBridge.RESULT_OPENED:
			// fragment refreshes itself via the bridge listener
			break;
		}
	}
	
	
	public void _Extra() {
	}
	
	private TreeViewList.TreeViewAdapter adapter;
	private List<TreeViewList.TreeNode> nodes2;
	private TreeViewList.TreeNode<TreeViewList.Dir> node;
	
	public void loadFilesTotree(String path, final RecyclerView recycler, String rootFolderName){
		
		TreeViewList.isPath = true;
		
		nodes2 = new ArrayList<>();
		node = new TreeViewList.TreeNode<>(new TreeViewList.Dir(rootFolderName));
		node.expand();
		nodes2.add(node);
		
		recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
		
		adapter = new TreeViewList.TreeViewAdapter(nodes2, Arrays.asList(new TreeViewList.FileNodeBinder(), new TreeViewList.DirectoryNodeBinder()));
		adapter.setOnTreeNodeListener(new TreeViewList.TreeViewAdapter.OnTreeNodeListener() {
			@Override
			public boolean onClick(String clickedPath, TreeViewList.TreeNode node, RecyclerView.ViewHolder holder) {
				if (!node.isLeaf()) {
					// Update and toggle the node.
					onToggle(!node.isExpand(), holder);
				}
				
				if (FileUtil.isFile(clickedPath)) {
					_DrawerFileOnClick(clickedPath);
				}
				else {
					if (FileUtil.isDirectory(clickedPath)) {
						_DrawerFolderOnClick(clickedPath);
					}
				}
				
				return false;
			}
			
			@Override
			public void onToggle(boolean isExpand, RecyclerView.ViewHolder holder) {
				TreeViewList.DirectoryNodeBinder.ViewHolder dirViewHolder = (TreeViewList.DirectoryNodeBinder.ViewHolder) holder;
				final ImageView ivArrow = dirViewHolder.getIvArrow();
				final ImageView ivFolder = dirViewHolder.getIvFolder();
				// Set absolute rotation so the icon always matches the real state.
				ivArrow.animate().rotation(isExpand ? 90 : 0).setDuration(180).start();
				ivFolder.setImageDrawable(ContextCompat.getDrawable(EditorActivity.this, isExpand ? R.drawable.folder_open_icon : R.drawable.folder_closed_icon));
			}
			
			@Override
			public void onLongClick(String clickedPath){
				if (FileUtil.isFile(clickedPath)) {
					_DrawerOnFileLongClick(clickedPath);
				}
				else {
					if (FileUtil.isDirectory(clickedPath)) {
						_DrawerFolderOnLongClicked(clickedPath);
					}
				}
			}
		});
		recycler.setAdapter(adapter);
		
		// Build the whole tree off the UI thread first, then refresh the
		// adapter so the expanded state of the tree and the icons stay in sync.
		initData2(path, node, new Runnable() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						adapter.refresh(nodes2);
					}
				});
			}
		});
	}
	
	public void initData2(String path, final TreeViewList.TreeNode<TreeViewList.Dir> dir, final Runnable onComplete){
		
		final String[] pathStr = {path};
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				ArrayList<String> rootDir = new ArrayList<>();
				
				FileUtil.listDir(pathStr[0], rootDir);
				
				for (String one : rootDir){
					if (FileUtil.isFile(one)){
						dir.addChild(new TreeViewList.TreeNode<>(new TreeViewList.File(one)));
					} else if (FileUtil.isDirectory(one)) {
						TreeViewList.TreeNode<TreeViewList.Dir> dirTree = new TreeViewList.TreeNode<>(new TreeViewList.Dir(one));
						dir.addChild(dirTree);
						initData2(one, dirTree, null);
					}
				}
				
				if (onComplete != null) {
					onComplete.run();
				}
			}
		}).start();
	}
	
	
	
	
	public static class TreeViewList {
		
		public static boolean isPath = false;
		public static boolean darkMode = false;
		
		
		public static class TreeNode<T extends LayoutItemType> implements Cloneable {
			private T content;
			private TreeNode parent;
			private List<TreeNode> childList;
			private boolean isExpand;
			private boolean isLocked;
			//the tree high
			private int height = UNDEFINE;
			
			private static final int UNDEFINE = -1;
			
			public TreeNode(@NonNull T content) {
				this.content = content;
				this.childList = new ArrayList<>();
			}
			
			public int getHeight() {
				if (isRoot())
				height = 0;
				else if (height == UNDEFINE)
				height = parent.getHeight() + 1;
				return height;
			}
			
			public boolean isRoot() {
				return parent == null;
			}
			
			public boolean isLeaf() {
				return childList == null || childList.isEmpty();
			}
			
			public void setContent(T content) {
				this.content = content;
			}
			
			public T getContent() {
				return content;
			}
			
			public List<TreeNode> getChildList() {
				return childList;
			}
			
			public void setChildList(List<TreeNode> childList) {
				this.childList.clear();
				for (TreeNode treeNode : childList) {
					addChild(treeNode);
				}
			}
			
			public TreeNode addChild(TreeNode node) {
				if (childList == null)
				childList = new ArrayList<>();
				childList.add(node);
				node.parent = this;
				return this;
			}
			
			public boolean toggle() {
				isExpand = !isExpand;
				return isExpand;
			}
			
			public void collapse() {
				if (isExpand) {
					isExpand = false;
				}
			}
			
			public void collapseAll() {
				if (childList == null || childList.isEmpty()) {
					return;
				}
				for (TreeNode child : this.childList) {
					child.collapseAll();
				}
			}
			
			public void expand() {
				if (!isExpand) {
					isExpand = true;
				}
			}
			
			public void expandAll() {
				expand();
				if (childList == null || childList.isEmpty()) {
					return;
				}
				for (TreeNode child : this.childList) {
					child.expandAll();
				}
			}
			
			public boolean isExpand() {
				return isExpand;
			}
			
			public void setParent(TreeNode parent) {
				this.parent = parent;
			}
			
			public TreeNode getParent() {
				return parent;
			}
			
			public TreeNode<T> lock() {
				isLocked = true;
				return this;
			}
			
			public TreeNode<T> unlock() {
				isLocked = false;
				return this;
			}
			
			public boolean isLocked() {
				return isLocked;
			}
			
			@Override
			public String toString() {
				return "TreeNode{" +
				"content=" + this.content +
				", parent=" + (parent == null ? "null" : parent.getContent().toString()) +
				", childList=" + (childList == null ? "null" : childList.toString()) +
				", isExpand=" + isExpand +
				'}';
			}
			
			@Override
			protected TreeNode<T> clone() throws CloneNotSupportedException {
				TreeNode<T> clone = new TreeNode<>(this.content);
				clone.isExpand = this.isExpand;
				return clone;
			}
		}
		
		
		//interface
		public interface LayoutItemType {
			int getLayoutId();
		}
		
		
		// Tree View Adapter
		
		
		public static class TreeViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
			private static final String KEY_IS_EXPAND = "IS_EXPAND";
			private final List<? extends TreeViewBinder> viewBinders;
			private List<TreeNode> displayNodes;
			private int padding = 30;
			private OnTreeNodeListener onTreeNodeListener;
			private boolean toCollapseChild;
			
			public TreeViewAdapter(List<? extends TreeViewBinder> viewBinders) {
				this(null, viewBinders);
			}
			
			public TreeViewAdapter(List<TreeNode> nodes, List<? extends TreeViewBinder> viewBinders) {
				displayNodes = new ArrayList<>();
				if (nodes != null)
				findDisplayNodes(nodes);
				this.viewBinders = viewBinders;
			}
			
			private void findDisplayNodes(List<TreeNode> nodes) {
				for (TreeNode node : nodes) {
					displayNodes.add(node);
					if (!node.isLeaf() && node.isExpand())
					findDisplayNodes(node.getChildList());
				}
			}
			
			@Override
			public int getItemViewType(int position) {
				return displayNodes.get(position).getContent().getLayoutId();
			}
			
			@Override
			public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
				View v = LayoutInflater.from(parent.getContext())
				.inflate(viewType, parent, false);
				if (viewBinders.size() == 1)
				return viewBinders.get(0).provideViewHolder(v);
				for (TreeViewBinder viewBinder : viewBinders) {
					if (viewBinder.getLayoutId() == viewType)
					return viewBinder.provideViewHolder(v);
				}
				return viewBinders.get(0).provideViewHolder(v);
			}
			
			@Override
			public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
				if (payloads != null && !payloads.isEmpty()) {
					Bundle b = (Bundle) payloads.get(0);
					for (String key : b.keySet()) {
						switch (key) {
							case KEY_IS_EXPAND:
							if (onTreeNodeListener != null)
							onTreeNodeListener.onToggle(b.getBoolean(key), holder);
							break;
						}
					}
				}
				super.onBindViewHolder(holder, position, payloads);
			}
			
			@Override
			public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
				
				holder.itemView.setPaddingRelative(displayNodes.get(position).getHeight() * padding, 3, 3, 3);
				
				
				final TextView txt = holder.itemView.findViewById(R.id.tv_name);
				
				
				final String clickedPath[] = {""};
				
				holder.itemView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						TreeNode selectedNode = displayNodes.get(holder.getLayoutPosition());
						// Prevent multi-click during the short interval.
						try {
							long lastClickTime = (long) holder.itemView.getTag();
							if (System.currentTimeMillis() - lastClickTime < 500)
							return;
						} catch (Exception e) {
							holder.itemView.setTag(System.currentTimeMillis());
						}
						holder.itemView.setTag(System.currentTimeMillis());
						
						
						
						
						
						try {
							Dir dirNode = (Dir) selectedNode.getContent();
							clickedPath[0] = dirNode.dirName;
						} catch (Exception e){
							File fileNode = (File) selectedNode.getContent();
							clickedPath[0] = fileNode.fileName;
						}
						
						if (onTreeNodeListener != null && onTreeNodeListener.onClick(clickedPath[0],
						selectedNode, holder))
						return;
						if (selectedNode.isLeaf())
						return;
						// This TreeNode was locked to click.
						if (selectedNode.isLocked()) return;
						boolean isExpand = selectedNode.isExpand();
						int positionStart = displayNodes.indexOf(selectedNode) + 1;
						if (!isExpand) {
							notifyItemRangeInserted(positionStart, addChildNodes(selectedNode, positionStart));
						} else {
							notifyItemRangeRemoved(positionStart, removeChildNodes(selectedNode, true));
						}
					}
				});
				
				
				holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
					@Override
					public boolean onLongClick(View v) {
						TreeNode selectedNode = displayNodes.get(holder.getLayoutPosition());
						
						try {
							Dir dirNode = (Dir) selectedNode.getContent();
							clickedPath[0] = dirNode.dirName;
						} catch (Exception e){
							File fileNode = (File) selectedNode.getContent();
							clickedPath[0] = fileNode.fileName;
						}
						
						onTreeNodeListener.onLongClick(clickedPath[0]);
						
						
						return true;
					}
				});
				
				
				for (TreeViewBinder viewBinder : viewBinders) {
					if (viewBinder.getLayoutId() == displayNodes.get(position).getContent().getLayoutId())
					viewBinder.bindView(holder, position, displayNodes.get(position));
				}
			}
			
			private int addChildNodes(TreeNode pNode, int startIndex) {
				List<TreeNode> childList = pNode.getChildList();
				int addChildCount = 0;
				for (TreeNode treeNode : childList) {
					displayNodes.add(startIndex + addChildCount++, treeNode);
					if (treeNode.isExpand()) {
						addChildCount += addChildNodes(treeNode, startIndex + addChildCount);
					}
				}
				if (!pNode.isExpand())
				pNode.toggle();
				return addChildCount;
			}
			
			private int removeChildNodes(TreeNode pNode) {
				return removeChildNodes(pNode, true);
			}
			
			private int removeChildNodes(TreeNode pNode, boolean shouldToggle) {
				if (pNode.isLeaf())
				return 0;
				List<TreeNode> childList = pNode.getChildList();
				int removeChildCount = childList.size();
				displayNodes.removeAll(childList);
				for (TreeNode child : childList) {
					if (child.isExpand()) {
						if (toCollapseChild)
						child.toggle();
						removeChildCount += removeChildNodes(child, false);
					}
				}
				if (shouldToggle)
				pNode.toggle();
				return removeChildCount;
			}
			
			@Override
			public int getItemCount() {
				return displayNodes == null ? 0 : displayNodes.size();
			}
			
			public void setPadding(int padding) {
				this.padding = padding;
			}
			
			public void ifCollapseChildWhileCollapseParent(boolean toCollapseChild) {
				this.toCollapseChild = toCollapseChild;
			}
			
			public void setOnTreeNodeListener(OnTreeNodeListener onTreeNodeListener) {
				this.onTreeNodeListener = onTreeNodeListener;
			}
			
			public interface OnTreeNodeListener {
				/**
             * called when TreeNodes were clicked.
             * @return weather consume the click event.
             */
				boolean onClick(String clickedPath, TreeNode node, RecyclerView.ViewHolder holder);
				
				/**
             * called when TreeNodes were toggle.
             * @param isExpand the status of TreeNodes after being toggled.
             */
				void onToggle(boolean isExpand, RecyclerView.ViewHolder holder);
				
				
				//long clickedPath
				void onLongClick(String clickedPath);
			}
			
			public void refresh(List<TreeNode> treeNodes) {
				displayNodes.clear();
				findDisplayNodes(treeNodes);
				notifyDataSetChanged();
			}
			
			public Iterator<TreeNode> getDisplayNodesIterator() {
				return displayNodes.iterator();
			}
			
			private void notifyDiff(final List<TreeNode> temp) {
				DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
					@Override
					public int getOldListSize() {
						return temp.size();
					}
					
					@Override
					public int getNewListSize() {
						return displayNodes.size();
					}
					
					// judge if the same items
					@Override
					public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
						return TreeViewAdapter.this.areItemsTheSame(temp.get(oldItemPosition), displayNodes.get(newItemPosition));
					}
					
					// if they are the same items, whether the contents has bean changed.
					@Override
					public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
						return TreeViewAdapter.this.areContentsTheSame(temp.get(oldItemPosition), displayNodes.get(newItemPosition));
					}
					
					@Nullable
					@Override
					public Object getChangePayload(int oldItemPosition, int newItemPosition) {
						return TreeViewAdapter.this.getChangePayload(temp.get(oldItemPosition), displayNodes.get(newItemPosition));
					}
				});
				diffResult.dispatchUpdatesTo(this);
			}
			
			private Object getChangePayload(TreeNode oldNode, TreeNode newNode) {
				Bundle diffBundle = new Bundle();
				if (newNode.isExpand() != oldNode.isExpand()) {
					diffBundle.putBoolean(KEY_IS_EXPAND, newNode.isExpand());
				}
				if (diffBundle.size() == 0)
				return null;
				return diffBundle;
			}
			
			// For DiffUtil, if they are the same items, whether the contents has bean changed.
			private boolean areContentsTheSame(TreeNode oldNode, TreeNode newNode) {
				return oldNode.getContent() != null && oldNode.getContent().equals(newNode.getContent())
				&& oldNode.isExpand() == newNode.isExpand();
			}
			
			// judge if the same item for DiffUtil
			private boolean areItemsTheSame(TreeNode oldNode, TreeNode newNode) {
				return oldNode.getContent() != null && oldNode.getContent().equals(newNode.getContent());
			}
			
			/**
         * collapse all root nodes.
         */
			public void collapseAll() {
				// Back up the nodes are displaying.
				List<TreeNode> temp = backupDisplayNodes();
				//find all root nodes.
				List<TreeNode> roots = new ArrayList<>();
				for (TreeNode displayNode : displayNodes) {
					if (displayNode.isRoot())
					roots.add(displayNode);
				}
				//Close all root nodes.
				for (TreeNode root : roots) {
					if (root.isExpand())
					removeChildNodes(root);
				}
				notifyDiff(temp);
			}
			
			@NonNull
			private List<TreeNode> backupDisplayNodes() {
				List<TreeNode> temp = new ArrayList<>();
				for (TreeNode displayNode : displayNodes) {
					try {
						temp.add(displayNode.clone());
					} catch (CloneNotSupportedException e) {
						temp.add(displayNode);
					}
				}
				return temp;
			}
			
			public void collapseNode(TreeNode pNode) {
				List<TreeNode> temp = backupDisplayNodes();
				removeChildNodes(pNode);
				notifyDiff(temp);
			}
			
			public void collapseBrotherNode(TreeNode pNode) {
				List<TreeNode> temp = backupDisplayNodes();
				if (pNode.isRoot()) {
					List<TreeNode> roots = new ArrayList<>();
					for (TreeNode displayNode : displayNodes) {
						if (displayNode.isRoot())
						roots.add(displayNode);
					}
					//Close all root nodes.
					for (TreeNode root : roots) {
						if (root.isExpand() && !root.equals(pNode))
						removeChildNodes(root);
					}
				} else {
					TreeNode parent = pNode.getParent();
					if (parent == null)
					return;
					List<TreeNode> childList = parent.getChildList();
					for (TreeNode node : childList) {
						if (node.equals(pNode) || !node.isExpand())
						continue;
						removeChildNodes(node);
					}
				}
				notifyDiff(temp);
			}
			
		}
		
		
		// Tree View Binder
		
		public static abstract class TreeViewBinder<VH extends RecyclerView.ViewHolder> implements LayoutItemType {
			public abstract VH provideViewHolder(View itemView);
			
			public abstract void bindView(VH holder, int position, TreeNode node);
			
			public static class ViewHolder extends RecyclerView.ViewHolder {
				public ViewHolder(View rootView) {
					super(rootView);
				}
				
				protected <T extends View> T findViewById(@IdRes int id) {
					return (T) itemView.findViewById(id);
				}
			}
			
		}
		
		
		public static class FileNodeBinder extends TreeViewBinder<FileNodeBinder.ViewHolder> {
    @Override
    public ViewHolder provideViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void bindView(ViewHolder holder, int position, TreeNode node) {
        File fileNode = (File) node.getContent();
        String fileName;

        if (TreeViewList.isPath) {
            fileName = Uri.parse(fileNode.fileName).getLastPathSegment();
        } else {
            fileName = fileNode.fileName;
        }

        holder.tvName.setText(fileName);

        // 🔑 Icon logic based on extension
        if (fileName.endsWith(".html")) {
            holder.ivIcon.setImageResource(R.drawable.html_icon);
        } else if (fileName.endsWith(".css")) {
            holder.ivIcon.setImageResource(R.drawable.css_icon);
        } else if (fileName.endsWith(".gradle")) {
            holder.ivIcon.setImageResource(R.drawable.gradle_icon);
        } else if (fileName.endsWith(".java")) {
            holder.ivIcon.setImageResource(R.drawable.java_icon);
        } else if (fileName.endsWith(".js")) {
            holder.ivIcon.setImageResource(R.drawable.js_icon);
        } else if (fileName.endsWith(".json")) {
            holder.ivIcon.setImageResource(R.drawable.json_icon);
        } else if (fileName.endsWith(".xml")) {
            holder.ivIcon.setImageResource(R.drawable.xml_icon);
        } else if (fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            holder.ivIcon.setImageResource(R.drawable.image_icon);
        } else if (fileName.equalsIgnoreCase(".gitignore") || fileName.endsWith(".git")) {
            holder.ivIcon.setImageResource(R.drawable.git_icon);
        } else {
            holder.ivIcon.setImageResource(R.drawable.file_icon); // fallback generic icon
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_file;
    }

    public class ViewHolder extends TreeViewBinder.ViewHolder {
        public TextView tvName;
        public ImageView ivIcon; // 👈 reference to imageview1 in your layout

        public ViewHolder(View rootView) {
            super(rootView);
            this.tvName = rootView.findViewById(R.id.tv_name);
            this.ivIcon = rootView.findViewById(R.id.imageview1); // use imageview1 from XML
        }
    }
}
		
		
		public static class DirectoryNodeBinder extends TreeViewBinder<DirectoryNodeBinder.ViewHolder> {
    @Override
    public ViewHolder provideViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void bindView(ViewHolder holder, int position, TreeNode node) {
        // Arrow rotation
        holder.ivArrow.setRotation(0);
        int rotateDegree = node.isExpand() ? 90 : 0;
        holder.ivArrow.setRotation(rotateDegree);

        // Folder name
        Dir dirNode = (Dir) node.getContent();
        String dirName = TreeViewList.isPath
                ? Uri.parse(dirNode.dirName).getLastPathSegment()
                : dirNode.dirName;
        holder.tvName.setText(dirName);

        // 🔑 Folder icon logic
        if (node.isExpand()) {
            holder.ivFolder.setImageDrawable(
                ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.folder_open_icon)
            );
        } else {
            holder.ivFolder.setImageDrawable(
                ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.folder_closed_icon)
            );
        }

        // Arrow visibility
        if (node.isLeaf()) {
            holder.ivArrow.setVisibility(View.INVISIBLE);
        } else {
            holder.ivArrow.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_dir;
    }

    public static class ViewHolder extends TreeViewBinder.ViewHolder {
        private ImageView ivArrow;
        private TextView tvName;
        private ImageView ivFolder; // 👈 matches imageview2 in XML

        public ViewHolder(View rootView) {
            super(rootView);
            this.ivArrow = rootView.findViewById(R.id.iv_arrow);
            this.tvName = rootView.findViewById(R.id.tv_name);
            this.ivFolder = rootView.findViewById(R.id.imageview2); // use imageview2 here
        }

        public ImageView getIvArrow() { return ivArrow; }
        public TextView getTvName() { return tvName; }
        public ImageView getIvFolder() { return ivFolder; }
    }
}
		
		
		public static class Dir implements TreeViewList.LayoutItemType {
			public String dirName;
			
			public Dir(String dirName) {
				this.dirName = dirName;
			}
			
			@Override
			public int getLayoutId() {
				return R.layout.item_dir;
			}
		}
		
		
		public static class File implements TreeViewList.LayoutItemType {
			public String fileName;
			
			public File(String fileName) {
				this.fileName = fileName;
			}
			
			@Override
			public int getLayoutId() {
				return R.layout.item_file;
			}
		}
	}
	
	
	{
	}
	
	
	public void _menu1(final View _v) {
		try {
			
			android.widget.PopupMenu popupMenu = new android.widget.PopupMenu(EditorActivity.this, _v);
			
			popupMenu.getMenu().add(android.view.Menu.NONE, 1, android.view.Menu.NONE, "Resource");
			popupMenu.getMenu().add(android.view.Menu.NONE, 2, android.view.Menu.NONE, "Java");
			popupMenu.getMenu().add(android.view.Menu.NONE, 3, android.view.Menu.NONE, "Library");
			popupMenu.getMenu().add(android.view.Menu.NONE, 4, android.view.Menu.NONE, "Configurations");
			
			boolean isChecked = wordWrapHelper.isWordWrapEnabled();
			
			android.view.MenuItem wordWrapItem = popupMenu.getMenu().add(android.view.Menu.NONE, 5, android.view.Menu.NONE, "Word Wrap");
			wordWrapItem.setCheckable(true);
			wordWrapItem.setChecked(isChecked);
			
			popupMenu.getMenu().add(android.view.Menu.NONE, 6, android.view.Menu.NONE, "Text Size");
			
			popupMenu.getMenu().add(android.view.Menu.NONE, 7, android.view.Menu.NONE, "Build Logs");
			
			popupMenu.setOnMenuItemClickListener(new android.widget.PopupMenu.OnMenuItemClickListener() {
				@Override
				public boolean onMenuItemClick(android.view.MenuItem item) {
					int id = item.getItemId();
					
					if (id == 1) {
						i.setClass(EditorActivity.this, ResActivity.class);
						i.putExtra("spath", getIntent().getStringExtra("path"));
						startActivity(i);
						return true;
					} 
					else if (id == 2) {
						i.setClass(EditorActivity.this, JavActivity.class);
						i.putExtra("spath", getIntent().getStringExtra("path"));
						startActivity(i);
						return true;
					} 
					else if (id == 3) {
						i.setClass(EditorActivity.this, LibActivity.class);
						i.putExtra("spath", getIntent().getStringExtra("path"));
						startActivity(i);
						return true;
					}
					else if (id == 4) {
						i.setClass(EditorActivity.this, ProjEditorConfigActivity.class);
						i.putExtra("spath", getIntent().getStringExtra("path"));
						startActivity(i);
						return true;
					}
					else if (id == 5) {
						boolean newCheckState = !item.isChecked();
						item.setChecked(newCheckState);
						wordWrapHelper.setWordWrapEnabled(newCheckState);
						return true;
					}
					else if (id == 6) {
						_showTextSizeDialog();
						return true;
					}
					else if (id == 7) {
						_openBuildLogs();
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
	
	
	public void _initializeApkBuilder() {
		try{
			/*

Initialize code by adding it directly
----------------------------
チュ！可愛いくてごめん！！

*/
			
			
			_checkOtherNecessaryThings();
			
			mLogger = new Logger();
			SystemLogPrinter.start(mLogger);
			Project project = new Project();
			project.setLibraries(Library.fromLibsJson(new File(localLibsPath)));
			project.setResourcesFile(new File(resPath));
			project.setOutputFile(new File(et_output));
			project.setJavaFile(new File(javaPath));
			project.setManifestFile(new File(manifestPath));
			if (!android.text.TextUtils.isEmpty(assetsPath)) {
				project.setAssetsFile(new File(assetsPath));
			}
			project.setVersionName(new String(versionName));
			project.setLogger(mLogger);
			project.setMinSdk(Integer.parseInt(minSdk));
			project.setTargetSdk(Integer.parseInt(maxSdk));
			project.setVersionCode(Integer.parseInt(versionCode));
			CompilerAsyncTask task = new CompilerAsyncTask(EditorActivity.this);
			task.execute(project);
			
			/*

Ending of the code
-----------------------------
じゃ！待ったね！ｗｗｗ

*/
		}catch(Exception e){
			System.out.println("An error occurred: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	public void _checkOtherNecessaryThings() {
		if (FileUtil.isDirectory(getIntent().getStringExtra("path").concat("/app/src/main/res/"))) {
			resPath = getIntent().getStringExtra("path").concat("/app/src/main/res/");
		}
		if (FileUtil.isDirectory(getIntent().getStringExtra("path").concat("/app/src/main/java/"))) {
			javaPath = getIntent().getStringExtra("path").concat("/app/src/main/java/");
		}
		if (FileUtil.isFile(getIntent().getStringExtra("path").concat("/app/src/main/AndroidManifest.xml"))) {
			manifestPath = getIntent().getStringExtra("path").concat("/app/src/main/AndroidManifest.xml");
		}
		if (FileUtil.isDirectory(getIntent().getStringExtra("path").concat("/app/src/main/assets/"))) {
			assetsPath = getIntent().getStringExtra("path").concat("/app/src/main/assets/");
		}
		if (FileUtil.isDirectory(getIntent().getStringExtra("path").concat("/app/src/main/jni/"))) {
			nativeLibsPath = getIntent().getStringExtra("path").concat("/app/src/main/jni/");
		}
		localLibsPath = "/storage/emulated/0/.androIDE/data/".concat(getIntent().getStringExtra("name")).concat("/local_libs.json");
		et_output = getIntent().getStringExtra("path").concat("/app/build/");
	}
	
	
	public void _anchore_extra() {
    // Reserved for extra anchoring logic if needed later
}

private void _selectTab(final com.google.android.material.card.MaterialCardView _card,
final Fragment _fragment,
final String _tag) {
	if (_card == _selectedCard) return;
	final int _selectedColor = Color.parseColor(_isDarkModeGlobal ? "#374955" : "#D1E5F4");
	final com.google.android.material.card.MaterialCardView _oldCard = _selectedCard;
	
	boolean isEditor = "editor".equals(_tag);
	if (keyboardHelper != null) {
		keyboardHelper.setEditorTabActive(isEditor);
	}
	
	ValueAnimator _fadeOut = ValueAnimator.ofArgb(_selectedColor, Color.parseColor("#00000000"));
	_fadeOut.setDuration(180);
	_fadeOut.addUpdateListener(a -> _oldCard.setCardBackgroundColor((int) a.getAnimatedValue()));
	_fadeOut.start();
	
	ValueAnimator _fadeIn = ValueAnimator.ofArgb(Color.parseColor("#00000000"), _selectedColor);
	_fadeIn.setDuration(180);
	_fadeIn.addUpdateListener(a -> _card.setCardBackgroundColor((int) a.getAnimatedValue()));
	_fadeIn.start();
	
	getSupportFragmentManager().beginTransaction()
	.hide(getSupportFragmentManager().findFragmentByTag(_tagFor(_oldCard)))
	.show(_fragment)
	.runOnCommit(() -> {
		if (!isViewModeHidden && isEditor) {
			keyboardHelper = new KeyboardVisibilityHelper(this);
			keyboardHelper.bindBottomHolder(findViewById(R.id.inner_run_holder));
		}
	})
	.commit();
	
	_selectedCard = _card;
}

private String _tagFor(MaterialCardView card) {
    if (card == edit_cd) return "editor";
    if (card == file_cd) return "files";
    if (card == run_cd) return "run";
    if (card == ai_gen_cd) return "ai_gen";
    if (card == terminal_cd) return "terminal";
    return "";
}

private void _installApk(String _apkPath) {
	try {
		File _apkFile = new File(_apkPath);

		if (!_apkFile.exists()) {
			com.google.android.material.snackbar.Snackbar.make(linear2, "APK not found at " + _apkPath, com.google.android.material.snackbar.Snackbar.LENGTH_LONG)
				.setAction("OK", new View.OnClickListener() { @Override public void onClick(View _view) {} }).show();
			return;
		}

		Uri _apkUri = androidx.core.content.FileProvider.getUriForFile(
			EditorActivity.this,
			getPackageName() + ".provider",
			_apkFile
		);

		Intent _installIntent = new Intent(Intent.ACTION_VIEW);
		_installIntent.setDataAndType(_apkUri, "application/vnd.android.package-archive");
		_installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		_installIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

		startActivity(_installIntent);

	} catch (Exception e) {
		e.printStackTrace();
		com.google.android.material.snackbar.Snackbar.make(linear2, "Failed to launch installer: " + e.getMessage(), com.google.android.material.snackbar.Snackbar.LENGTH_LONG)
			.setAction("OK", new View.OnClickListener() { @Override public void onClick(View _view) {} }).show();
	}
}

private void _showBuildSnackbar() {
	if (buildSnackbar != null && buildSnackbar.isShown()) {
		return;
	}
	buildSnackbar = com.google.android.material.snackbar.Snackbar.make(linear2, "Build Started....", com.google.android.material.snackbar.Snackbar.LENGTH_INDEFINITE)
		.setAction("View Logs", new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_openBuildLogs();
			}
		})
		.setBehavior(new com.google.android.material.snackbar.BaseTransientBottomBar.Behavior() {
			@Override
			public boolean canSwipeDismissView(View view) {
				return false;
			}
		});
	buildSnackbar.show();
}

private void _dismissBuildSnackbar() {
	if (buildSnackbar != null && buildSnackbar.isShown()) {
		buildSnackbar.dismiss();
	}
	buildSnackbar = null;
}

private void _openBuildLogs() {
	Intent _intent = new Intent(EditorActivity.this, BuildLogsActivity.class);
	_intent.putExtra("name", getIntent().getStringExtra("name"));
	_intent.putExtra("data", "/storage/emulated/0/.androIDE/data/".concat(getIntent().getStringExtra("name")).concat("/"));
	startActivity(_intent);
}


private void _showTextSizeDialog() {
    final float[] sizes = {12f, 14f, 16f, 18f, 20f, 24f};
    final String[] labels = {"12sp", "14sp (Default)", "16sp", "18sp", "20sp", "24sp"};

    float currentSize = textSizeHelper.getTextSize();
    int checkedIndex = 1; // fallback to default
    for (int idx = 0; idx < sizes.length; idx++) {
        if (sizes[idx] == currentSize) {
            checkedIndex = idx;
            break;
        }
    }

    View dialogView = getLayoutInflater().inflate(R.layout.text_size_dialog, null);
    TextView subtext = dialogView.findViewById(R.id.subtext);
    subtext.setText("Choose a text size for the code editor.");

    final android.widget.RadioGroup radioGroup = dialogView.findViewById(R.id.radio_group);
    final android.widget.RadioButton[] radioButtons = new android.widget.RadioButton[sizes.length];

    for (int idx = 0; idx < sizes.length; idx++) {
        com.google.android.material.radiobutton.MaterialRadioButton rb =
            new com.google.android.material.radiobutton.MaterialRadioButton(this);
        rb.setText(labels[idx]);
        rb.setId(View.generateViewId());
        radioGroup.addView(rb);
        radioButtons[idx] = rb;
    }
    radioGroup.check(radioButtons[checkedIndex].getId());

    final int[] selectedIndex = {checkedIndex};
    radioGroup.setOnCheckedChangeListener(new android.widget.RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(android.widget.RadioGroup group, int checkedId) {
            for (int idx = 0; idx < radioButtons.length; idx++) {
                if (radioButtons[idx].getId() == checkedId) {
                    selectedIndex[0] = idx;
                    break;
                }
            }
        }
    });

    MaterialAlertDialogBuilder d = new MaterialAlertDialogBuilder(EditorActivity.this);
    d.setTitle("Editor text size");
    d.setView(dialogView);
    d.setPositiveButton("Save", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface _dialog, int _which) {
            textSizeHelper.setTextSize(sizes[selectedIndex[0]]);
        }
    });
    d.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface _dialog, int _which) {
        }
    });
    d.create().show();
}


public boolean isViewModeHidden() {
    return isViewModeHidden;
}

public KeyboardVisibilityHelper getKeyboardHelper() {
    return keyboardHelper;
}

public void setKeyboardHelper(KeyboardVisibilityHelper helper) {
    this.keyboardHelper = helper;
}

public WordWrapHelper getWordWrapHelper() {
    return wordWrapHelper;
}

public TextSizeHelper getTextSizeHelper() {
    return textSizeHelper;
}
	
	
	public void _initializeCrashLogger() {
		try {
			final Thread.UncaughtExceptionHandler defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
			Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
				@Override
				public void uncaughtException(Thread thread, Throwable throwable) {
					try {
						// Use the Activity context directly
						android.content.Intent intent = new android.content.Intent(EditorActivity.this, DebugActivity.class);
						intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK);
						intent.putExtra("error", android.util.Log.getStackTraceString(throwable));
						startActivity(intent);
						
						// Kill the process to avoid inconsistent state
						android.os.Process.killProcess(android.os.Process.myPid());
						System.exit(1);
					} catch (Exception e) {
						if (defaultHandler != null) {
							defaultHandler.uncaughtException(thread, throwable);
						}
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
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
