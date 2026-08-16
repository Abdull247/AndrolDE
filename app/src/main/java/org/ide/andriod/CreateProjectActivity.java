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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import androidx.vectordrawable.*;
import androidx.versionedparcelable.*;
import androidx.viewpager.*;
import com.google.android.material.button.*;
import com.google.android.material.card.*;
import com.google.android.material.textfield.*;
import com.google.gson.*;
import de.hdodenhof.circleimageview.*;
import java.io.*;
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.json.*;
import android.annotation.SuppressLint;
import android.view.MotionEvent;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.shape.ShapeAppearanceModel;



public class CreateProjectActivity extends AppCompatActivity {
	
	public final int REQ_CD_FP = 101;
	
	private String fontName = "";
	private String typeace = "";
	private String color1 = "";
	private String color2 = "";
	private String color3 = "";
	private String color4 = "";
	private String color5 = "";
	private String iconPath = "";
	
	private LinearLayout linear1;
	private LinearLayout top_bar;
	private NestedScrollView nestedScrollView1;
	private LinearLayout linear6;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private ImageView imageview1;
	private TextView textview1;
	private LinearLayout linear5;
	private LinearLayout linear7;
	private LinearLayout linear8;
	private LinearLayout linear9;
	private LinearLayout linear10;
	private TextInputLayout textinputlayout1;
	private TextInputLayout textinputlayout2;
	private TextInputLayout textinputlayout3;
	private TextInputLayout textinputlayout4;
	private LinearLayout linear27;
	private MaterialCardView materialCardView1;
	private LinearLayout linear29;
	private MaterialCardView materialCardView7;
	private CircleImageView app_icon;
	private TextView textview2;
	private LinearLayout linear11;
	private TextInputEditText application_name_input;
	private TextInputEditText package_name_input;
	private TextInputEditText project_name_input;
	private TextInputEditText project_path_input;
	private LinearLayout linear13;
	private LinearLayout linear14;
	private LinearLayout linear28;
	private LinearLayout linear15;
	private ImageView imageview2;
	private TextView textview3;
	private MaterialButton materialbutton1;
	private TextView textview9;
	private HorizontalScrollView hscroll1;
	private LinearLayout linear16;
	private LinearLayout item_color1_holder;
	private LinearLayout item_color2_holder;
	private LinearLayout item_color3_holder;
	private LinearLayout linear23;
	private LinearLayout linear25;
	private MaterialCardView color_item1;
	private TextView textview4;
	private LinearLayout color_linear1;
	private MaterialCardView color_item2;
	private TextView textview5;
	private LinearLayout color_linear2;
	private MaterialCardView color_item3;
	private TextView textview6;
	private LinearLayout color_linear3;
	private MaterialCardView color_item4;
	private TextView textview7;
	private LinearLayout color_linear4;
	private MaterialCardView color_item5;
	private TextView textview8;
	private LinearLayout color_linear5;
	private LinearLayout linear30;
	private LinearLayout linear_vercode;
	private LinearLayout linear32;
	private LinearLayout linear_ver_name;
	private TextView ver_code_txt;
	private TextView textview11;
	private ImageView imageview3;
	private TextView ver_name_txt;
	private TextView textview13;
	private MaterialButton button5;
	private MaterialButton button3;
	
