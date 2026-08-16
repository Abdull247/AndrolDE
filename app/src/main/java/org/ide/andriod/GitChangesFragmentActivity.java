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
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.vectordrawable.*;
import androidx.versionedparcelable.*;
import androidx.viewpager.*;
import com.google.android.material.button.*;
import com.google.android.material.card.*;
import com.google.gson.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;
import org.json.*;

public class GitChangesFragmentActivity extends Fragment {
	
	private GitBridge gitBridge;
	private Unstagged_recAdapter unstagged_recAdapter;
	private Stagged_recAdapter stagged_recAdapter;
	private String path = "";
	private String name = "";
	
	private ArrayList<HashMap<String, Object>> unstagged_listmap = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> stagged_listmap = new ArrayList<>();
	
	private RelativeLayout mainRelative;
	private LinearLayout linear1;
	private LinearLayout linear9;
	private NestedScrollView nestedScrollView1;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private RecyclerView unstagged_rec;
	private LinearLayout linear6;
	private RecyclerView stagged_rec;
	private TextView textview6;
	private MaterialCardView cardview1;
	private LinearLayout linear8;
	private TextView textview7;
	private ImageView unstagged_collapse_icon;
	private TextView textview1;
	private TextView unstagged_count;
	private MaterialCardView stage_all_cd;
	private LinearLayout linear5;
	private TextView textview2;
	private ImageView stagged_collapse_icon;
	private TextView textview3;
	private TextView stagged_count;
	private MaterialCardView unstage_all_cd;
	private LinearLayout linear7;
	private TextView textview5;
	private MaterialButton commit_btn;
	
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.git_changes_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		mainRelative = _view.findViewById(R.id.mainRelative);
		linear1 = _view.findViewById(R.id.linear1);
		linear9 = _view.findViewById(R.id.linear9);
		nestedScrollView1 = _view.findViewById(R.id.nestedScrollView1);
		linear2 = _view.findViewById(R.id.linear2);
		linear3 = _view.findViewById(R.id.linear3);
		linear4 = _view.findViewById(R.id.linear4);
		unstagged_rec = _view.findViewById(R.id.unstagged_rec);
		linear6 = _view.findViewById(R.id.linear6);
		stagged_rec = _view.findViewById(R.id.stagged_rec);
		textview6 = _view.findViewById(R.id.textview6);
		cardview1 = _view.findViewById(R.id.cardview1);
		linear8 = _view.findViewById(R.id.linear8);
		textview7 = _view.findViewById(R.id.textview7);
		unstagged_collapse_icon = _view.findViewById(R.id.unstagged_collapse_icon);
		textview1 = _view.findViewById(R.id.textview1);
		unstagged_count = _view.findViewById(R.id.unstagged_count);
		stage_all_cd = _view.findViewById(R.id.stage_all_cd);
		linear5 = _view.findViewById(R.id.linear5);
		textview2 = _view.findViewById(R.id.textview2);
		stagged_collapse_icon = _view.findViewById(R.id.stagged_collapse_icon);
		textview3 = _view.findViewById(R.id.textview3);
		stagged_count = _view.findViewById(R.id.stagged_count);
		unstage_all_cd = _view.findViewById(R.id.unstage_all_cd);
		linear7 = _view.findViewById(R.id.linear7);
		textview5 = _view.findViewById(R.id.textview5);
		commit_btn = _view.findViewById(R.id.commit_btn);
		
