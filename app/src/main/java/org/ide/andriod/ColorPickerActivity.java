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
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.*;
import androidx.appcompat.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.*;
import androidx.asynclayoutinflater.*;
import androidx.biometric.*;
import androidx.constraintlayout.core.*;
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
import androidx.legacy.coreui.*;
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
import androidx.slidingpanelayout.*;
import androidx.swiperefreshlayout.*;
import androidx.vectordrawable.*;
import androidx.versionedparcelable.*;
import androidx.viewpager.*;
import com.google.android.material.card.*;
import com.google.gson.*;
import com.googlecode.d2j.*;
import io.github.rosemoe.editor.*;
import io.github.rosemoe.sora.*;
import io.github.rosemoe.sora.langs.base.*;
import io.github.rosemoe.sora.langs.css3.*;
import io.github.rosemoe.sora.langs.html.*;
import io.github.rosemoe.sora.langs.java.*;
import io.github.rosemoe.sora.langs.python.*;
import io.github.rosemoe.sora.langs.textmate.*;
import io.github.rosemoe.sora.langs.universal.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;
import org.antlr.v4.runtime.*;
import org.benf.cfr.reader.*;
import org.eclipse.jdt.*;
import org.json.*;
import android.graphics.Color;

public class ColorPickerActivity extends AppCompatActivity {
	
	private HashMap<String, Object> img_name_map = new HashMap<>();
	private String fontName = "";
	private String typeace = "";
	private double selectedColorPosition = 0;
	private double autoSelectedCategoryIndex = 0;
	private String passedColor = "";
	private String passedColorKey = "";
	
