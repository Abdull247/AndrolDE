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
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.LinearLayout;
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
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener;
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
import java.util.regex.*;
import org.antlr.v4.runtime.*;
import org.benf.cfr.reader.*;
import org.eclipse.jdt.*;
import org.json.*;
import androidx.fragment.app.FragmentTransaction;

public class FilesFragFragmentActivity extends Fragment {
	
	private String path = "";
	private String name = "";
	private EditorBridge bridge;
	private FilesSubFrag1FragmentActivity frag1;
	private FilesSubFrag2FragmentActivity frag2;
	private FilesSubFrag3FragmentActivity frag3;
	
	private LinearLayout main_back;
	private TabLayout tablayout1;
	private FrameLayout appFrame;
	
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.files_frag_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		main_back = _view.findViewById(R.id.main_back);
		tablayout1 = _view.findViewById(R.id.tablayout1);
		appFrame = _view.findViewById(R.id.appFrame);
	}
	
	private void initializeLogic() {
		path = getArguments() != null ? getArguments().getString("path") : "";
		name = getArguments() != null ? getArguments().getString("name") : "";
		bridge = ((EditorActivity) getActivity()).getBridge();
		
		Bundle args = new Bundle();
		args.putString("path", path);
		args.putString("name", name);
		
		frag1 = new FilesSubFrag1FragmentActivity();
		frag1.setArguments(args);
		frag2 = new FilesSubFrag2FragmentActivity();
		frag2.setArguments(args);
		frag3 = new FilesSubFrag3FragmentActivity();
		frag3.setArguments(args);
		
		FragmentTransaction ft = getChildFragmentManager().beginTransaction();
		ft.add(R.id.appFrame, frag1, "tab1");
		ft.add(R.id.appFrame, frag2, "tab2");
		ft.hide(frag2);
		ft.add(R.id.appFrame, frag3, "tab3");
		ft.hide(frag3);
		ft.commit();
		
		tablayout1.addTab(tablayout1.newTab().setText("Opened files"));
		tablayout1.addTab(tablayout1.newTab().setText("Project"));
		tablayout1.addTab(tablayout1.newTab().setText("Files"));
		
		tablayout1.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				FragmentTransaction _ft = getChildFragmentManager().beginTransaction();
				_ft.hide(frag1);
				_ft.hide(frag2);
				_ft.hide(frag3);
				if (tab.getPosition() == 0) {
					_ft.show(frag1);
				} else if (tab.getPosition() == 1) {
					_ft.show(frag2);
				} else if (tab.getPosition() == 2) {
					_ft.show(frag3);
				}
				_ft.commit();
			}
			
			@Override
			public void onTabUnselected(TabLayout.Tab tab) {
			}
			
			@Override
			public void onTabReselected(TabLayout.Tab tab) {
			}
		});
	}
	
}