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
import android.view.View;
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

public class GitHistoryFragmentActivity extends Fragment {
	
	private String path = "";
	private String name = "";
	private GitBridge gitBridge;
	private Recyclerview1Adapter recyclerview1Adapter;
	
	private ArrayList<HashMap<String, Object>> history_listmap = new ArrayList<>();
	
	private LinearLayout linear1;
	private NestedScrollView nestedScrollView1;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private RecyclerView recyclerview1;
	private TextView textview6;
	private MaterialCardView cardview1;
	private LinearLayout linear8;
	private TextView textview7;
	
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.git_history_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		linear1 = _view.findViewById(R.id.linear1);
		nestedScrollView1 = _view.findViewById(R.id.nestedScrollView1);
		linear2 = _view.findViewById(R.id.linear2);
		linear3 = _view.findViewById(R.id.linear3);
		recyclerview1 = _view.findViewById(R.id.recyclerview1);
		textview6 = _view.findViewById(R.id.textview6);
		cardview1 = _view.findViewById(R.id.cardview1);
		linear8 = _view.findViewById(R.id.linear8);
		textview7 = _view.findViewById(R.id.textview7);
		
		cardview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_loadHistory();
			}
		});
	}
	
	private void initializeLogic() {
		path = getArguments() != null ? getArguments().getString("path") : "";
		name = getArguments() != null ? getArguments().getString("name") : "";
		gitBridge = ((EditorActivity) getActivity()).getGitBridge();
		
		recyclerview1.setLayoutManager(new LinearLayoutManager(getContext()));
		recyclerview1Adapter = new Recyclerview1Adapter(history_listmap);
		recyclerview1.setAdapter(recyclerview1Adapter);
		
		_loadHistory();
	}
	
	public void _anchore() {
		
	}
    
    public void _loadHistory() {
	new Thread(new Runnable() {
		@Override
		public void run() {
			final java.util.List<org.eclipse.jgit.revwalk.RevCommit> _commits = gitBridge._log();
			final String _headId = gitBridge._getHeadCommitId();

			final ArrayList<HashMap<String, Object>> _list = new ArrayList<>();
			SimpleDateFormat _dateFmt = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
			SimpleDateFormat _timeFmt = new SimpleDateFormat("HH:mm", Locale.getDefault());

			for (org.eclipse.jgit.revwalk.RevCommit _c : _commits) {
				HashMap<String, Object> _item = new HashMap<>();
				Date _commitDate = new Date(_c.getCommitTime() * 1000L);

				_item.put("commitText", _c.getShortMessage());
				_item.put("commitId", _c.getName().substring(0, 7));
				_item.put("fullCommitId", _c.getName());
				_item.put("isHead", _c.getName().equals(_headId));
				_item.put("infoText", "Android IDE" + " • " + _dateFmt.format(_commitDate) + " • " + _timeFmt.format(_commitDate));

				_list.add(_item);
			}

			if (getActivity() == null) return;
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					history_listmap.clear();
					history_listmap.addAll(_list);
					recyclerview1Adapter.notifyDataSetChanged();
				}
			});
		}
	}).start();
}
	
	public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getActivity().getLayoutInflater();
			View _v = _inflater.inflate(R.layout.commits_history_item, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final com.google.android.material.card.MaterialCardView item_cd = _view.findViewById(R.id.item_cd);
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final LinearLayout linear4 = _view.findViewById(R.id.linear4);
			final LinearLayout linear5 = _view.findViewById(R.id.linear5);
			final com.google.android.material.card.MaterialCardView pin_cd = _view.findViewById(R.id.pin_cd);
			final LinearLayout pin_lin = _view.findViewById(R.id.pin_lin);
			final LinearLayout linear6 = _view.findViewById(R.id.linear6);
			final LinearLayout linear8 = _view.findViewById(R.id.linear8);
			final TextView head_badge = _view.findViewById(R.id.head_badge);
			final TextView commit_id = _view.findViewById(R.id.commit_id);
			final TextView commit_txt = _view.findViewById(R.id.commit_txt);
			final TextView commit_info_txt = _view.findViewById(R.id.commit_info_txt);
			final com.google.android.material.card.MaterialCardView more_cd = _view.findViewById(R.id.more_cd);
			final LinearLayout linear9 = _view.findViewById(R.id.linear9);
			final ImageView imageview1 = _view.findViewById(R.id.imageview1);
			
			head_badge.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)10, getResources().getColor(R.color.md_theme_onPrimaryContainer)));
			head_badge.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/enbold.ttf"), 0);
			commit_id.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/enlight.ttf"), 0);
			commit_txt.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/enbold.ttf"), 0);
			commit_info_txt.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/enlight.ttf"), 0);
			final HashMap<String, Object> _item = _data.get(_position);
			final String _commitText = (String) _item.get("commitText");
			final String _commitId = (String) _item.get("commitId");
			final String _infoText = (String) _item.get("infoText");
			final boolean _isHead = (boolean) _item.get("isHead");
			
			commit_txt.setText(_commitText);
			commit_info_txt.setText(_infoText);
			commit_id.setText(_commitId);
			
			head_badge.setVisibility(_isHead ? View.VISIBLE : View.GONE);
			
			if (_position == _data.size() - 1) {
				pin_lin.setVisibility(View.GONE);
			} else {
				pin_lin.setVisibility(View.VISIBLE);
			}
			item_cd.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					
				}
			});
			more_cd.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					
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