		linear4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (unstagged_rec.getVisibility() == View.VISIBLE) {
					unstagged_collapse_icon.setImageResource(R.drawable.icon_keyboard_arrow_up_round);
					_TransitionManager(linear2, 150);
					unstagged_rec.setVisibility(View.GONE);
				} else {
					unstagged_collapse_icon.setImageResource(R.drawable.icon_keyboard_arrow_down_round);
					_TransitionManager(linear2, 150);
					unstagged_rec.setVisibility(View.VISIBLE);
				}
			}
		});
		
		linear6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (stagged_rec.getVisibility() == View.VISIBLE) {
					stagged_collapse_icon.setImageResource(R.drawable.icon_keyboard_arrow_up_round);
					_TransitionManager(linear2, 150);
					stagged_rec.setVisibility(View.GONE);
				} else {
					stagged_collapse_icon.setImageResource(R.drawable.icon_keyboard_arrow_down_round);
					_TransitionManager(linear2, 150);
					stagged_rec.setVisibility(View.VISIBLE);
				}
			}
		});
		
		cardview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_refreshStatus();
			}
		});
		
		stage_all_cd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		unstage_all_cd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
	}
	
	private void initializeLogic() {
		_initializeCrashLogger();
		textview6.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/enbold.ttf"), 0);
		unstagged_count.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)90, (int)0, Color.TRANSPARENT, 0xFF2196F3));
		stagged_count.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b, int c, int d) { this.setCornerRadius(a); this.setStroke(b, c); this.setColor(d); return this; } }.getIns((int)90, (int)0, Color.TRANSPARENT, 0xFF2196F3));
		path = getArguments() != null ? getArguments().getString("path") : "";
		name = getArguments() != null ? getArguments().getString("name") : "";
		gitBridge = ((EditorActivity) getActivity()).getGitBridge();
		
		unstagged_rec.setLayoutManager(new LinearLayoutManager(getContext()));
		unstagged_recAdapter = new Unstagged_recAdapter(unstagged_listmap);
		unstagged_rec.setAdapter(unstagged_recAdapter);
		
		stagged_rec.setLayoutManager(new LinearLayoutManager(getContext()));
		stagged_recAdapter = new Stagged_recAdapter(stagged_listmap);
		stagged_rec.setAdapter(stagged_recAdapter);
		
		_refreshStatus();
		stage_all_cd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						gitBridge._stageAll();
						if (getActivity() != null) {
							getActivity().runOnUiThread(new Runnable() {
								@Override
								public void run() {
									_refreshStatus();
								}
							});
						}
					}
				}).start();
			}
		});
		
		unstage_all_cd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						gitBridge._unstageAll();
						if (getActivity() != null) {
							getActivity().runOnUiThread(new Runnable() {
								@Override
								public void run() {
									_refreshStatus();
								}
							});
						}
					}
				}).start();
			}
		});
		final float density = getResources().getDisplayMetrics().density;
		final float defaultRadius = 28f * density;  // Circular shape
		final float pressedRadius = 8f * density;   // Squircle/square shape
		
		if (commit_btn != null) {
			// Set initial circular shape
			commit_btn.setShapeAppearanceModel(commit_btn.getShapeAppearanceModel().toBuilder()
			.setAllCornerSizes(defaultRadius)
			.build());
			
			// Add touch listener for morphing animation
			commit_btn.setOnTouchListener(new android.view.View.OnTouchListener() {
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
					
					android.graphics.RectF rect = new android.graphics.RectF(0, 0, commit_btn.getWidth(), commit_btn.getHeight());
					float startVal = commit_btn.getShapeAppearanceModel().getBottomLeftCornerSize().getCornerSize(rect);
					
					animator = android.animation.ValueAnimator.ofFloat(startVal, target);
					animator.setDuration(duration);
					animator.setInterpolator(interpolator);
					
					animator.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() {
						@Override
						public void onAnimationUpdate(android.animation.ValueAnimator animation) {
							float value = (float) animation.getAnimatedValue();
							commit_btn.setShapeAppearanceModel(commit_btn.getShapeAppearanceModel().toBuilder()
							.setAllCornerSizes(value)
							.build());
						}
					});
					animator.start();
				}
			});
		}
		if (unstagged_rec.getVisibility() == View.VISIBLE) {
			unstagged_collapse_icon.setImageResource(R.drawable.icon_keyboard_arrow_down_round);
		} else {
			unstagged_collapse_icon.setImageResource(R.drawable.icon_keyboard_arrow_up_round);
		}
		if (stagged_rec.getVisibility() == View.VISIBLE) {
			stagged_collapse_icon.setImageResource(R.drawable.icon_keyboard_arrow_down_round);
		} else {
			stagged_collapse_icon.setImageResource(R.drawable.icon_keyboard_arrow_up_round);
		}
		commit_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
				View _alertView = getActivity().getLayoutInflater().inflate(R.layout.edit, null);
				
				com.google.android.material.dialog.MaterialAlertDialogBuilder _d = new com.google.android.material.dialog.MaterialAlertDialogBuilder(getContext());
				_d.setTitle("Commit changes");
				_d.setMessage("Enter a commit message:");
				_d.setView(_alertView);
				
				final com.google.android.material.textfield.TextInputEditText _input = (com.google.android.material.textfield.TextInputEditText) _alertView.findViewById(R.id.edittext1);
				_input.setLines(3);
				_input.setMinLines(3);
				_input.setGravity(Gravity.TOP | Gravity.START);
				_input.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_FLAG_MULTI_LINE);
				
				_d.setPositiveButton("Commit", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						final String _message = _input.getText().toString().trim();
						
						if (_message.isEmpty()) {
							com.google.android.material.snackbar.Snackbar.make(linear1, "Commit message can't be empty", com.google.android.material.snackbar.Snackbar.LENGTH_SHORT)
							.setAction("OK", new View.OnClickListener() {
								@Override
								public void onClick(View _view) {}
							}).show();
							return;
						}
						
						new Thread(new Runnable() {
							@Override
							public void run() {
								final int _result = gitBridge._commit(_message);
								if (getActivity() == null) return;
								getActivity().runOnUiThread(new Runnable() {
									@Override
									public void run() {
										_refreshStatus();
										String _msg;
										if (_result == GitBridge.RESULT_SUCCESS) {
											_msg = "Committed successfully!";
										} else if (_result == GitBridge.RESULT_NO_CHANGES) {
											_msg = "No staged changes to commit";
										} else {
											_msg = "Commit failed";
										}
										com.google.android.material.snackbar.Snackbar.make(linear1, _msg, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT)
										.setAction("OK", new View.OnClickListener() {
											@Override
											public void onClick(View _view) {}
										}).show();
									}
								});
							}
						}).start();
					}
				});
				
				_d.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						
					}
				});
				
				_d.create().show();
			}
		});
	}
	
	@Override
	public void onResume() {
		super.onResume();
		_refreshStatus();
	}
	
	@Override
	public void onStart() {
		super.onStart();
		_refreshStatus();
	}
	public void _anchore() {
		
	}
    
    public void _refreshStatus() {
	new Thread(new Runnable() {
		@Override
		public void run() {
			final org.eclipse.jgit.api.Status _status = gitBridge._status();
			if (_status == null) return;

			final ArrayList<HashMap<String, Object>> _unstagged = new ArrayList<>();
			final ArrayList<HashMap<String, Object>> _stagged = new ArrayList<>();

			for (String _f : _status.getUntracked()) {
				HashMap<String, Object> _item = new HashMap<>();
				_item.put("name", Uri.parse(_f).getLastPathSegment());
				_item.put("path", path.concat("/").concat(_f));
				_item.put("relativePath", _f);
				_item.put("changeType", "new");
				_unstagged.add(_item);
			}
			for (String _f : _status.getModified()) {
				HashMap<String, Object> _item = new HashMap<>();
				_item.put("name", Uri.parse(_f).getLastPathSegment());
				_item.put("path", path.concat("/").concat(_f));
				_item.put("relativePath", _f);
				_item.put("changeType", "modified");
				_unstagged.add(_item);
			}
			for (String _f : _status.getMissing()) {
				HashMap<String, Object> _item = new HashMap<>();
				_item.put("name", Uri.parse(_f).getLastPathSegment());
				_item.put("path", path.concat("/").concat(_f));
				_item.put("relativePath", _f);
				_item.put("changeType", "deleted");
				_unstagged.add(_item);
			}

			for (String _f : _status.getAdded()) {
				HashMap<String, Object> _item = new HashMap<>();
				_item.put("name", Uri.parse(_f).getLastPathSegment());
				_item.put("path", path.concat("/").concat(_f));
				_item.put("relativePath", _f);
				_item.put("changeType", "new");
				_stagged.add(_item);
			}
			for (String _f : _status.getChanged()) {
				HashMap<String, Object> _item = new HashMap<>();
				_item.put("name", Uri.parse(_f).getLastPathSegment());
				_item.put("path", path.concat("/").concat(_f));
				_item.put("relativePath", _f);
				_item.put("changeType", "modified");
				_stagged.add(_item);
			}
			for (String _f : _status.getRemoved()) {
				HashMap<String, Object> _item = new HashMap<>();
				_item.put("name", Uri.parse(_f).getLastPathSegment());
				_item.put("path", path.concat("/").concat(_f));
				_item.put("relativePath", _f);
				_item.put("changeType", "deleted");
				_stagged.add(_item);
			}

			if (getActivity() == null) return;
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					unstagged_listmap.clear();
					unstagged_listmap.addAll(_unstagged);
					unstagged_recAdapter.notifyDataSetChanged();
					unstagged_count.setText(String.valueOf(_unstagged.size()));

					stagged_listmap.clear();
					stagged_listmap.addAll(_stagged);
					stagged_recAdapter.notifyDataSetChanged();
					stagged_count.setText(String.valueOf(_stagged.size()));
				}
			});
		}
	}).start();
}
	
	
	public void _initializeCrashLogger() {
		try {
			final Thread.UncaughtExceptionHandler defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
			Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
				@Override
				public void uncaughtException(Thread thread, Throwable throwable) {
					try {
						android.content.Intent intent = new android.content.Intent(getContext().getApplicationContext(), DebugActivity.class);
						intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK);
						intent.putExtra("error", android.util.Log.getStackTraceString(throwable));
						startActivity(intent);
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
	
	
	public void _TransitionManager(final View _view, final double _duration) {
		LinearLayout viewgroup =(LinearLayout) _view;
		
		android.transition.AutoTransition autoTransition = new android.transition.AutoTransition(); autoTransition.setDuration((long)_duration);
		autoTransition.setInterpolator(new android.view.animation.DecelerateInterpolator()); android.transition.TransitionManager.beginDelayedTransition(viewgroup, autoTransition);
	}
	
	public class Unstagged_recAdapter extends RecyclerView.Adapter<Unstagged_recAdapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Unstagged_recAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getActivity().getLayoutInflater();
			View _v = _inflater.inflate(R.layout.git_stage_item, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final com.google.android.material.card.MaterialCardView item_cd = _view.findViewById(R.id.item_cd);
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final ImageView stage_icon = _view.findViewById(R.id.stage_icon);
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final ImageView diff_icon = _view.findViewById(R.id.diff_icon);
			final ImageView plus_btn = _view.findViewById(R.id.plus_btn);
			final TextView file_name = _view.findViewById(R.id.file_name);
			final TextView file_path = _view.findViewById(R.id.file_path);
			
			file_name.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/enbold.ttf"), 0);
			file_path.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/enlight.ttf"), 0);
			final HashMap<String, Object> _item = _data.get(_position);
			final String _name = (String) _item.get("name");
			final String _path = (String) _item.get("path");
			final String _relativePath = (String) _item.get("relativePath");
			final String _changeType = (String) _item.get("changeType");
			
			file_name.setText(_name);
			file_path.setText(_relativePath);
			
			String _fileName = _name != null ? _name.toLowerCase() : "";
			
			if (_fileName.endsWith(".html")) {
				stage_icon.setImageResource(R.drawable.html_icon);
			} else if (_fileName.endsWith(".css")) {
				stage_icon.setImageResource(R.drawable.css_icon);
			} else if (_fileName.endsWith(".gradle")) {
				stage_icon.setImageResource(R.drawable.gradle_icon);
			} else if (_fileName.endsWith(".java")) {
				stage_icon.setImageResource(R.drawable.java_icon);
			} else if (_fileName.endsWith(".js")) {
				stage_icon.setImageResource(R.drawable.js_icon);
			} else if (_fileName.endsWith(".json")) {
				stage_icon.setImageResource(R.drawable.json_icon);
			} else if (_fileName.endsWith(".xml")) {
				stage_icon.setImageResource(R.drawable.xml_icon);
			} else if (_fileName.endsWith(".png") || _fileName.endsWith(".jpg") || _fileName.endsWith(".jpeg")) {
				stage_icon.setImageResource(R.drawable.image_icon);
			} else if (_fileName.equalsIgnoreCase(".gitignore") || _fileName.endsWith(".git")) {
				stage_icon.setImageResource(R.drawable.git_icon);
			} else {
				stage_icon.setImageResource(R.drawable.file_icon);
			}
			
			//change_badge.setText("new".equals(_changeType) ? "+" : "modified".equals(_changeType) ? "M" : "−");
			
			plus_btn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					new Thread(new Runnable() {
						@Override
						public void run() {
							gitBridge._stageFile(_relativePath, "deleted".equals(_changeType));
							if (getActivity() != null) {
								getActivity().runOnUiThread(new Runnable() {
									@Override
									public void run() {
										_refreshStatus();
									}
								});
							}
						}
					}).start();
				}
			});
			
			diff_icon.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					DiffDialogFragment _dialog = DiffDialogFragment.newInstance(path, _relativePath);
					_dialog.show(getParentFragmentManager(), "diff_dialog");
				}
			});
			item_cd.setOnClickListener(new View.OnClickListener() {
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
	
	public class Stagged_recAdapter extends RecyclerView.Adapter<Stagged_recAdapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Stagged_recAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getActivity().getLayoutInflater();
			View _v = _inflater.inflate(R.layout.git_stage_item, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final com.google.android.material.card.MaterialCardView item_cd = _view.findViewById(R.id.item_cd);
			final LinearLayout linear1 = _view.findViewById(R.id.linear1);
			final ImageView stage_icon = _view.findViewById(R.id.stage_icon);
			final LinearLayout linear2 = _view.findViewById(R.id.linear2);
			final ImageView diff_icon = _view.findViewById(R.id.diff_icon);
			final ImageView plus_btn = _view.findViewById(R.id.plus_btn);
			final TextView file_name = _view.findViewById(R.id.file_name);
			final TextView file_path = _view.findViewById(R.id.file_path);
			
			file_name.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/enbold.ttf"), 0);
			file_path.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/enlight.ttf"), 0);
			plus_btn.setImageResource(R.drawable.icon_remove_circle_outline_round);
			final HashMap<String, Object> _item = _data.get(_position);
			final String _name = (String) _item.get("name");
			final String _path = (String) _item.get("path");
			final String _relativePath = (String) _item.get("relativePath");
			final String _changeType = (String) _item.get("changeType");
			
			file_name.setText(_name);
			file_path.setText(_relativePath);
			
			String _fileName = _name != null ? _name.toLowerCase() : "";
			
			if (_fileName.endsWith(".html")) {
				stage_icon.setImageResource(R.drawable.html_icon);
			} else if (_fileName.endsWith(".css")) {
				stage_icon.setImageResource(R.drawable.css_icon);
			} else if (_fileName.endsWith(".gradle")) {
				stage_icon.setImageResource(R.drawable.gradle_icon);
			} else if (_fileName.endsWith(".java")) {
				stage_icon.setImageResource(R.drawable.java_icon);
			} else if (_fileName.endsWith(".js")) {
				stage_icon.setImageResource(R.drawable.js_icon);
			} else if (_fileName.endsWith(".json")) {
				stage_icon.setImageResource(R.drawable.json_icon);
			} else if (_fileName.endsWith(".xml")) {
				stage_icon.setImageResource(R.drawable.xml_icon);
			} else if (_fileName.endsWith(".png") || _fileName.endsWith(".jpg") || _fileName.endsWith(".jpeg")) {
				stage_icon.setImageResource(R.drawable.image_icon);
			} else if (_fileName.equalsIgnoreCase(".gitignore") || _fileName.endsWith(".git")) {
				stage_icon.setImageResource(R.drawable.git_icon);
			} else {
				stage_icon.setImageResource(R.drawable.file_icon);
			}
			
			//change_badge.setText("new".equals(_changeType) ? "+" : "modified".equals(_changeType) ? "M" : "−");
			
			plus_btn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					new Thread(new Runnable() {
						@Override
						public void run() {
							gitBridge._unstageFile(_relativePath);
							if (getActivity() != null) {
								getActivity().runOnUiThread(new Runnable() {
									@Override
									public void run() {
										_refreshStatus();
									}
								});
							}
						}
					}).start();
				}
			});
			
			diff_icon.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					DiffDialogFragment _dialog = DiffDialogFragment.newInstance(path, _relativePath);
					_dialog.show(getParentFragmentManager(), "diff_dialog");
				}
			});
			item_cd.setOnClickListener(new View.OnClickListener() {
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
