package org.ide.andriod;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import java.util.HashMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class DiffDialogFragment extends DialogFragment {

	private String projectPath = "";
	private String relativePath = "";
	private GitBridge gitBridge;

	private LinearLayout diff_root;
	private ImageView close_btn;
	private TextView file_name_txt;
	private RecyclerView diff_rec;
	private ArrayList<HashMap<String, Object>> diff_listmap = new ArrayList<>();
	private DiffRecAdapter diffRecAdapter;

	public static DiffDialogFragment newInstance(String _projectPath, String _relativePath) {
		DiffDialogFragment _f = new DiffDialogFragment();
		Bundle _args = new Bundle();
		_args.putString("projectPath", _projectPath);
		_args.putString("relativePath", _relativePath);
		_f.setArguments(_args);
		return _f;
	}

	@Override
	public void onCreate(@Nullable Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Light_NoTitleBar_Fullscreen);

		if (getArguments() != null) {
			projectPath = getArguments().getString("projectPath", "");
			relativePath = getArguments().getString("relativePath", "");
		}
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		return _inflater.inflate(R.layout.diff_dialog_fragment, _container, false);
	}

	@Override
	public void onViewCreated(@NonNull View _view, @Nullable Bundle _savedInstanceState) {
		super.onViewCreated(_view, _savedInstanceState);

		diff_root = _view.findViewById(R.id.diff_root);
		close_btn = _view.findViewById(R.id.close_btn);
		file_name_txt = _view.findViewById(R.id.file_name_txt);
		diff_rec = _view.findViewById(R.id.diff_rec);

		diff_root.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.md_theme_background));
		file_name_txt.setText(relativePath);

		close_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) {
				dismiss();
			}
		});

		diff_rec.setLayoutManager(new LinearLayoutManager(getContext()));
		diffRecAdapter = new DiffRecAdapter(diff_listmap);
		diff_rec.setAdapter(diffRecAdapter);

		gitBridge = new GitBridge();
		gitBridge._setProjectPath(projectPath);

		_loadDiff();
	}

	@Override
	public void onStart() {
		super.onStart();
		if (getDialog() != null && getDialog().getWindow() != null) {
			getDialog().getWindow().setLayout(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT
			);
			getDialog().getWindow().setBackgroundDrawable(
				new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT)
			);
		}
	}

	private void _loadDiff() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				final ArrayList<HashMap<String, Object>> _rows = new ArrayList<>(gitBridge._getFileDiff(relativePath));

				if (getActivity() == null) return;
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						diff_listmap.clear();
						diff_listmap.addAll(_rows);
						diffRecAdapter.notifyDataSetChanged();
					}
				});
			}
		}).start();
	}

	public static class DiffRecAdapter extends RecyclerView.Adapter<DiffRecAdapter.ViewHolder> {

		ArrayList<HashMap<String, Object>> _data;

		public DiffRecAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}

		@NonNull
		@Override
		public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			View _v = LayoutInflater.from(parent.getContext()).inflate(R.layout.diff_line_item, parent, false);
			return new ViewHolder(_v);
		}

		@Override
		public void onBindViewHolder(@NonNull ViewHolder holder, int _position) {
			final HashMap<String, Object> _item = _data.get(_position);
			final String _type = (String) _item.get("type");
			final String _content = (String) _item.get("content");
			final String _oldNum = (String) _item.get("oldLineNum");
			final String _newNum = (String) _item.get("newLineNum");

			holder.old_line_num.setText(_oldNum);
			holder.new_line_num.setText(_newNum);
			holder.diff_content.setText(_content);

			if ("added".equals(_type)) {
				holder.diff_row_bg.setBackgroundColor(Color.parseColor("#1B3B24"));
				holder.diff_marker.setText("+");
				holder.diff_marker.setTextColor(Color.parseColor("#4CAF50"));
			} else if ("removed".equals(_type)) {
				holder.diff_row_bg.setBackgroundColor(Color.parseColor("#3B1B1B"));
				holder.diff_marker.setText("-");
				holder.diff_marker.setTextColor(Color.parseColor("#F44336"));
			} else {
				holder.diff_row_bg.setBackgroundColor(Color.TRANSPARENT);
				holder.diff_marker.setText("");
			}
		}

		@Override
		public int getItemCount() {
			return _data == null ? 0 : _data.size();
		}

		public static class ViewHolder extends RecyclerView.ViewHolder {
			LinearLayout diff_row_bg;
			TextView old_line_num;
			TextView new_line_num;
			TextView diff_marker;
			TextView diff_content;

			public ViewHolder(@NonNull View itemView) {
				super(itemView);
				diff_row_bg = itemView.findViewById(R.id.diff_row_bg);
				old_line_num = itemView.findViewById(R.id.old_line_num);
				new_line_num = itemView.findViewById(R.id.new_line_num);
				diff_marker = itemView.findViewById(R.id.diff_marker);
				diff_content = itemView.findViewById(R.id.diff_content);
			}
		}
	}
}