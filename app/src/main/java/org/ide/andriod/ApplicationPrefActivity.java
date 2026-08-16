package org.ide.andriod;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.content.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.*;
import android.graphics.*;
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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
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
import androidx.slidingpanelayout.*;
import androidx.swiperefreshlayout.*;
import androidx.vectordrawable.*;
import androidx.versionedparcelable.*;
import androidx.viewpager.*;
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
import java.io.InputStream;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.antlr.v4.runtime.*;
import org.benf.cfr.reader.*;
import org.eclipse.jdt.*;
import org.json.*;

public class ApplicationPrefActivity extends AppCompatActivity {
	
	private LinearLayout linear1;
	private ScrollView vscroll1;
	private TextView textview1;
	private LinearLayout linear2;
	private TextView textview23;
	private LinearLayout linear28;
	private LinearLayout linear31;
	private TextView textview7;
	private LinearLayout linear11;
	private LinearLayout linear13;
	private LinearLayout linear17;
	private TextView textview2;
	private LinearLayout linear3;
	private LinearLayout linear6;
	private LinearLayout linear5;
	private TextView textview14;
	private LinearLayout linear18;
	private LinearLayout linear22;
	private LinearLayout linear24;
	private ImageView imageview12;
	private LinearLayout linear29;
	private TextView textview26;
	private TextView textview27;
	private ImageView imageview5;
	private LinearLayout linear12;
	private TextView textview10;
	private TextView textview11;
	private ImageView imageview6;
	private LinearLayout linear14;
	private TextView textview12;
	private TextView textview13;
	private ImageView imageview2;
	private LinearLayout linear4;
	private Switch switch1;
	private TextView textview4;
	private TextView textview3;
	private ImageView imageview3;
	private LinearLayout linear7;
	private Switch switch2;
	private TextView textview5;
	private TextView textview6;
	private ImageView imageview7;
	private LinearLayout linear19;
	private TextView textview15;
	private TextView textview16;
	private ImageView imageview9;
	private LinearLayout linear23;
	private Switch switch3;
	private TextView textview19;
	private TextView textview20;
	private ImageView imageview10;
	private LinearLayout linear25;
	private Switch switch4;
	private TextView textview21;
	private TextView textview22;
	
	private SharedPreferences s;
	private Intent i = new Intent();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.application_pref);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = findViewById(R.id.linear1);
		vscroll1 = findViewById(R.id.vscroll1);
		textview1 = findViewById(R.id.textview1);
		linear2 = findViewById(R.id.linear2);
		textview23 = findViewById(R.id.textview23);
		linear28 = findViewById(R.id.linear28);
		linear31 = findViewById(R.id.linear31);
		textview7 = findViewById(R.id.textview7);
		linear11 = findViewById(R.id.linear11);
		linear13 = findViewById(R.id.linear13);
		linear17 = findViewById(R.id.linear17);
		textview2 = findViewById(R.id.textview2);
		linear3 = findViewById(R.id.linear3);
		linear6 = findViewById(R.id.linear6);
		linear5 = findViewById(R.id.linear5);
		textview14 = findViewById(R.id.textview14);
		linear18 = findViewById(R.id.linear18);
		linear22 = findViewById(R.id.linear22);
		linear24 = findViewById(R.id.linear24);
		imageview12 = findViewById(R.id.imageview12);
		linear29 = findViewById(R.id.linear29);
		textview26 = findViewById(R.id.textview26);
		textview27 = findViewById(R.id.textview27);
		imageview5 = findViewById(R.id.imageview5);
		linear12 = findViewById(R.id.linear12);
		textview10 = findViewById(R.id.textview10);
		textview11 = findViewById(R.id.textview11);
		imageview6 = findViewById(R.id.imageview6);
		linear14 = findViewById(R.id.linear14);
		textview12 = findViewById(R.id.textview12);
		textview13 = findViewById(R.id.textview13);
		imageview2 = findViewById(R.id.imageview2);
		linear4 = findViewById(R.id.linear4);
		switch1 = findViewById(R.id.switch1);
		textview4 = findViewById(R.id.textview4);
		textview3 = findViewById(R.id.textview3);
		imageview3 = findViewById(R.id.imageview3);
		linear7 = findViewById(R.id.linear7);
		switch2 = findViewById(R.id.switch2);
		textview5 = findViewById(R.id.textview5);
		textview6 = findViewById(R.id.textview6);
		imageview7 = findViewById(R.id.imageview7);
		linear19 = findViewById(R.id.linear19);
		textview15 = findViewById(R.id.textview15);
		textview16 = findViewById(R.id.textview16);
		imageview9 = findViewById(R.id.imageview9);
		linear23 = findViewById(R.id.linear23);
		switch3 = findViewById(R.id.switch3);
		textview19 = findViewById(R.id.textview19);
		textview20 = findViewById(R.id.textview20);
		imageview10 = findViewById(R.id.imageview10);
		linear25 = findViewById(R.id.linear25);
		switch4 = findViewById(R.id.switch4);
		textview21 = findViewById(R.id.textview21);
		textview22 = findViewById(R.id.textview22);
		s = getSharedPreferences("s", Activity.MODE_PRIVATE);
		
		linear28.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), AboutAppActivity.class);
				startActivity(i);
			}
		});
		
		switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
				final boolean _isChecked = _param2;
				if (_isChecked) {
					s.edit().putString("checked1", ".").commit();
				} else {
					s.edit().remove("checked1").commit();
				}
			}
		});
		
		switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
				final boolean _isChecked = _param2;
				if (_isChecked) {
					s.edit().putString("ask", "...").commit();
					switch1.setChecked(true);
				} else {
					s.edit().remove("ask").commit();
					switch1.setChecked(false);
				}
			}
		});
	}
	
	private void initializeLogic() {
		if (s.contains("checked1")) {
			switch1.setChecked(true);
		}
		if (s.contains("ask")) {
			switch2.setChecked(true);
		}
		switch4.setChecked(true);
		switch4.setEnabled(false);
		getWindow().setNavigationBarColor(Color.TRANSPARENT);
		getWindow().setStatusBarColor(Color.TRANSPARENT);
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