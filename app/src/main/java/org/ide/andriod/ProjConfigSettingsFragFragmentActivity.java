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
import androidx.vectordrawable.*;
import androidx.versionedparcelable.*;
import androidx.viewpager.*;
import com.google.android.material.card.*;
import com.google.gson.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.json.*;

public class ProjConfigSettingsFragFragmentActivity extends Fragment {
	
	private String path = "";
	private String assets_path = "";
	private String java_path = "";
	private String layout_path = "";
	private String resources_path = "";
	private String libs_path = "";
	
	private NestedScrollView nestedScrollView1;
	private LinearLayout linear4;
	private MaterialCardView materialCardView1;
	private MaterialCardView cardview1;
	private LinearLayout linear5;
	private TextView textview1;
	private LinearLayout linear6;
	private TextView textview2;
	private TextView build_output_txt;
	private LinearLayout linear7;
	private TextView textview3;
	private MaterialCardView materialCardView2;
	private LinearLayout linear9;
	private LinearLayout linear10;
	private LinearLayout linear11;
	private LinearLayout linear12;
	private LinearLayout linear13;
	private LinearLayout linear15;
	private LinearLayout linear16;
	private TextView textview6;
	private TextView impl_txt;
	private TextView assets_path_txt;
	private ImageView imageview1;
	private TextView java_path_txt;
	private ImageView imageview2;
	private TextView layout_path_txt;
	private ImageView imageview3;
	private TextView res_path_txt;
	private ImageView imageview5;
	private TextView libs_path_txt;
	private ImageView imageview6;
	
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.proj_config_settings_frag_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		nestedScrollView1 = _view.findViewById(R.id.nestedScrollView1);
		linear4 = _view.findViewById(R.id.linear4);
		materialCardView1 = _view.findViewById(R.id.materialCardView1);
		cardview1 = _view.findViewById(R.id.cardview1);
		linear5 = _view.findViewById(R.id.linear5);
		textview1 = _view.findViewById(R.id.textview1);
		linear6 = _view.findViewById(R.id.linear6);
		textview2 = _view.findViewById(R.id.textview2);
		build_output_txt = _view.findViewById(R.id.build_output_txt);
		linear7 = _view.findViewById(R.id.linear7);
		textview3 = _view.findViewById(R.id.textview3);
		materialCardView2 = _view.findViewById(R.id.materialCardView2);
		linear9 = _view.findViewById(R.id.linear9);
		linear10 = _view.findViewById(R.id.linear10);
		linear11 = _view.findViewById(R.id.linear11);
		linear12 = _view.findViewById(R.id.linear12);
		linear13 = _view.findViewById(R.id.linear13);
		linear15 = _view.findViewById(R.id.linear15);
		linear16 = _view.findViewById(R.id.linear16);
		textview6 = _view.findViewById(R.id.textview6);
		impl_txt = _view.findViewById(R.id.impl_txt);
		assets_path_txt = _view.findViewById(R.id.assets_path_txt);
		imageview1 = _view.findViewById(R.id.imageview1);
		java_path_txt = _view.findViewById(R.id.java_path_txt);
		imageview2 = _view.findViewById(R.id.imageview2);
		layout_path_txt = _view.findViewById(R.id.layout_path_txt);
		imageview3 = _view.findViewById(R.id.imageview3);
		res_path_txt = _view.findViewById(R.id.res_path_txt);
		imageview5 = _view.findViewById(R.id.imageview5);
		libs_path_txt = _view.findViewById(R.id.libs_path_txt);
		imageview6 = _view.findViewById(R.id.imageview6);
	}
	
	private void initializeLogic() {
		path = getArguments() != null ? getArguments().getString("path") : "";
		build_output_txt.setText(path.concat("/app/build/bin"));
		assets_path = "app/src/main/assets";
		java_path = "app/src/main/java";
		layout_path = "app/src/main/res/layout";
		resources_path = "app/src/main/res";
		libs_path = "data/acctiv/local_libs.json";
		assets_path_txt.setText(assets_path);
		java_path_txt.setText(java_path);
		layout_path_txt.setText(layout_path);
		res_path_txt.setText(resources_path);
		libs_path_txt.setText(libs_path);
		impl_txt.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)60, getResources().getColor(R.color.md_theme_inversePrimary)));
		textview1.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/enbold.ttf"), 0);
		textview2.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/enlight.ttf"), 0);
		build_output_txt.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/enlight.ttf"), 0);
		textview3.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/enbold.ttf"), 0);
		textview6.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/enbold.ttf"), 1);
		impl_txt.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/enmedium.ttf"), 0);
		assets_path_txt.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/ooo.ttf"), 0);
		java_path_txt.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/ooo.ttf"), 0);
		layout_path_txt.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/ooo.ttf"), 0);
	}
	
}
