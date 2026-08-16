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
import androidx.fragment.app.FragmentStatePagerAdapter;
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
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnAdapterChangeListener;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
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

public class RunFragFragmentActivity extends Fragment {
	
	private GitBridge gitBridge;
	private EditorBridge bridge;
	private String path = "";
	private String name = "";
	
	private LinearLayout main_back;
	private TabLayout tab;
	private ViewPager viewpager1;
	
	private FragFragmentAdapter frag;
	
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.run_frag_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		main_back = _view.findViewById(R.id.main_back);
		tab = _view.findViewById(R.id.tab);
		viewpager1 = _view.findViewById(R.id.viewpager1);
		frag = new FragFragmentAdapter(getContext().getApplicationContext(), getActivity().getSupportFragmentManager());
	}
	
	private void initializeLogic() {
		path = getArguments() != null ? getArguments().getString("path") : "";
		name = getArguments() != null ? getArguments().getString("name") : "";
		bridge = ((EditorActivity) getActivity()).getBridge();
		
		gitBridge = ((EditorActivity) getActivity()).getGitBridge();
		tab.setupWithViewPager(viewpager1);
		frag.setTabCount(4);
		viewpager1.setAdapter(frag);
		((PagerAdapter)viewpager1.getAdapter()).notifyDataSetChanged();
	}
	
	public class FragFragmentAdapter extends FragmentStatePagerAdapter {
		// This class is deprecated, you should migrate to ViewPager2:
		// https://developer.android.com/reference/androidx/viewpager2/widget/ViewPager2
		Context context;
		int tabCount;
		
		public FragFragmentAdapter(Context context, FragmentManager manager) {
			super(manager);
			this.context = context;
		}
		
		public void setTabCount(int tabCount) {
			this.tabCount = tabCount;
		}
		
		@Override
		public int getCount() {
			return tabCount;
		}
		
		@Override
		public CharSequence getPageTitle(int _position) {
			if (_position == 0) {
				return "Changes";
			}
			if (_position == 1) {
				return "History";
			}
			if (_position == 2) {
				return "Branches";
			}
			if (_position == 3) {
				return "Remote";
			}
			return "";
		}
		
		
		@Override
		public Fragment getItem(int _position) {
			if (_position == 0) {
				GitChangesFragmentActivity _f = new GitChangesFragmentActivity();
				Bundle _args = new Bundle();
				_args.putString("path", path);
				_args.putString("name", name);
				_f.setArguments(_args);
				return _f;
			}
			if (_position == 1) {
				GitHistoryFragmentActivity _f = new GitHistoryFragmentActivity();
				Bundle _args = new Bundle();
				_args.putString("path", path);
				_args.putString("name", name);
				_f.setArguments(_args);
				return _f;
			}
			if (_position == 2) {
				GitBranchesFragmentActivity _f = new GitBranchesFragmentActivity();
				Bundle _args = new Bundle();
				_args.putString("path", path);
				_args.putString("name", name);
				_f.setArguments(_args);
				return _f;
			}
			if (_position == 3) {
				GitRemoteFragmentActivity _f = new GitRemoteFragmentActivity();
				Bundle _args = new Bundle();
				_args.putString("path", path);
				_args.putString("name", name);
				_f.setArguments(_args);
				return _f;
			}
			return new Fragment();
		}
		
	}
	
}