	private Intent intent = new Intent();
	private SharedPreferences sp;
	private AlertDialog.Builder dialog;
	private Intent fp = new Intent(Intent.ACTION_GET_CONTENT);
	private Intent i = new Intent();
	private SharedPreferences s;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.create_project);
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
		top_bar = findViewById(R.id.top_bar);
		nestedScrollView1 = findViewById(R.id.nestedScrollView1);
		linear6 = findViewById(R.id.linear6);
		linear3 = findViewById(R.id.linear3);
		linear4 = findViewById(R.id.linear4);
		imageview1 = findViewById(R.id.imageview1);
		textview1 = findViewById(R.id.textview1);
		linear5 = findViewById(R.id.linear5);
		linear7 = findViewById(R.id.linear7);
		linear8 = findViewById(R.id.linear8);
		linear9 = findViewById(R.id.linear9);
		linear10 = findViewById(R.id.linear10);
		textinputlayout1 = findViewById(R.id.textinputlayout1);
		textinputlayout2 = findViewById(R.id.textinputlayout2);
		textinputlayout3 = findViewById(R.id.textinputlayout3);
		textinputlayout4 = findViewById(R.id.textinputlayout4);
		linear27 = findViewById(R.id.linear27);
		materialCardView1 = findViewById(R.id.materialCardView1);
		linear29 = findViewById(R.id.linear29);
		materialCardView7 = findViewById(R.id.materialCardView7);
		app_icon = findViewById(R.id.app_icon);
		textview2 = findViewById(R.id.textview2);
		linear11 = findViewById(R.id.linear11);
		application_name_input = findViewById(R.id.application_name_input);
		package_name_input = findViewById(R.id.package_name_input);
		project_name_input = findViewById(R.id.project_name_input);
		project_path_input = findViewById(R.id.project_path_input);
		linear13 = findViewById(R.id.linear13);
		linear14 = findViewById(R.id.linear14);
		linear28 = findViewById(R.id.linear28);
		linear15 = findViewById(R.id.linear15);
		imageview2 = findViewById(R.id.imageview2);
		textview3 = findViewById(R.id.textview3);
		materialbutton1 = findViewById(R.id.materialbutton1);
		textview9 = findViewById(R.id.textview9);
		hscroll1 = findViewById(R.id.hscroll1);
		linear16 = findViewById(R.id.linear16);
		item_color1_holder = findViewById(R.id.item_color1_holder);
		item_color2_holder = findViewById(R.id.item_color2_holder);
		item_color3_holder = findViewById(R.id.item_color3_holder);
		linear23 = findViewById(R.id.linear23);
		linear25 = findViewById(R.id.linear25);
		color_item1 = findViewById(R.id.color_item1);
		textview4 = findViewById(R.id.textview4);
		color_linear1 = findViewById(R.id.color_linear1);
		color_item2 = findViewById(R.id.color_item2);
		textview5 = findViewById(R.id.textview5);
		color_linear2 = findViewById(R.id.color_linear2);
		color_item3 = findViewById(R.id.color_item3);
		textview6 = findViewById(R.id.textview6);
		color_linear3 = findViewById(R.id.color_linear3);
		color_item4 = findViewById(R.id.color_item4);
		textview7 = findViewById(R.id.textview7);
		color_linear4 = findViewById(R.id.color_linear4);
		color_item5 = findViewById(R.id.color_item5);
		textview8 = findViewById(R.id.textview8);
		color_linear5 = findViewById(R.id.color_linear5);
		linear30 = findViewById(R.id.linear30);
		linear_vercode = findViewById(R.id.linear_vercode);
		linear32 = findViewById(R.id.linear32);
		linear_ver_name = findViewById(R.id.linear_ver_name);
		ver_code_txt = findViewById(R.id.ver_code_txt);
		textview11 = findViewById(R.id.textview11);
		imageview3 = findViewById(R.id.imageview3);
		ver_name_txt = findViewById(R.id.ver_name_txt);
		textview13 = findViewById(R.id.textview13);
		button5 = findViewById(R.id.button5);
		button3 = findViewById(R.id.button3);
		sp = getSharedPreferences("colors", Activity.MODE_PRIVATE);
		dialog = new AlertDialog.Builder(this);
		fp.setType("image/*");
		fp.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		s = getSharedPreferences("s", Activity.MODE_PRIVATE);
		
		application_name_input.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				project_path_input.setText("/sdcard/.androIDE/".concat(_charSeq));
				package_name_input.setText("com.".concat(_charSeq.replace(" ", ".")).toLowerCase());
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		materialbutton1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				String[] allColors = {
					// RED
					"#FFEBEE","#FFCDD2","#EF9A9A","#E57373","#EF5350","#F44336","#E53935","#D32F2F","#C62828","#B71C1C","#FF8A80","#FF5252","#FF1744","#D50000",
					// PINK
					"#FCE4EC","#F8BBD0","#F48FB1","#F06292","#EC407A","#E91E63","#D81B60","#C2185B","#AD1457","#880E4F","#FF80AB","#FF4081","#F50057","#C51162",
					// PURPLE
					"#F3E5F5","#E1BEE7","#CE93D8","#BA68C8","#AB47BC","#9C27B0","#8E24AA","#7B1FA2","#6A1B9A","#4A148C","#EA80FC","#E040FB","#D500F9","#AA00FF",
					// DEEP PURPLE
					"#EDE7F6","#D1C4E9","#B39DDB","#9575CD","#7E57C2","#673AB7","#5E35B1","#512DA8","#4527A0","#311B92","#B388FF","#7C4DFF","#651FFF","#6200EA",
					// INDIGO
					"#E8EAF6","#C5CAE9","#9FA8DA","#7986CB","#5C6BC0","#3F51B5","#3949AB","#303F9F","#283593","#1A237E","#8C9EFF","#536DFE","#3D5AFE","#304FFE",
					// BLUE
					"#E3F2FD","#BBDEFB","#90CAF9","#64B5F6","#42A5F5","#2196F3","#1E88E5","#1976D2","#1565C0","#0D47A1","#82B1FF","#448AFF","#2979FF","#2962FF",
					// LIGHT BLUE
					"#E1F5FE","#B3E5FC","#81D4FA","#4FC3F7","#29B6F6","#03A9F4","#039BE5","#0288D1","#0277BD","#01579B","#80D8FF","#40C4FF","#00B0FF","#0091EA",
					// CYAN
					"#E0F7FA","#B2EBF2","#80DEEA","#4DD0E1","#26C6DA","#00BCD4","#00ACC1","#0097A7","#00838F","#006064","#84FFFF","#18FFFF","#00E5FF","#00B8D4",
					// TEAL
					"#E0F2F1","#B2DFDB","#80CBC4","#4DB6AC","#26A69A","#009688","#00897B","#00796B","#00695C","#004D40","#A7FFEB","#64FFDA","#1DE9B6","#00BFA5",
					// GREEN
					"#E8F5E9","#C8E6C9","#A5D6A7","#81C784","#66BB6A","#4CAF50","#43A047","#388E3C","#2E7D32","#1B5E20","#B9F6CA","#69F0AE","#00E676","#00C853",
					// LIGHT GREEN
					"#F1F8E9","#DCEDC8","#C5E1A5","#AED581","#9CCC65","#8BC34A","#7CB342","#689F38","#558B2F","#33691E","#CCFF90","#B2FF59","#76FF03","#64DD17",
					// LIME
					"#F9FBE7","#F0F4C3","#E6EE9C","#DCE775","#D4E157","#CDDC39","#C0CA33","#AFB42B","#9E9D24","#827717","#F4FF81","#EEFF41","#C6FF00","#AEEA00",
					// YELLOW
					"#FFFDE7","#FFF9C4","#FFF59D","#FFF176","#FFEE58","#FFEB3B","#FDD835","#FBC02D","#F9A825","#F57F17","#FFFF8D","#FFEA00","#FFD600",
					// AMBER
					"#FFF8E1","#FFECB3","#FFE082","#FFD54F","#FFCA28","#FFC107","#FFB300","#FFA000","#FF8F00","#FF6F00","#FFE57F","#FFD740","#FFC400","#FFAB00",
					// ORANGE
					"#FFF3E0","#FFE0B2","#FFCC80","#FFB74D","#FFA726","#FF9800","#FB8C00","#F57C00","#EF6C00","#E65100","#FFD180","#FFAB40","#FF9100","#FF6D00",
					// DEEP ORANGE
					"#FBE9E7","#FFCCBC","#FFAB91","#FF8A65","#FF7043","#FF5722","#F4511E","#E64A19","#D84315","#BF360C","#FF9E80","#FF6E40","#FF3D00","#DD2C00",
					// BROWN
					"#EFEBE9","#D7CCC8","#BCAAA4","#A1887F","#8D6E63","#795548","#6D4C41","#5D4037","#4E342E","#3E2723",
					// GREY
					"#FAFAFA","#F5F5F5","#EEEEEE","#E0E0E0","#BDBDBD","#9E9E9E","#757575","#616161","#424242","#212121",
					// BLUE GREY
					"#607D8B","#ECEFF1","#CFD8DC","#B0BEC5","#90A4AE","#78909C","#546E7A","#455A64","#37474F","#263238",
					// BLACK, WHITE, TRANSPARENT
					"#000000","#FFFFFF","#00000000"
				};
				
				Random random = new Random();
				
				color1 = allColors[random.nextInt(allColors.length)];
				color2 = allColors[random.nextInt(allColors.length)];
				color3 = allColors[random.nextInt(allColors.length)];
				color4 = allColors[random.nextInt(allColors.length)];
				color5 = allColors[random.nextInt(allColors.length)];
				
				// Save to SharedPreferences
				sp.edit()
				.putString("color1", color1)
				.putString("color2", color2)
				.putString("color3", color3)
				.putString("color4", color4)
				.putString("color5", color5)
				.apply();
				
				// Update layout backgrounds
				color_linear1.setBackgroundColor(Color.parseColor(color1));
				color_linear2.setBackgroundColor(Color.parseColor(color2));
				color_linear3.setBackgroundColor(Color.parseColor(color3));
				color_linear4.setBackgroundColor(Color.parseColor(color4));
				color_linear5.setBackgroundColor(Color.parseColor(color5));
			}
		});
		
		linear_vercode.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				dialog.setTitle("Version code");
				
				final String[] versionCodeValues = new String[99];
				for (int i = 0; i < 99; i++) {
					versionCodeValues[i] = String.valueOf(i + 1);
				}
				
				final android.widget.NumberPicker codePickerr = new android.widget.NumberPicker(CreateProjectActivity.this);
				codePickerr.setMinValue(0);
				codePickerr.setMaxValue(98);
				codePickerr.setWrapSelectorWheel(true);
				codePickerr.setDisplayedValues(versionCodeValues);
				
				// Pre-select the currently saved version code
				String currentCode = sp.getString("versionCode", "1");
				for (int i = 0; i < versionCodeValues.length; i++) {
					if (versionCodeValues[i].equals(currentCode)) {
						codePickerr.setValue(i);
						break;
					}
				}
				
				try {
					java.lang.reflect.Field selectionDivider = android.widget.NumberPicker.class.getDeclaredField("mSelectionDivider");
					selectionDivider.setAccessible(true);
					selectionDivider.set(codePickerr, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				android.widget.LinearLayout codeContainer = new android.widget.LinearLayout(CreateProjectActivity.this);
				codeContainer.setOrientation(android.widget.LinearLayout.VERTICAL);
				codeContainer.setGravity(android.view.Gravity.CENTER);
				codeContainer.setPadding(40, 24, 40, 8);
				
				android.widget.TextView codeLabel = new android.widget.TextView(CreateProjectActivity.this);
				codeLabel.setText("SELECT CODE");
				codeLabel.setTextSize(10f);
				codeLabel.setLetterSpacing(0.2f);
				codeLabel.setTextColor(android.graphics.Color.parseColor("#76D1FF"));
				codeLabel.setGravity(android.view.Gravity.CENTER);
				codeLabel.setPadding(0, 0, 0, 12);
				
				android.view.View codeAccentLine = new android.view.View(CreateProjectActivity.this);
				codeAccentLine.setBackgroundColor(android.graphics.Color.parseColor("#76D1FF"));
				android.widget.LinearLayout.LayoutParams codeAccentParams =
				new android.widget.LinearLayout.LayoutParams(120, 3);
				codeAccentParams.gravity = android.view.Gravity.CENTER_HORIZONTAL;
				codeAccentParams.setMargins(0, 0, 0, 16);
				
				android.widget.LinearLayout.LayoutParams codeNpParams =
				new android.widget.LinearLayout.LayoutParams(220,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
				codeNpParams.gravity = android.view.Gravity.CENTER;
				
				codeContainer.addView(codeLabel);
				codeContainer.addView(codeAccentLine, codeAccentParams);
				codeContainer.addView(codePickerr, codeNpParams);
				
				dialog.setView(codeContainer);
				
				dialog.setPositiveButton("Apply", new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(android.content.DialogInterface dialogInterface, int which) {
						String selectedCode = versionCodeValues[codePickerr.getValue()];
						
						// Save to SharedPreferences
						sp.edit().putString("versionCode", selectedCode).apply();
						
						// Update the TextView immediately
						ver_code_txt.setText(selectedCode);
					}
				});
				
				dialog.setNegativeButton("Dismiss", new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(android.content.DialogInterface dialogInterface, int which) {
						dialogInterface.dismiss();
					}
				});
				
				android.app.AlertDialog codeDialog = dialog.create();
				codeDialog.show();
				
				if (codeDialog.getWindow() != null) {
					android.graphics.drawable.GradientDrawable bg = new android.graphics.drawable.GradientDrawable();
					bg.setColor(android.graphics.Color.parseColor("#1A2226"));
					bg.setCornerRadius(36f);
					codeDialog.getWindow().setBackgroundDrawable(bg);
					
					android.view.WindowManager.LayoutParams lp = codeDialog.getWindow().getAttributes();
					lp.width = (int) (CreateProjectActivity.this.getResources().getDisplayMetrics().widthPixels * 0.72f);
					lp.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
					codeDialog.getWindow().setAttributes(lp);
					
					codeDialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE)
					.setTextColor(android.graphics.Color.parseColor("#76D1FF"));
					codeDialog.getButton(android.app.AlertDialog.BUTTON_NEGATIVE)
					.setTextColor(android.graphics.Color.parseColor("#607880"));
				}
			}
		});
		
		linear_ver_name.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				dialog.setTitle("Version name");
				
				final String[] versionValues = new String[99];
				for (int i = 0; i < 99; i++) {
					versionValues[i] = (i + 1) + ".0";
				}
				
				final android.widget.NumberPicker numberPicker = new android.widget.NumberPicker(CreateProjectActivity.this);
				numberPicker.setMinValue(0);
				numberPicker.setMaxValue(98);
				numberPicker.setWrapSelectorWheel(true);
				numberPicker.setDisplayedValues(versionValues);
				
				// Pre-select the currently saved version
				String currentVersion = sp.getString("versionName", "1.0");
				for (int i = 0; i < versionValues.length; i++) {
					if (versionValues[i].equals(currentVersion)) {
						numberPicker.setValue(i);
						break;
					}
				}
				
				try {
					java.lang.reflect.Field selectionDivider = android.widget.NumberPicker.class.getDeclaredField("mSelectionDivider");
					selectionDivider.setAccessible(true);
					selectionDivider.set(numberPicker, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				android.widget.LinearLayout pickerContainer = new android.widget.LinearLayout(CreateProjectActivity.this);
				pickerContainer.setOrientation(android.widget.LinearLayout.VERTICAL);
				pickerContainer.setGravity(android.view.Gravity.CENTER);
				pickerContainer.setPadding(40, 24, 40, 8);
				
				android.widget.TextView pickerLabel = new android.widget.TextView(CreateProjectActivity.this);
				pickerLabel.setText("SELECT VERSION");
				pickerLabel.setTextSize(10f);
				pickerLabel.setLetterSpacing(0.2f);
				pickerLabel.setTextColor(android.graphics.Color.parseColor("#76D1FF"));
				pickerLabel.setGravity(android.view.Gravity.CENTER);
				pickerLabel.setPadding(0, 0, 0, 12);
				
				android.view.View accentLine = new android.view.View(CreateProjectActivity.this);
				accentLine.setBackgroundColor(android.graphics.Color.parseColor("#76D1FF"));
				android.widget.LinearLayout.LayoutParams accentParams =
				new android.widget.LinearLayout.LayoutParams(120, 3);
				accentParams.gravity = android.view.Gravity.CENTER_HORIZONTAL;
				accentParams.setMargins(0, 0, 0, 16);
				
				android.widget.LinearLayout.LayoutParams npParams =
				new android.widget.LinearLayout.LayoutParams(220,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
				npParams.gravity = android.view.Gravity.CENTER;
				
				pickerContainer.addView(pickerLabel);
				pickerContainer.addView(accentLine, accentParams);
				pickerContainer.addView(numberPicker, npParams);
				
				dialog.setView(pickerContainer);
				
				dialog.setPositiveButton("Apply", new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(android.content.DialogInterface dialogInterface, int which) {
						String selectedVersion = versionValues[numberPicker.getValue()];
						
						// Save to SharedPreferences
						sp.edit().putString("versionName", selectedVersion).apply();
						
						// Update the TextView immediately
						ver_name_txt.setText(selectedVersion);
					}
				});
				
				dialog.setNegativeButton("Dismiss", new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(android.content.DialogInterface dialogInterface, int which) {
						dialogInterface.dismiss();
					}
				});
				
				android.app.AlertDialog alertDialog = dialog.create();
				alertDialog.show();
				
				if (alertDialog.getWindow() != null) {
					android.graphics.drawable.GradientDrawable bg = new android.graphics.drawable.GradientDrawable();
					bg.setColor(android.graphics.Color.parseColor("#1A2226"));
					bg.setCornerRadius(36f);
					alertDialog.getWindow().setBackgroundDrawable(bg);
					
					android.view.WindowManager.LayoutParams lp = alertDialog.getWindow().getAttributes();
					lp.width = (int) (CreateProjectActivity.this.getResources().getDisplayMetrics().widthPixels * 0.72f);
					lp.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
					alertDialog.getWindow().setAttributes(lp);
					
					alertDialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE)
					.setTextColor(android.graphics.Color.parseColor("#76D1FF"));
					alertDialog.getButton(android.app.AlertDialog.BUTTON_NEGATIVE)
					.setTextColor(android.graphics.Color.parseColor("#607880"));
				}
			}
		});
		
		button5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		button3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (application_name_input.getText().toString().equals("") || package_name_input.getText().toString().equals("") || project_name_input.getText().toString().equals("")) {
					com.google.android.material.snackbar.Snackbar.make(linear1, "Error, all fields are required!", com.google.android.material.snackbar.Snackbar.LENGTH_SHORT).show();
				} else {
					ProjectCreator _creator = new ProjectCreator(getApplicationContext(), project_name_input.getText().toString(), package_name_input.getText().toString(), application_name_input.getText().toString(), color1, color2, color3, color4, color5);
					String _projectId = _creator.create("y".equals(getIntent().getStringExtra("a")));
					
					i.setClass(getApplicationContext(), EditorActivity.class);
					i.putExtra("path", "/storage/emulated/0/.androIDE/mysc/".concat(project_name_input.getText().toString()));
					i.putExtra("name", project_name_input.getText().toString());
					i.putExtra("id", _projectId);
					s.edit().putString("zz", "/storage/emulated/0/.androIDE/mysc/".concat(project_name_input.getText().toString())).commit();
					startActivity(i);
				}
			}
		});
	}
	
	private void initializeLogic() {
		_changeActivityFont("enlight");
		try {
			final Thread.UncaughtExceptionHandler defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
			Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
				@Override
				public void uncaughtException(Thread thread, Throwable throwable) {
					try {
						// Use the Activity context directly
						android.content.Intent intent = new android.content.Intent(CreateProjectActivity.this, DebugActivity.class);
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
		hscroll1.setHorizontalScrollBarEnabled(false);
		hscroll1.setVerticalScrollBarEnabled(false);
		hscroll1.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
		// --- BUTTON 5 LOGIC ---
		final float density = getResources().getDisplayMetrics().density;
		final float defaultRadius = 28f * density;  // Circular shape
		final float pressedRadius = 8f * density;   // Squircle/square shape
		
		com.google.android.material.button.MaterialButton button5 = findViewById(R.id.button5);
		
		if (button5 != null) {
			// Set initial circular shape
			button5.setShapeAppearanceModel(button5.getShapeAppearanceModel().toBuilder()
			.setAllCornerSizes(defaultRadius)
			.build());
			
			// Add touch listener for morphing animation
			button5.setOnTouchListener(new android.view.View.OnTouchListener() {
				private android.animation.ValueAnimator animator;
				
				@Override
				public boolean onTouch(android.view.View v, android.view.MotionEvent event) {
					
					// Prevent parent from intercepting touch
					if (v.getParent() != null) {
						v.getParent().requestDisallowInterceptTouchEvent(true);
					}
					
					if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
						// Press: quick morph to squircle (100ms)
						startAnim(pressedRadius, 100, new android.view.animation.DecelerateInterpolator());
					} 
					else if (event.getAction() == android.view.MotionEvent.ACTION_UP || 
					event.getAction() == android.view.MotionEvent.ACTION_CANCEL) {
						// Release: smooth morph back to circle (300ms)
						startAnim(defaultRadius, 300, new android.view.animation.AccelerateDecelerateInterpolator());
					}
					
					return false; // Allow OnClickListener to work
				}
				
				private void startAnim(float target, int duration, android.view.animation.Interpolator interpolator) {
					if (animator != null && animator.isRunning()) {
						animator.cancel();
					}
					
					android.graphics.RectF rect = new android.graphics.RectF(0, 0, button5.getWidth(), button5.getHeight());
					float startVal = button5.getShapeAppearanceModel().getBottomLeftCornerSize().getCornerSize(rect);
					
					animator = android.animation.ValueAnimator.ofFloat(startVal, target);
					animator.setDuration(duration);
					animator.setInterpolator(interpolator);
					
					animator.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() {
						@Override
						public void onAnimationUpdate(android.animation.ValueAnimator animation) {
							float value = (float) animation.getAnimatedValue();
							button5.setShapeAppearanceModel(button5.getShapeAppearanceModel().toBuilder()
							.setAllCornerSizes(value)
							.build());
						}
					});
					animator.start();
				}
			});
		}
		
		// --- BUTTON 3 LOGIC (IDENTICAL) ---
		com.google.android.material.button.MaterialButton button3 = findViewById(R.id.button3);
		
		if (button3 != null) {
			// Set initial circular shape
			button3.setShapeAppearanceModel(button3.getShapeAppearanceModel().toBuilder()
			.setAllCornerSizes(defaultRadius)
			.build());
			
			// Add touch listener for morphing animation
			button3.setOnTouchListener(new android.view.View.OnTouchListener() {
				private android.animation.ValueAnimator animator;
				
				@Override
				public boolean onTouch(android.view.View v, android.view.MotionEvent event) {
					
					if (v.getParent() != null) {
						v.getParent().requestDisallowInterceptTouchEvent(true);
					}
					
					if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
						startAnim(pressedRadius, 100, new android.view.animation.DecelerateInterpolator());
					} 
					else if (event.getAction() == android.view.MotionEvent.ACTION_UP || 
					event.getAction() == android.view.MotionEvent.ACTION_CANCEL) {
						startAnim(defaultRadius, 300, new android.view.animation.AccelerateDecelerateInterpolator());
					}
					
					return false;
				}
				
				private void startAnim(float target, int duration, android.view.animation.Interpolator interpolator) {
					if (animator != null && animator.isRunning()) {
						animator.cancel();
					}
					
					android.graphics.RectF rect = new android.graphics.RectF(0, 0, button3.getWidth(), button3.getHeight());
					float startVal = button3.getShapeAppearanceModel().getBottomLeftCornerSize().getCornerSize(rect);
					
					animator = android.animation.ValueAnimator.ofFloat(startVal, target);
					animator.setDuration(duration);
					animator.setInterpolator(interpolator);
					
					animator.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() {
						@Override
						public void onAnimationUpdate(android.animation.ValueAnimator animation) {
							float value = (float) animation.getAnimatedValue();
							button3.setShapeAppearanceModel(button3.getShapeAppearanceModel().toBuilder()
							.setAllCornerSizes(value)
							.build());
						}
					});
					animator.start();
				}
			});
		}
		application_name_input.setSingleLine(true);
		package_name_input.setSingleLine(true);
		project_name_input.setSingleLine(true);
		// Define defaults
		color1 = "#2196F3";
		color2 = "#2196F3";
		color3 = "#2196F3";
		color4 = "#90CAF9";
		color5 = "#2196F3";
		
		// Fallback to defaults if SharedPreferences keys don't exist, otherwise read them
		color1 = sp.getString("color1", color1);
		color2 = sp.getString("color2", color2);
		color3 = sp.getString("color3", color3);
		color4 = sp.getString("color4", color4);
		color5 = sp.getString("color5", color5);
		
		// Apply colors to the view backgrounds
		color_linear1.setBackgroundColor(Color.parseColor(color1));
		color_linear2.setBackgroundColor(Color.parseColor(color2));
		color_linear3.setBackgroundColor(Color.parseColor(color3));
		color_linear4.setBackgroundColor(Color.parseColor(color4));
		color_linear5.setBackgroundColor(Color.parseColor(color5));
		
		color_item1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				Intent intent = new Intent(getApplicationContext(), ColorPickerActivity.class);
				intent.putExtra("color1", color1);
				startActivityForResult(intent, 100);
				overridePendingTransition(R.anim.slide_in_enter, R.anim.slide_out_exit);
			}
		});
		
		color_item2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				Intent intent = new Intent(getApplicationContext(), ColorPickerActivity.class);
				intent.putExtra("color2", color2);
				startActivityForResult(intent, 100);
				overridePendingTransition(R.anim.slide_in_enter, R.anim.slide_out_exit);
			}
		});
		
		color_item3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				Intent intent = new Intent(getApplicationContext(), ColorPickerActivity.class);
				intent.putExtra("color3", color3);
				startActivityForResult(intent, 100);
				overridePendingTransition(R.anim.slide_in_enter, R.anim.slide_out_exit);
			}
		});
		
		color_item4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				Intent intent = new Intent(getApplicationContext(), ColorPickerActivity.class);
				intent.putExtra("color4", color4);
				startActivityForResult(intent, 100);
				overridePendingTransition(R.anim.slide_in_enter, R.anim.slide_out_exit);
			}
		});
		
		color_item5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				Intent intent = new Intent(getApplicationContext(), ColorPickerActivity.class);
				intent.putExtra("color5", color5);
				startActivityForResult(intent, 100);
				overridePendingTransition(R.anim.slide_in_enter, R.anim.slide_out_exit);
			}
		});
		// Load saved version name, default to "1.0" if not set
		String savedVersion = sp.getString("versionName", "1.0");
		ver_name_txt.setText(savedVersion);
		String savedCode = sp.getString("versionCode", "1");
		ver_code_txt.setText(savedCode);
		iconPath = "";
		project_path_input.setFocusable(false);
		project_path_input.setFreezesText(true);
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		if (_requestCode == 100 && _resultCode == RESULT_OK && _data != null) {
			String selectedColor = _data.getStringExtra("selectedColor");
			String colorKey = _data.getStringExtra("colorKey");
			int parsedColor = Color.parseColor(selectedColor);
			
			// Save the freshly picked hex string straight into SharedPreferences
			sp.edit().putString(colorKey, selectedColor).commit();
			
			// Update local variables and layouts dynamically
			switch (colorKey) {
				case "color1":
				color1 = selectedColor;
				color_linear1.setBackgroundColor(parsedColor);
				break;
				case "color2":
				color2 = selectedColor;
				color_linear2.setBackgroundColor(parsedColor);
				break;
				case "color3":
				color3 = selectedColor;
				color_linear3.setBackgroundColor(parsedColor);
				break;
				case "color4":
				color4 = selectedColor;
				color_linear4.setBackgroundColor(parsedColor);
				break;
				case "color5":
				color5 = selectedColor;
				color_linear5.setBackgroundColor(parsedColor);
				break;
			}
			// No need to clear extras — _data is local to this call, it won't re-trigger
		}
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
				iconPath = _filePath.get((int)(0));
				app_icon.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(iconPath, 1024, 1024));
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
		// Wipe the draft colors out of preferences completely
		sp.edit().remove("color1").commit();
		sp.edit().remove("color2").commit();
		sp.edit().remove("color3").commit();
		sp.edit().remove("color4").commit();
		sp.edit().remove("color5").commit();
		
		// Wipe draft version name
		sp.edit().remove("versionName").commit();
		sp.edit().remove("versionCode").commit();
		
		finish();
		overridePendingTransition(R.anim.slide_in_enter, R.anim.slide_out_exit);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		// Wipe the draft colors out of preferences completely
		sp.edit().remove("color1").commit();
		sp.edit().remove("color2").commit();
		sp.edit().remove("color3").commit();
		sp.edit().remove("color4").commit();
		sp.edit().remove("color5").commit();
		// Wipe draft version name
		sp.edit().remove("versionName").commit();
		sp.edit().remove("versionCode").commit();
		
	}
	public void _changeActivityFont(final String _fontname) {
		fontName = "fonts/".concat(_fontname.concat(".ttf"));
		overrideFonts(this,getWindow().getDecorView()); 
	} 
	private void overrideFonts(final android.content.Context context, final View v) {
		
		try {
			Typeface 
			typeace = Typeface.createFromAsset(getAssets(), fontName);;
			if ((v instanceof ViewGroup)) {
				ViewGroup vg = (ViewGroup) v;
				for (int i = 0;
				i < vg.getChildCount();
				i++) {
					View child = vg.getChildAt(i);
					overrideFonts(context, child);
				}
			} else {
				if ((v instanceof TextView)) {
					((TextView) v).setTypeface(typeace);
				} else {
					if ((v instanceof EditText )) {
						((EditText) v).setTypeface(typeace);
					} else {
						if ((v instanceof Button)) {
							((Button) v).setTypeface(typeace);
						}
					}
				}
			}
		}
		catch(Exception e)
		
		{
			SketchwareUtil.showMessage(getApplicationContext(), "Error Loading Font");
		};
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