	private ArrayList<HashMap<String, Object>> colors_name_list = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> ColorsMap = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout top_bar;
	private MaterialCardView cardview1;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private LinearLayout color_main_veetical_holder;
	private TextView textview1;
	private TextView textview2;
	private MaterialCardView materialCardView1;
	private RecyclerView recyclerview1;
	private RecyclerView recyclerview2;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.color_picker);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		top_bar = findViewById(R.id.top_bar);
		cardview1 = findViewById(R.id.cardview1);
		linear3 = findViewById(R.id.linear3);
		linear4 = findViewById(R.id.linear4);
		color_main_veetical_holder = findViewById(R.id.color_main_veetical_holder);
		textview1 = findViewById(R.id.textview1);
		textview2 = findViewById(R.id.textview2);
		materialCardView1 = findViewById(R.id.materialCardView1);
		recyclerview1 = findViewById(R.id.recyclerview1);
		recyclerview2 = findViewById(R.id.recyclerview2);
	}
	
	private void initializeLogic() {
		_changeActivityFont("ooo");
		try {
			final Thread.UncaughtExceptionHandler defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
			Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
				@Override
				public void uncaughtException(Thread thread, Throwable throwable) {
					try {
						// Use the Activity context directly
						android.content.Intent intent = new android.content.Intent(ColorPickerActivity.this, DebugActivity.class);
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
		for (int i = 1; i <= 5; i++) {
			String currentKey = "color" + i;
			if (getIntent().hasExtra(currentKey)) {
				passedColorKey = currentKey; // Save the key name (e.g., "color1")
				passedColor = getIntent().getStringExtra(currentKey).trim().toUpperCase();
				
				if (!passedColor.startsWith("#")) {
					passedColor = "#" + passedColor;
				}
				
				if (passedColor.length() == 7 || passedColor.length() == 9) {
					_findAndLoadMatchingPalette(passedColor);
				}
				break; 
			}
		}
		
		_add_colors_title();
	}
	
	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.slide_in_enter, R.anim.slide_out_exit);
	}
	
	public void _add_colors_title() {
		img_name_map = new HashMap<>();
		img_name_map.put("name", "Custom");
		img_name_map.put("color", "#FFFFFF");
		img_name_map.put("textColor", "#161C20");
		colors_name_list.add(img_name_map);
		img_name_map = new HashMap<>();
		img_name_map.put("name", "colors.xml");
		img_name_map.put("color", "#FFFFFF");
		img_name_map.put("textColor", "#161C20");
		colors_name_list.add(img_name_map);
		img_name_map = new HashMap<>();
		img_name_map.put("name", "RED");
		img_name_map.put("color", "#F44336");
		img_name_map.put("textColor", "#FFFFFF");
		colors_name_list.add(img_name_map);
		img_name_map = new HashMap<>();
		img_name_map.put("name", "PINK");
		img_name_map.put("color", "#E91E63");
		img_name_map.put("textColor", "#FFFFFF");
		colors_name_list.add(img_name_map);
		img_name_map = new HashMap<>();
		img_name_map.put("name", "PURPLE");
		img_name_map.put("color", "#9C27B0");
		img_name_map.put("textColor", "#FFFFFF");
		colors_name_list.add(img_name_map);
		img_name_map = new HashMap<>();
		img_name_map.put("name", "DEEP PURPLE");
		img_name_map.put("color", "#673AB7");
		img_name_map.put("textColor", "#FFFFFF");
		colors_name_list.add(img_name_map);
		img_name_map = new HashMap<>();
		img_name_map.put("name", "INDIGO");
		img_name_map.put("color", "#3F51B5");
		img_name_map.put("textColor", "#FFFFFF");
		colors_name_list.add(img_name_map);
		img_name_map = new HashMap<>();
		img_name_map.put("name", "BLUE");
		img_name_map.put("color", "#2196F3");
		img_name_map.put("textColor", "#FFFFFF");
		colors_name_list.add(img_name_map);
		img_name_map = new HashMap<>();
		img_name_map.put("name", "LIGHT BLUE");
		img_name_map.put("color", "#03A9F4");
		img_name_map.put("textColor", "#161C20");
		colors_name_list.add(img_name_map);
		img_name_map = new HashMap<>();
		img_name_map.put("name", "CYAN");
		img_name_map.put("color", "#00BCD4");
		img_name_map.put("textColor", "#161C20");
		colors_name_list.add(img_name_map);
		img_name_map = new HashMap<>();
		img_name_map.put("name", "TEAL");
		img_name_map.put("color", "#009688");
		img_name_map.put("textColor", "#FFFFFF");
		colors_name_list.add(img_name_map);
		img_name_map = new HashMap<>();
		img_name_map.put("name", "GREEN");
		img_name_map.put("color", "#4CAF50");
		img_name_map.put("textColor", "#161C20");
		colors_name_list.add(img_name_map);
		img_name_map = new HashMap<>();
		img_name_map.put("name", "LIGHT GREEN");
		img_name_map.put("color", "#8BC34A");
		img_name_map.put("textColor", "#161C20");
		colors_name_list.add(img_name_map);
		img_name_map = new HashMap<>();
		img_name_map.put("name", "LIME");
		img_name_map.put("color", "#CDDC39");
		img_name_map.put("textColor", "#161C20");
		colors_name_list.add(img_name_map);
		img_name_map = new HashMap<>();
		img_name_map.put("name", "YELLOW");
		img_name_map.put("color", "#FFEB3B");
		img_name_map.put("textColor", "#161C20");
		colors_name_list.add(img_name_map);
		img_name_map = new HashMap<>();
		img_name_map.put("name", "AMBER");
		img_name_map.put("color", "#FFC107");
		img_name_map.put("textColor", "#161C20");
		colors_name_list.add(img_name_map);
		img_name_map = new HashMap<>();
		img_name_map.put("name", "ORANGE");
		img_name_map.put("color", "#FF9800");
		img_name_map.put("textColor", "#161C20");
		colors_name_list.add(img_name_map);
		img_name_map = new HashMap<>();
		img_name_map.put("name", "DEEP ORANGE");
		img_name_map.put("color", "#FF5722");
		img_name_map.put("textColor", "#FFFFFF");
		colors_name_list.add(img_name_map);
		img_name_map = new HashMap<>();
		img_name_map.put("name", "BROWN");
		img_name_map.put("color", "#795548");
		img_name_map.put("textColor", "#FFFFFF");
		colors_name_list.add(img_name_map);
		img_name_map = new HashMap<>();
		img_name_map.put("name", "GREY");
		img_name_map.put("color", "#9E9E9E");
		img_name_map.put("textColor", "#161C20");
		colors_name_list.add(img_name_map);
		img_name_map = new HashMap<>();
		img_name_map.put("name", "BLUE GREY");
		img_name_map.put("color", "#607D8B");
		img_name_map.put("textColor", "#FFFFFF");
		colors_name_list.add(img_name_map);
		img_name_map = new HashMap<>();
		img_name_map.put("name", "BLACK");
		img_name_map.put("color", "#000000");
		img_name_map.put("textColor", "#FFFFFF");
		colors_name_list.add(img_name_map);
		img_name_map = new HashMap<>();
		img_name_map.put("name", "WHITE");
		img_name_map.put("color", "#FFFFFF");
		img_name_map.put("textColor", "#161C20");
		colors_name_list.add(img_name_map);
		img_name_map = new HashMap<>();
		img_name_map.put("name", "TRANSPARENT");
		img_name_map.put("color", "#00000000");
		img_name_map.put("textColor", "#E0E0E0");
		colors_name_list.add(img_name_map);
		img_name_map = new HashMap<>();
		img_name_map.put("name", "NONE");
		img_name_map.put("color", "#161C20");
		img_name_map.put("textColor", "#E0E0E0");
		colors_name_list.add(img_name_map);
		recyclerview1.setAdapter(new Recyclerview1Adapter(colors_name_list));
		recyclerview1.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
	}
	
	
	public void _updateColor(final ArrayList<HashMap<String, Object>> _ColoursMap) {
		recyclerview2.setAdapter(new Recyclerview2Adapter(_ColoursMap));
		recyclerview2.setLayoutManager(new LinearLayoutManager(this));
	}
	
	
	public String _getTextColorForBackground(final String _hexColor) {
		int color = Color.parseColor(_hexColor);
		int r = Color.red(color);
		int g = Color.green(color);
		int b = Color.blue(color);
		
		// Calculate luminance
		double luminance = (0.299 * r + 0.587 * g + 0.114 * b) / 255;
		
		// If it's bright, use black text ("B"), else white text ("W")
		return luminance > 0.7 ? "B" : "W";
	}
	
	
	public void _findAndLoadMatchingPalette(final String _targetHex) {
		// Local primitive array conversions for strict index checking
		int[] categoryIndices = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
		
		String[][] allCategoryColors = {
			{"#FFEBEE", "#FFCDD2", "#EF9A9A", "#E57373", "#EF5350", "#F44336", "#E53935", "#D32F2F", "#C62828", "#B71C1C", "#FF8A80", "#FF5252", "#FF1744", "#D50000"}, // RED
			{"#FCE4EC", "#F8BBD0", "#F48FB1", "#F06292", "#EC407A", "#E91E63", "#D81B60", "#C2185B", "#AD1457", "#880E4F", "#FF80AB", "#FF4081", "#F50057", "#C51162"}, // PINK
			{"#F3E5F5", "#E1BEE7", "#CE93D8", "#BA68C8", "#AB47BC", "#9C27B0", "#8E24AA", "#7B1FA2", "#6A1B9A", "#4A148C", "#EA80FC", "#E040FB", "#D500F9", "#AA00FF"}, // PURPLE
			{"#EDE7F6", "#D1C4E9", "#B39DDB", "#9575CD", "#7E57C2", "#673AB7", "#5E35B1", "#512DA8", "#4527A0", "#311B92", "#B388FF", "#7C4DFF", "#651FFF", "#6200EA"}, // DEEP PURPLE
			{"#E8EAF6", "#C5CAE9", "#9FA8DA", "#7986CB", "#5C6BC0", "#3F51B5", "#3949AB", "#303F9F", "#283593", "#1A237E", "#8C9EFF", "#536DFE", "#3D5AFE", "#304FFE"}, // INDIGO
			{"#E3F2FD", "#BBDEFB", "#90CAF9", "#64B5F6", "#42A5F5", "#2196F3", "#1E88E5", "#1976D2", "#1565C0", "#0D47A1", "#82B1FF", "#448AFF", "#2979FF", "#2962FF"}, // BLUE
			{"#E1F5FE", "#B3E5FC", "#81D4FA", "#4FC3F7", "#29B6F6", "#03A9F4", "#039BE5", "#0288D1", "#0277BD", "#01579B", "#80D8FF", "#40C4FF", "#00B0FF", "#0091EA"}, // LIGHT BLUE
			{"#E0F7FA", "#B2EBF2", "#80DEEA", "#4DD0E1", "#26C6DA", "#00BCD4", "#00ACC1", "#0097A7", "#00838F", "#006064", "#84FFFF", "#18FFFF", "#00E5FF", "#00B8D4"}, // CYAN
			{"#E0F2F1", "#B2DFDB", "#80CBC4", "#4DB6AC", "#26A69A", "#009688", "#00897B", "#00796B", "#00695C", "#004D40", "#A7FFEB", "#64FFDA", "#1DE9B6", "#00BFA5"}, // TEAL
			{"#E8F5E9", "#C8E6C9", "#A5D6A7", "#81C784", "#66BB6A", "#4CAF50", "#43A047", "#388E3C", "#2E7D32", "#1B5E20", "#B9F6CA", "#69F0AE", "#00E676", "#00C853"}, // GREEN
			{"#F1F8E9", "#DCEDC8", "#C5E1A5", "#AED581", "#9CCC65", "#8BC34A", "#7CB342", "#689F38", "#558B2F", "#33691E", "#CCFF90", "#B2FF59", "#76FF03", "#64DD17"}, // LIGHT GREEN
			{"#F9FBE7", "#F0F4C3", "#E6EE9C", "#DCE775", "#D4E157", "#CDDC39", "#C0CA33", "#AFB42B", "#9E9D24", "#827717", "#F4FF81", "#EEFF41", "#C6FF00", "#AEEA00"}, // LIME
			{"#FFFDE7", "#FFF9C4", "#FFF59D", "#FFF176", "#FFEE58", "#FFEB3B", "#FDD835", "#FBC02D", "#F9A825", "#F57F17", "#FFFF8D", "#FFEA00", "#FFD600"},         // YELLOW
			{"#FFF8E1", "#FFECB3", "#FFE082", "#FFD54F", "#FFCA28", "#FFC107", "#FFB300", "#FFA000", "#FF8F00", "#FF6F00", "#FFE57F", "#FFD740", "#FFC400", "#FFAB00"}, // AMBER
			{"#FFF3E0", "#FFE0B2", "#FFCC80", "#FFB74D", "#FFA726", "#FF9800", "#FB8C00", "#F57C00", "#EF6C00", "#E65100", "#FFD180", "#FFAB40", "#FF9100", "#FF6D00"}, // ORANGE
			{"#FBE9E7", "#FFCCBC", "#FFAB91", "#FF8A65", "#FF7043", "#FF5722", "#F4511E", "#E64A19", "#D84315", "#BF360C", "#FF9E80", "#FF6E40", "#FF3D00", "#DD2C00"}, // DEEP ORANGE
			{"#EFEBE9", "#D7CCC8", "#BCAAA4", "#A1887F", "#8D6E63", "#795548", "#6D4C41", "#5D4037", "#4E342E", "#3E2723"},                                             // BROWN
			{"#FAFAFA", "#F5F5F5", "#EEEEEE", "#E0E0E0", "#BDBDBD", "#9E9E9E", "#757575", "#616161", "#424242", "#212121"},                                             // GREY
			{"#607D8B", "#ECEFF1", "#CFD8DC", "#B0BEC5", "#90A4AE", "#78909C", "#607D8B", "#546E7A", "#455A64", "#37474F", "#263238"},                                 // BLUE GREY
			{"#000000"}, // BLACK
			{"#FFFFFF"}, // WHITE
			{"#00000000"} // TRANSPARENT
		};
		
		for (int catIdx = 0; catIdx < allCategoryColors.length; catIdx++) {
			for (int itemIdx = 0; itemIdx < allCategoryColors[catIdx].length; itemIdx++) {
				if (allCategoryColors[catIdx][itemIdx].equalsIgnoreCase(_targetHex)) {
					// Assign values casting smoothly to Sketchware variables (doubles)
					autoSelectedCategoryIndex = (double) categoryIndices[catIdx];
					selectedColorPosition = (double) itemIdx;
					
					ColorsMap = new ArrayList<>();
					for (String hex : allCategoryColors[catIdx]) {
						HashMap<String, Object> ColourMap = new HashMap<>();
						ColourMap.put("Hex", hex);
						ColourMap.put("TColor", _getTextColorForBackground(hex));
						ColorsMap.add(ColourMap);
					}
					
					_updateColor(ColorsMap);
					recyclerview1.scrollToPosition((int) autoSelectedCategoryIndex);
					return;
				}
			}
		}
		
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
	
	public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getLayoutInflater();
			View _v = _inflater.inflate(R.layout.horizontal_color_list, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final TextView textview1 = _view.findViewById(R.id.textview1);
			
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_view.setLayoutParams(_lp);
			textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/ooo.ttf"), 0);
			textview1.setText(_data.get((int)_position).get("name").toString());
			String hexColor = _data.get((int)_position).get("color").toString();
			linear1.setBackgroundColor(Color.parseColor(hexColor));
			
			String textHexColor = _data.get((int)_position).get("textColor").toString();
			textview1.setTextColor(Color.parseColor(textHexColor));
			// Ripple color setup
			int[][] states = new int[][]{
				new int[]{android.R.attr.state_pressed},
				new int[]{android.R.attr.state_focused},
				new int[]{}
			};
			
			int[] colors = new int[]{
				android.graphics.Color.parseColor("#E0E0E0"),
				android.graphics.Color.parseColor("#E0E0E0"),
				android.graphics.Color.parseColor("#E0E0E0")
			};
			
			android.content.res.ColorStateList rippleColor = new android.content.res.ColorStateList(states, colors);
			
			// Apply to linear_vercode
			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
				linear1.setForeground(new android.graphics.drawable.RippleDrawable(rippleColor, null, null));
			} else {
				linear1.setBackgroundColor(android.graphics.Color.parseColor("#E0E0E0"));
			}
			
			linear1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					selectedColorPosition = -1; // Reset selection index when manually swapping lists
					if (_position == 2) {
						ColorsMap = new ArrayList<>();
						
						String[] hexColors = {
							"#FFEBEE", "#FFCDD2", "#EF9A9A", "#E57373", "#EF5350",
							"#F44336", "#E53935", "#D32F2F", "#C62828", "#B71C1C",
							"#FF8A80", "#FF5252", "#FF1744", "#D50000"
						};
						
						for (String hex : hexColors) {
							HashMap<String, Object> ColourMap = new HashMap<>();
							ColourMap.put("Hex", hex);
							ColourMap.put("TColor", _getTextColorForBackground(hex));
							ColorsMap.add(ColourMap);
						}
						_updateColor(ColorsMap);
					}
					if (_position == 3) {
						ColorsMap = new ArrayList<>();
						
						String[] hexColors = {
							"#FCE4EC", "#F8BBD0", "#F48FB1", "#F06292", "#EC407A",
							"#E91E63", "#D81B60", "#C2185B", "#AD1457", "#880E4F",
							"#FF80AB", "#FF4081", "#F50057", "#C51162"
						};
						
						for (String hex : hexColors) {
							HashMap<String, Object> ColourMap = new HashMap<>();
							ColourMap.put("Hex", hex);
							ColourMap.put("TColor", _getTextColorForBackground(hex));
							ColorsMap.add(ColourMap);
						}
						
						_updateColor(ColorsMap);
					}
					if (_position == 4) {
						ColorsMap = new ArrayList<>();
						
						String[] hexColors = {
							"#F3E5F5", "#E1BEE7", "#CE93D8", "#BA68C8", "#AB47BC",
							"#9C27B0", "#8E24AA", "#7B1FA2", "#6A1B9A", "#4A148C",
							"#EA80FC", "#E040FB", "#D500F9", "#AA00FF"
						};
						
						for (String hex : hexColors) {
							HashMap<String, Object> ColourMap = new HashMap<>();
							ColourMap.put("Hex", hex);
							ColourMap.put("TColor", _getTextColorForBackground(hex));
							ColorsMap.add(ColourMap);
						}
						
						_updateColor(ColorsMap);
					}
					if (_position == 5) {
						ColorsMap = new ArrayList<>();
						
						String[] hexColors = {
							"#EDE7F6", "#D1C4E9", "#B39DDB", "#9575CD", "#7E57C2",
							"#673AB7", "#5E35B1", "#512DA8", "#4527A0", "#311B92",
							"#B388FF", "#7C4DFF", "#651FFF", "#6200EA"
						};
						
						for (String hex : hexColors) {
							HashMap<String, Object> ColourMap = new HashMap<>();
							ColourMap.put("Hex", hex);
							ColourMap.put("TColor", _getTextColorForBackground(hex));
							ColorsMap.add(ColourMap);
						}
						
						_updateColor(ColorsMap);
					}
					if (_position == 6) {
						ColorsMap = new ArrayList<>();
						
						String[] hexColors = {
							"#E8EAF6", "#C5CAE9", "#9FA8DA", "#7986CB", "#5C6BC0",
							"#3F51B5", "#3949AB", "#303F9F", "#283593", "#1A237E",
							"#8C9EFF", "#536DFE", "#3D5AFE", "#304FFE"
						};
						
						for (String hex : hexColors) {
							HashMap<String, Object> ColourMap = new HashMap<>();
							ColourMap.put("Hex", hex);
							ColourMap.put("TColor", _getTextColorForBackground(hex)); // smart text color picker
							ColorsMap.add(ColourMap);
						}
						
						_updateColor(ColorsMap);
					}
					if (_position == 7) {
						ColorsMap = new ArrayList<>();
						
						String[] hexColors = {
							"#E3F2FD", "#BBDEFB", "#90CAF9", "#64B5F6", "#42A5F5",
							"#2196F3", "#1E88E5", "#1976D2", "#1565C0", "#0D47A1",
							"#82B1FF", "#448AFF", "#2979FF", "#2962FF"
						};
						
						for (String hex : hexColors) {
							HashMap<String, Object> ColourMap = new HashMap<>();
							ColourMap.put("Hex", hex);
							ColourMap.put("TColor", _getTextColorForBackground(hex)); // function that sets text color based on background
							ColorsMap.add(ColourMap);
						}
						
						_updateColor(ColorsMap);
					}
					if (_position == 8) {
						ColorsMap = new ArrayList<>();
						
						String[] hexColors = {
							"#E1F5FE", "#B3E5FC", "#81D4FA", "#4FC3F7", "#29B6F6",
							"#03A9F4", "#039BE5", "#0288D1", "#0277BD", "#01579B",
							"#80D8FF", "#40C4FF", "#00B0FF", "#0091EA"
						};
						
						for (String hex : hexColors) {
							HashMap<String, Object> ColourMap = new HashMap<>();
							ColourMap.put("Hex", hex);
							ColourMap.put("TColor", _getTextColorForBackground(hex)); // auto pick text color 💅
							ColorsMap.add(ColourMap);
						}
						
						_updateColor(ColorsMap);
					}
					if (_position == 9) {
						ColorsMap = new ArrayList<>();
						
						String[] hexColors = {
							"#E0F7FA", "#B2EBF2", "#80DEEA", "#4DD0E1", "#26C6DA",
							"#00BCD4", "#00ACC1", "#0097A7", "#00838F", "#006064",
							"#84FFFF", "#18FFFF", "#00E5FF", "#00B8D4"
						};
						
						for (String hex : hexColors) {
							HashMap<String, Object> ColourMap = new HashMap<>();
							ColourMap.put("Hex", hex);
							ColourMap.put("TColor", _getTextColorForBackground(hex)); // for perfect text contrast 💡
							ColorsMap.add(ColourMap);
						}
						
						_updateColor(ColorsMap);
					}
					if (_position == 10) {
						ColorsMap = new ArrayList<>();
						
						String[] hexColors = {
							"#E0F2F1", "#B2DFDB", "#80CBC4", "#4DB6AC", "#26A69A",
							"#009688", "#00897B", "#00796B", "#00695C", "#004D40",
							"#A7FFEB", "#64FFDA", "#1DE9B6", "#00BFA5"
						};
						
						for (String hex : hexColors) {
							HashMap<String, Object> ColourMap = new HashMap<>();
							ColourMap.put("Hex", hex);
							ColourMap.put("TColor", _getTextColorForBackground(hex)); // auto contrast for readable text 💡
							ColorsMap.add(ColourMap);
						}
						
						_updateColor(ColorsMap);
					}
					if (_position == 11) {
						ColorsMap = new ArrayList<>();
						
						String[] hexColors = {
							"#E8F5E9", "#C8E6C9", "#A5D6A7", "#81C784", "#66BB6A",
							"#4CAF50", "#43A047", "#388E3C", "#2E7D32", "#1B5E20",
							"#B9F6CA", "#69F0AE", "#00E676", "#00C853"
						};
						
						for (String hex : hexColors) {
							HashMap<String, Object> ColourMap = new HashMap<>();
							ColourMap.put("Hex", hex);
							ColourMap.put("TColor", _getTextColorForBackground(hex)); // always readable, always fab 💅
							ColorsMap.add(ColourMap);
						}
						
						_updateColor(ColorsMap);
					}
					if (_position == 12) {
						ColorsMap = new ArrayList<>();
						
						String[] hexColors = {
							"#F1F8E9", "#DCEDC8", "#C5E1A5", "#AED581", "#9CCC65",
							"#8BC34A", "#7CB342", "#689F38", "#558B2F", "#33691E",
							"#CCFF90", "#B2FF59", "#76FF03", "#64DD17"
						};
						
						for (String hex : hexColors) {
							HashMap<String, Object> ColourMap = new HashMap<>();
							ColourMap.put("Hex", hex);
							ColourMap.put("TColor", _getTextColorForBackground(hex)); // perfect contrast always 💡
							ColorsMap.add(ColourMap);
						}
						
						_updateColor(ColorsMap);
					}
					if (_position == 13) {
						ColorsMap = new ArrayList<>();
						
						String[] hexColors = {
							"#F9FBE7", "#F0F4C3", "#E6EE9C", "#DCE775", "#D4E157",
							"#CDDC39", "#C0CA33", "#AFB42B", "#9E9D24", "#827717",
							"#F4FF81", "#EEFF41", "#C6FF00", "#AEEA00"
						};
						
						for (String hex : hexColors) {
							HashMap<String, Object> ColourMap = new HashMap<>();
							ColourMap.put("Hex", hex);
							ColourMap.put("TColor", _getTextColorForBackground(hex)); // choose readable text color 🌞
							ColorsMap.add(ColourMap);
						}
						
						_updateColor(ColorsMap);
					}
					if (_position == 14) {
						ColorsMap = new ArrayList<>();
						
						String[] hexColors = {
							"#FFFDE7", "#FFF9C4", "#FFF59D", "#FFF176", "#FFEE58",
							"#FFEB3B", "#FDD835", "#FBC02D", "#F9A825", "#F57F17",
							"#FFFF8D", "#FFEA00", "#FFD600"
						};
						
						for (String hex : hexColors) {
							HashMap<String, Object> ColourMap = new HashMap<>();
							ColourMap.put("Hex", hex);
							ColourMap.put("TColor", _getTextColorForBackground(hex)); // makes sure text is poppin' 💬✨
							ColorsMap.add(ColourMap);
						}
						
						_updateColor(ColorsMap);
					}
					if (_position == 15) {
						ColorsMap = new ArrayList<>();
						
						String[] hexColors = {
							"#FFF8E1", "#FFECB3", "#FFE082", "#FFD54F", "#FFCA28",
							"#FFC107", "#FFB300", "#FFA000", "#FF8F00", "#FF6F00",
							"#FFE57F", "#FFD740", "#FFC400", "#FFAB00"
						};
						
						for (String hex : hexColors) {
							HashMap<String, Object> ColourMap = new HashMap<>();
							ColourMap.put("Hex", hex);
							ColourMap.put("TColor", _getTextColorForBackground(hex)); // brightness-aware text color 💡
							ColorsMap.add(ColourMap);
						}
						
						_updateColor(ColorsMap);
					}
					if (_position == 16) {
						ColorsMap = new ArrayList<>();
						
						String[] hexColors = {
							"#FFF3E0", "#FFE0B2", "#FFCC80", "#FFB74D", "#FFA726",
							"#FF9800", "#FB8C00", "#F57C00", "#EF6C00", "#E65100",
							"#FFD180", "#FFAB40", "#FF9100", "#FF6D00"
						};
						
						for (String hex : hexColors) {
							HashMap<String, Object> ColourMap = new HashMap<>();
							ColourMap.put("Hex", hex);
							ColourMap.put("TColor", _getTextColorForBackground(hex)); // makes text pop perfectly ✨
							ColorsMap.add(ColourMap);
						}
						
						_updateColor(ColorsMap);
					}
					if (_position == 17) {
						ColorsMap = new ArrayList<>();
						
						String[] hexColors = {
							"#FBE9E7", "#FFCCBC", "#FFAB91", "#FF8A65", "#FF7043",
							"#FF5722", "#F4511E", "#E64A19", "#D84315", "#BF360C",
							"#FF9E80", "#FF6E40", "#FF3D00", "#DD2C00"
						};
						
						for (String hex : hexColors) {
							HashMap<String, Object> ColourMap = new HashMap<>();
							ColourMap.put("Hex", hex);
							ColourMap.put("TColor", _getTextColorForBackground(hex)); // keeps your text always visible 🔍
							ColorsMap.add(ColourMap);
						}
						
						_updateColor(ColorsMap);
					}
					if (_position == 18) {
						ColorsMap = new ArrayList<>();
						
						String[] hexColors = {
							"#EFEBE9", "#D7CCC8", "#BCAAA4", "#A1887F", "#8D6E63",
							"#795548", "#6D4C41", "#5D4037", "#4E342E", "#3E2723"
						};
						
						for (String hex : hexColors) {
							HashMap<String, Object> ColourMap = new HashMap<>();
							ColourMap.put("Hex", hex);
							ColourMap.put("TColor", _getTextColorForBackground(hex)); // Keeps that contrast on point 📱
							ColorsMap.add(ColourMap);
						}
						
						_updateColor(ColorsMap);
					}
					if (_position == 19) {
						ColorsMap = new ArrayList<>();
						
						String[] hexColors = {
							"#FAFAFA", // Grey 50
							"#F5F5F5", // Grey 100
							"#EEEEEE", // Grey 200
							"#E0E0E0", // Grey 300
							"#BDBDBD", // Grey 400
							"#9E9E9E", // Grey 500
							"#757575", // Grey 600
							"#616161", // Grey 700
							"#424242", // Grey 800
							"#212121"  // Grey 900
						};
						
						for (String hex : hexColors) {
							HashMap<String, Object> ColourMap = new HashMap<>();
							ColourMap.put("Hex", hex);
							ColourMap.put("TColor", _getTextColorForBackground(hex)); // so your text always stands out 🖋️
							ColorsMap.add(ColourMap);
						}
						
						_updateColor(ColorsMap);
					}
					if (_position == 20) {
						ColorsMap = new ArrayList<>();
						
						String[] hexColors = {
							"#607D8B", "#ECEFF1", "#CFD8DC", "#B0BEC5", "#90A4AE",
							"#78909C", "#607D8B", "#546E7A", "#455A64", "#37474F",
							"#263238"
						};
						
						for (String hex : hexColors) {
							HashMap<String, Object> ColourMap = new HashMap<>();
							ColourMap.put("Hex", hex);
							ColourMap.put("TColor", _getTextColorForBackground(hex)); // For perfect contrast, every time 🌓
							ColorsMap.add(ColourMap);
						}
						
						_updateColor(ColorsMap);
					}
					if (_position == 21) {
						ColorsMap = new ArrayList<>();
						
						String[] hexColors = {
							"#000000"
						};
						
						for (String hex : hexColors) {
							HashMap<String, Object> ColourMap = new HashMap<>();
							ColourMap.put("Hex", hex);
							ColourMap.put("TColor", _getTextColorForBackground(hex)); // For perfect contrast, every time 🌓
							ColorsMap.add(ColourMap);
						}
						
						_updateColor(ColorsMap);
					}
					if (_position == 22) {
						ColorsMap = new ArrayList<>();
						
						String[] hexColors = {
							"#FFFFFF"
						};
						
						for (String hex : hexColors) {
							HashMap<String, Object> ColourMap = new HashMap<>();
							ColourMap.put("Hex", hex);
							ColourMap.put("TColor", _getTextColorForBackground(hex)); // For perfect contrast, every time 🌓
							ColorsMap.add(ColourMap);
						}
						
						_updateColor(ColorsMap);
					}
					if (_position == 23) {
						ColorsMap = new ArrayList<>();
						
						String[] hexColors = {
							"#00000000"
						};
						
						for (String hex : hexColors) {
							HashMap<String, Object> ColourMap = new HashMap<>();
							ColourMap.put("Hex", hex);
							ColourMap.put("TColor", _getTextColorForBackground(hex)); // For perfect contrast, every time 🌓
							ColorsMap.add(ColourMap);
						}
						
						_updateColor(ColorsMap);
					}
					if (_position == 24) {
						
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
	
	public class Recyclerview2Adapter extends RecyclerView.Adapter<Recyclerview2Adapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Recyclerview2Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getLayoutInflater();
			View _v = _inflater.inflate(R.layout.vertical_color_list, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final ImageView selected_img = _view.findViewById(R.id.selected_img);
			final TextView textview1 = _view.findViewById(R.id.textview1);
			
			textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/ooo.ttf"), 0);
			textview1.setText(_data.get((int)_position).get("Hex").toString());
			linear1.setBackgroundColor(Color.parseColor(_data.get(_position).get("Hex").toString()));
			
			if (_data.get(_position).get("TColor").toString().equals("B")) {
				textview1.setTextColor(Color.BLACK);
			} else {
				textview1.setTextColor(Color.WHITE);
			}
			// Ripple color setup
			int[][] states = new int[][]{
				new int[]{android.R.attr.state_pressed},
				new int[]{android.R.attr.state_focused},
				new int[]{}
			};
			
			int[] colors = new int[]{
				android.graphics.Color.parseColor("#E0E0E0"),
				android.graphics.Color.parseColor("#E0E0E0"),
				android.graphics.Color.parseColor("#E0E0E0")
			};
			
			android.content.res.ColorStateList rippleColor = new android.content.res.ColorStateList(states, colors);
			
			// Apply to linear_vercode
			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
				linear1.setForeground(new android.graphics.drawable.RippleDrawable(rippleColor, null, null));
			} else {
				linear1.setBackgroundColor(android.graphics.Color.parseColor("#E0E0E0"));
			}
			
			// Explicit cast double tracking back down to integer list alignment safely
			if ((double) _position == selectedColorPosition) {
				selected_img.setVisibility(View.VISIBLE);
			} else {
				selected_img.setVisibility(View.GONE);
			}
			linear1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					selectedColorPosition = (double) _position;
					notifyDataSetChanged();
					
					// Send result back WITHOUT creating a new activity
					Intent returnIntent = new Intent();
					returnIntent.putExtra("selectedColor", _data.get(_position).get("Hex").toString());
					returnIntent.putExtra("colorKey", passedColorKey);
					setResult(RESULT_OK, returnIntent);
					finish();
					overridePendingTransition(R.anim.slide_in_enter, R.anim.slide_out_exit);
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