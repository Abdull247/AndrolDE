package org.ide.andriod;

import android.Manifest;
import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
import com.google.android.material.textfield.*;
import com.google.gson.*;
import com.google.gson.Gson;
import java.io.*;
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;
import org.json.*;
import android.content.Context;
import com.google.android.material.radiobutton.MaterialRadioButton;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.net.URISyntaxException;
import java.net.URI;
import java.io.File;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import android.view.LayoutInflater;
import android.widget.AutoCompleteTextView;
import com.google.android.material.color.DynamicColors;
import com.google.android.material.navigation.NavigationView;


public class CreateActivity extends AppCompatActivity {
	
	private HashMap<String, Object> proj = new HashMap<>();
	private String string = "";
	private String path = "";
	
	private ArrayList<String> liststring = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> editor = new ArrayList<>();
	
	private ScrollView vscroll1;
	private LinearLayout linear22;
	private LinearLayout linear1;
	private TextView textview1;
	private LinearLayout linear20;
	private LinearLayout linear26;
	private TextView textview10;
	private LinearLayout application_info_bg;
	private TextInputLayout textinputlayout1;
	private TextInputLayout textinputlayout2;
	private TextInputLayout textinputlayout3;
	private TextInputLayout textinputlayout4;
	private EditText edittext1;
	private EditText edittext2;
	private EditText edittext3;
	private EditText edittext4;
	private LinearLayout linear23;
	private LinearLayout linear24;
	private LinearLayout linear25;
	private TextView textview13;
	private TextView textview14;
	
	private Intent i = new Intent();
	private SharedPreferences s;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.create);
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
		vscroll1 = findViewById(R.id.vscroll1);
		linear22 = findViewById(R.id.linear22);
		linear1 = findViewById(R.id.linear1);
		textview1 = findViewById(R.id.textview1);
		linear20 = findViewById(R.id.linear20);
		linear26 = findViewById(R.id.linear26);
		textview10 = findViewById(R.id.textview10);
		application_info_bg = findViewById(R.id.application_info_bg);
		textinputlayout1 = findViewById(R.id.textinputlayout1);
		textinputlayout2 = findViewById(R.id.textinputlayout2);
		textinputlayout3 = findViewById(R.id.textinputlayout3);
		textinputlayout4 = findViewById(R.id.textinputlayout4);
		edittext1 = findViewById(R.id.edittext1);
		edittext2 = findViewById(R.id.edittext2);
		edittext3 = findViewById(R.id.edittext3);
		edittext4 = findViewById(R.id.edittext4);
		linear23 = findViewById(R.id.linear23);
		linear24 = findViewById(R.id.linear24);
		linear25 = findViewById(R.id.linear25);
		textview13 = findViewById(R.id.textview13);
		textview14 = findViewById(R.id.textview14);
		s = getSharedPreferences("s", Activity.MODE_PRIVATE);
		
		edittext1.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				edittext3.setText("/sdcard/.androIDE/".concat(_charSeq));
				edittext2.setText("com.".concat(_charSeq.replace(" ", ".")).toLowerCase());
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		linear23.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
		
		linear25.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (edittext1.getText().toString().equals("") || edittext2.getText().toString().equals("")) {
					com.google.android.material.snackbar.Snackbar.make(linear26, "Error, project name or package name can't be empty!", com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).setAction("Ok", new View.OnClickListener(){
						@Override
						public void onClick(View _view) {
							
						}
					}).show();
				} else {
					_Create(edittext1.getText().toString(), edittext2.getText().toString());
				}
			}
		});
	}
	
	private void initializeLogic() {
		_CreateActivity();
		getWindow().setNavigationBarColor(Color.TRANSPARENT);
		getWindow().setStatusBarColor(Color.TRANSPARENT);
	}
	
	
	@Override
	public void onStart() {
		super.onStart();
		
	}
	public void _CreateActivity() {
		
	}
	
	
	public void _project(final String _a, final String _b) {
		copyAssetFolder(this,_a,_b);
	}
	public static boolean copyAssetFolder(Context context, String srcName, String dstName) {
		try {
			boolean result = true;
			String fileList[] = context.getAssets().list(srcName);
			if (fileList == null) return false;
			
			if (fileList.length == 0) {
				result = copyAssetFile(context, srcName, dstName);
			} else {
				File file = new File(dstName);
				result = file.mkdirs();
				for (String filename : fileList) {
					result &= copyAssetFolder(context, srcName + File.separator + filename, dstName + File.separator + filename);
				}
			}
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean copyAssetFile(Context context, String srcName, String dstName) {
		try {
			InputStream in = context.getAssets().open(srcName);
			File outFile = new File(dstName);
			OutputStream out = new FileOutputStream(outFile);
			byte[] buffer = new byte[1024];
			int read;
			while ((read = in.read(buffer)) != -1) {
				out.write(buffer, 0, read);
			}
			in.close();
			out.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	{
	}
	
	
	public void _createFile(final String _a, final String _b) {
		FileUtil.writeFile(_b, _a);
	}
	
	
	public void _Create(final String _a, final String _b) {
		if (getIntent().getStringExtra("a").equals("y")) {
			// Main Code
			
			string = "/.androIDE/";
			// Create Main.java
			
			_createFile("package $&#1;\n\nimport android.app.Activity;\nimport android.os.Bundle;\nimport androidx.appcompat.app.AppCompatActivity;\n\npublic class Main extends AppCompatActivity\n{\n    @Override\n    protected void onCreate(Bundle savedInstanceState) {\n        \n        super.onCreate(savedInstanceState);\n        setContentView(R.layout.main);\n    }\n    \n}".replace("$&#1", _b), FileUtil.getExternalStorageDir().concat(string.concat(_a.concat("/app/src/main/java/".concat(_b.replace(".", "/").concat("/Main.java"))))));
			_createFile("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<manifest\n	xmlns:android=\"http://schemas.android.com/apk/res/android\"\n    package=\"$&#2\" >\n    <application\n        android:allowBackup=\"true\"\n        android:icon=\"@mipmap/ic_launcher\"\n        android:roundIcon=\"@mipmap/ic_launcher_round\"\n        android:label=\"@string/app_name\"\n        android:theme=\"@style/AppTheme\"\n		android:resizeableActivity = \"true\">\n        <activity\n            android:name=\".Main\"\n            android:label=\"@string/app_name\" >\n            <intent-filter>\n                <action android:name=\"android.intent.action.MAIN\" />\n                <category android:name=\"android.intent.category.LAUNCHER\" />\n            </intent-filter>\n        </activity>\n    </application>\n</manifest>".replace("$&#2", _b), FileUtil.getExternalStorageDir().concat(string.concat(_a.concat("/app/src/main/AndroidManifest.xml"))));
			_createFile("apply plugin: 'com.android.application'\n\nandroid {\n    compileSdkVersion 29\n    \n\n    defaultConfig {\n        applicationId \"$&#3\"\n        minSdkVersion 21\n        targetSdkVersion 29\n        versionCode 1\n        versionName \"1.0\"\n    }\n    buildTypes {\n        release {\n            minifyEnabled false\n            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'\n        }\n    }\n}\n\ndependencies {\n    implementation fileTree(dir: 'libs', include: ['*.jar'])\n}".replace("$&#3", _b), FileUtil.getExternalStorageDir().concat(string.concat(_a.concat("/app/build.gradle"))));
			_createFile("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<resources>\n\n    <string name=\"app_name\">$&#4</string>\n\n</resources>".replace("$&#4", _a), FileUtil.getExternalStorageDir().concat(string.concat(_a.concat("/app/src/main/res/values/strings.xml"))));
			_project("appcompat", FileUtil.getExternalStorageDir().concat(string.concat(_a.concat("/"))));
			_apkProjectJson(FileUtil.getExternalStorageDir().concat(string.concat(_a.concat("/app_config.json"))), _b);
			// Create a json file for the editor...
			
			proj = new HashMap<>();
			proj.put("path", FileUtil.getExternalStorageDir().concat(string.concat(_a.concat("/app/src/main/java/".concat(_b.replace(".", "/").concat("/Main.java"))))));
			editor.add(proj);
			_createFile(new Gson().toJson(editor), FileUtil.getExternalStorageDir().concat(string.concat(_a.concat("/editor/editorOpened.json"))));
			i.setClass(getApplicationContext(), EditorActivity.class);
			i.putExtra("path", FileUtil.getExternalStorageDir().concat(string.concat(_a)));
			i.putExtra("name", _a);
			s.edit().putString("zz", FileUtil.getExternalStorageDir().concat(string.concat(_a))).commit();
			startActivity(i);
		} else {
			// Main Code
			
			string = "/.androIDE/";
			// Create Main.java
			
			_createFile("package $&#1;\n\nimport android.app.*;\nimport android.os.*;\n\npublic class Main extends Activity \n{\n    @Override\n    protected void onCreate(Bundle savedInstanceState)\n    {\n        super.onCreate(savedInstanceState);\n        setContentView(R.layout.main);\n    }\n}".replace("$&#1", _b), FileUtil.getExternalStorageDir().concat(string.concat(_a.concat("/app/src/main/java/".concat(_b.replace(".", "/").concat("/Main.java"))))));
			_createFile("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<manifest\n	xmlns:android=\"http://schemas.android.com/apk/res/android\"\n    package=\"$&#2\" >\n    <application\n        android:allowBackup=\"true\"\n        android:icon=\"@mipmap/ic_launcher\"\n        android:roundIcon=\"@mipmap/ic_launcher_round\"\n        android:label=\"@string/app_name\"\n        android:theme=\"@style/AppTheme\"\n		android:resizeableActivity = \"true\">\n        <activity\n            android:name=\".Main\"\n            android:label=\"@string/app_name\" >\n            <intent-filter>\n                <action android:name=\"android.intent.action.MAIN\" />\n                <category android:name=\"android.intent.category.LAUNCHER\" />\n            </intent-filter>\n        </activity>\n    </application>\n</manifest>".replace("$&#2", _b), FileUtil.getExternalStorageDir().concat(string.concat(_a.concat("/app/src/main/AndroidManifest.xml"))));
			_createFile("apply plugin: 'com.android.application'\n\nandroid {\n    compileSdkVersion 29\n    \n\n    defaultConfig {\n        applicationId \"$&#3\"\n        minSdkVersion 21\n        targetSdkVersion 29\n        versionCode 1\n        versionName \"1.0\"\n    }\n    buildTypes {\n        release {\n            minifyEnabled false\n            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'\n        }\n    }\n}\n\ndependencies {\n    implementation fileTree(dir: 'libs', include: ['*.jar'])\n}".replace("$&#3", _b), FileUtil.getExternalStorageDir().concat(string.concat(_a.concat("/app/build.gradle"))));
			_createFile("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<resources>\n\n    <string name=\"app_name\">$&#4</string>\n\n</resources>".replace("$&#4", _a), FileUtil.getExternalStorageDir().concat(string.concat(_a.concat("/app/src/main/res/values/strings.xml"))));
			_project("activity", FileUtil.getExternalStorageDir().concat(string.concat(_a.concat("/"))));
			_apkProjectJson(FileUtil.getExternalStorageDir().concat(string.concat(_a.concat("/app_config.json"))), _b);
			// Create a json file for the editor...
			
			proj = new HashMap<>();
			proj.put("path", FileUtil.getExternalStorageDir().concat(string.concat(_a.concat("/app/src/main/java/".concat(_b.replace(".", "/").concat("/Main.java"))))));
			editor.add(proj);
			_createFile(new Gson().toJson(editor), FileUtil.getExternalStorageDir().concat(string.concat(_a.concat("/editor/editorOpened.json"))));
			i.setClass(getApplicationContext(), EditorActivity.class);
			i.putExtra("path", FileUtil.getExternalStorageDir().concat(string.concat(_a)));
			i.putExtra("name", _a);
			s.edit().putString("zz", FileUtil.getExternalStorageDir().concat(string.concat(_a))).commit();
			startActivity(i);
		}
	}
	
	
	public void _apkProjectJson(final String _a, final String _b) {
		proj.put("package", _b);
		proj.put("useR8", true);
		proj.put("minSdkVersion", (int)(21));
		proj.put("targetSdkVersion", (int)(29));
		proj.put("versionName", "1.0");
		proj.put("versionCode", (int)(1));
		proj.put("zipAlignEnabled", true);
		proj.put("viewBindingEnabled", true);
		proj.put("libraries", "[]");
		_createFile(new Gson().toJson(proj), _a);
		proj.clear();
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
