package org.ide.andriod;

import android.animation.*;
import android.app.*;
import android.app.AlertDialog;
import android.content.*;
import android.content.DialogInterface;
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
import android.widget.EditText;
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
import com.google.android.material.button.*;
import com.google.android.material.card.*;
import com.google.android.material.textfield.*;
import com.google.gson.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.json.*;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;


public class GitRemoteFragmentActivity extends Fragment {
	
	private GitBridge gitBridge;
	private String path = "";
	private String name = "";
	
	private LinearLayout linear1;
	private LinearLayout empty_link_acc_holder;
	private LinearLayout container_holder;
	private ImageView imageview1;
	private TextView textview1;
	private TextView textview2;
	private LinearLayout linear2;
	private MaterialButton link_btn;
	private LinearLayout linear3;
	private TextInputLayout textinputlayout1;
	private NestedScrollView nestedScrollView1;
	private LinearLayout linear4;
	private TextView textview3;
	private TextView textview4;
	private LinearLayout linear5;
	private LinearLayout linear6;
	private MaterialCardView materialCardView1;
	private MaterialCardView materialCardView2;
	private LinearLayout linear10;
	private LinearLayout linear7;
	private ImageView imageview2;
	private LinearLayout linear8;
	private ImageView imageview3;
	private TextView textview5;
	private TextView username_txt;
	private LinearLayout linear9;
	private TextInputLayout remote_url_input_layout;
	private TextInputLayout target_branch_input_layout;
	private EditText remote_url_input;
	private EditText target_branch_input;
	private MaterialButton fetch_btn;
	private MaterialButton pull_btn;
	private MaterialButton push_btn;
	
	private com.google.android.material.bottomsheet.BottomSheetDialog bottomSheetl;
	private AlertDialog.Builder dia;
	
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.git_remote_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		linear1 = _view.findViewById(R.id.linear1);
		empty_link_acc_holder = _view.findViewById(R.id.empty_link_acc_holder);
		container_holder = _view.findViewById(R.id.container_holder);
		imageview1 = _view.findViewById(R.id.imageview1);
		textview1 = _view.findViewById(R.id.textview1);
		textview2 = _view.findViewById(R.id.textview2);
		linear2 = _view.findViewById(R.id.linear2);
		link_btn = _view.findViewById(R.id.link_btn);
		linear3 = _view.findViewById(R.id.linear3);
		textinputlayout1 = _view.findViewById(R.id.textinputlayout1);
		nestedScrollView1 = _view.findViewById(R.id.nestedScrollView1);
		linear4 = _view.findViewById(R.id.linear4);
		textview3 = _view.findViewById(R.id.textview3);
		textview4 = _view.findViewById(R.id.textview4);
		linear5 = _view.findViewById(R.id.linear5);
		linear6 = _view.findViewById(R.id.linear6);
		materialCardView1 = _view.findViewById(R.id.materialCardView1);
		materialCardView2 = _view.findViewById(R.id.materialCardView2);
		linear10 = _view.findViewById(R.id.linear10);
		linear7 = _view.findViewById(R.id.linear7);
		imageview2 = _view.findViewById(R.id.imageview2);
		linear8 = _view.findViewById(R.id.linear8);
		imageview3 = _view.findViewById(R.id.imageview3);
		textview5 = _view.findViewById(R.id.textview5);
		username_txt = _view.findViewById(R.id.username_txt);
		linear9 = _view.findViewById(R.id.linear9);
		remote_url_input_layout = _view.findViewById(R.id.remote_url_input_layout);
		target_branch_input_layout = _view.findViewById(R.id.target_branch_input_layout);
		remote_url_input = _view.findViewById(R.id.remote_url_input);
		target_branch_input = _view.findViewById(R.id.target_branch_input);
		fetch_btn = _view.findViewById(R.id.fetch_btn);
		pull_btn = _view.findViewById(R.id.pull_btn);
		push_btn = _view.findViewById(R.id.push_btn);
		dia = new AlertDialog.Builder(getActivity());
		
		link_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				bottomSheetl = new com.google.android.material.bottomsheet.BottomSheetDialog(getActivity());
				View bottomSheetlV;
				bottomSheetlV = getActivity().getLayoutInflater().inflate(R.layout.link_github_acc_sheet,null );
				bottomSheetl.setContentView(bottomSheetlV);
				bottomSheetl.getWindow().findViewById(com.google.android.material.R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
				final LinearLayout main_back = (LinearLayout) bottomSheetlV.findViewById(R.id.main_back);
				final ImageView imageview1 = (ImageView) bottomSheetlV.findViewById(R.id.imageview1);
				final ImageView imageview2 = (ImageView) bottomSheetlV.findViewById(R.id.imageview2);
				final com.google.android.material.textfield.TextInputLayout access_token_input_layout = (com.google.android.material.textfield.TextInputLayout) bottomSheetlV.findViewById(R.id.access_token_input_layout);
				final EditText access_token_input = (EditText) bottomSheetlV.findViewById(R.id.access_token_input);
				final com.google.android.material.textfield.TextInputLayout username_input_layout = (com.google.android.material.textfield.TextInputLayout) bottomSheetlV.findViewById(R.id.username_input_layout);
				final EditText username_input = (EditText) bottomSheetlV.findViewById(R.id.username_input);
				final com.google.android.material.card.MaterialCardView materialCardView2 = (com.google.android.material.card.MaterialCardView) bottomSheetlV.findViewById(R.id.materialCardView2);
				final com.google.android.material.button.MaterialButton link_btn = (com.google.android.material.button.MaterialButton) bottomSheetlV.findViewById(R.id.link_btn);
				final TextView textview1 = (TextView) bottomSheetlV.findViewById(R.id.textview1);
				final TextView textview2 = (TextView) bottomSheetlV.findViewById(R.id.textview2);
				final TextView textview3 = (TextView) bottomSheetlV.findViewById(R.id.textview3);
				main_back.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)30, getResources().getColor(R.color.md_theme_onSecondary)));
				imageview1.setColorFilter(getResources().getColor(R.color.md_theme_onSurfaceVariant), PorterDuff.Mode.MULTIPLY);
				imageview2.setColorFilter(getResources().getColor(R.color.md_theme_surfaceTint), PorterDuff.Mode.MULTIPLY);
				textview1.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/enbold.ttf"), 1);
				textview2.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/enlight.ttf"), 0);
				textview3.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/enbold.ttf"), 0);
				materialCardView2.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						
					}
				});
				link_btn.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View _view) {
						
						username_input_layout.setError(null);
						access_token_input_layout.setError(null);
						
						final String _username = username_input.getText().toString().trim();
						final String _token = access_token_input.getText().toString().trim();
						
						if (_username.isEmpty()) {
							username_input_layout.setError("Please enter your GitHub username");
							return;
						}
						
						if (_token.isEmpty()) {
							access_token_input_layout.setError("Please enter your access token");
							return;
						}
						
						link_btn.setEnabled(false);
						link_btn.setText("Validating...");
						
						new Thread(new Runnable() {
							@Override
							public void run() {
								boolean _valid = false;
								String _returnedLogin = "";
								
								try {
									java.net.URL _url = new java.net.URL("https://api.github.com/user");
									java.net.HttpURLConnection _conn = (java.net.HttpURLConnection) _url.openConnection();
									_conn.setRequestMethod("GET");
									_conn.setRequestProperty("Authorization", "Bearer " + _token);
									_conn.setRequestProperty("Accept", "application/vnd.github+json");
									_conn.setConnectTimeout(10000);
									_conn.setReadTimeout(10000);
									
									int _responseCode = _conn.getResponseCode();
									
									if (_responseCode == 200) {
										java.io.InputStream _in = _conn.getInputStream();
										java.io.ByteArrayOutputStream _bos = new java.io.ByteArrayOutputStream();
										byte[] _buffer = new byte[1024];
										int _read;
										while ((_read = _in.read(_buffer)) != -1) {
											_bos.write(_buffer, 0, _read);
										}
										_in.close();
										String _responseBody = _bos.toString("UTF-8");
										
										org.json.JSONObject _json = new org.json.JSONObject(_responseBody);
										_returnedLogin = _json.optString("login", "");
										
										if (_returnedLogin.equalsIgnoreCase(_username)) {
											_valid = true;
										}
									}
									
									_conn.disconnect();
									
								} catch (Exception e) {
									e.printStackTrace();
								}
								
								final boolean _finalValid = _valid;
								final String _finalReturnedLogin = _returnedLogin;
								
								if (getActivity() == null) return;
								getActivity().runOnUiThread(new Runnable() {
									@Override
									public void run() {
										link_btn.setEnabled(true);
										link_btn.setText("Link Account");
										
										if (_finalValid) {
											_saveGitHubCredentials(_username, _token);
											_checkGitHubLinkStatus();
											bottomSheetl.dismiss();
										} else if (!_finalReturnedLogin.isEmpty()) {
											access_token_input_layout.setError("Token doesn't match username \"" + _finalReturnedLogin + "\"");
										} else {
											access_token_input_layout.setError("Invalid username or access token");
										}
									}
								});
							}
						}).start();
					}
				});
				bottomSheetl.show();
			}
		});
		
		imageview3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				MaterialAlertDialogBuilder dia = new MaterialAlertDialogBuilder(getContext());
				dia.setTitle("Logout?");
				dia.setMessage("This would fully delete/clear your existing GitHub stored details, proceed?");
				dia.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						_logoutGitHub();
					}
				});
				dia.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						
					}
				});
				dia.create().show();
			}
		});
	}
	
	private void initializeLogic() {
		path = getArguments() != null ? getArguments().getString("path") : "";
		name = getArguments() != null ? getArguments().getString("name") : "";
		gitBridge = ((EditorActivity) getActivity()).getGitBridge();
		_checkGitHubLinkStatus();
		textview1.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/enbold.ttf"), 0);
		textview2.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/enlight.ttf"), 0);
		link_btn.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/enbold.ttf"), 0);
		imageview1.setColorFilter(getResources().getColor(R.color.md_theme_onSurfaceVariant), PorterDuff.Mode.MULTIPLY);
		remote_url_input.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence _s, int _start, int _count, int _after) {}
			
			@Override
			public void onTextChanged(CharSequence _s, int _start, int _before, int _count) {}
			
			@Override
			public void afterTextChanged(Editable _s) {
				_saveGitRemoteConfig();
			}
		});
		
		target_branch_input.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence _s, int _start, int _count, int _after) {}
			
			@Override
			public void onTextChanged(CharSequence _s, int _start, int _before, int _count) {}
			
			@Override
			public void afterTextChanged(Editable _s) {
				_saveGitRemoteConfig();
			}
		});
		new Thread(new Runnable() {
			@Override
			public void run() {
				final HashMap<String, String> _remoteConfig = _readGitRemoteConfig();
				
				if (getActivity() == null) return;
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (_remoteConfig != null) {
							String _url = _remoteConfig.get("remote_url");
							String _branch = _remoteConfig.get("branch_name");
							
							if (_url != null && !_url.trim().isEmpty()) {
								remote_url_input.setText(_url);
							}
							if (_branch != null && !_branch.trim().isEmpty()) {
								target_branch_input.setText(_branch);
							}
						}
					}
				});
			}
		}).start();
		fetch_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				remote_url_input_layout.setError(null);
				
				final HashMap<String, String> _remoteConfig = _readGitRemoteConfig();
				final String _remoteUrl = _remoteConfig != null ? _remoteConfig.get("remote_url") : "";
				
				if (_remoteUrl == null || _remoteUrl.trim().isEmpty()) {
					remote_url_input_layout.setError("Please enter a remote repository URL");
					return;
				}
				
				final HashMap<String, String> _creds = _getGitHubCredentials();
				if (_creds == null) {
					com.google.android.material.snackbar.Snackbar.make(linear1, "Link your GitHub account first", com.google.android.material.snackbar.Snackbar.LENGTH_SHORT)
					.setAction("OK", new View.OnClickListener() { @Override public void onClick(View _v) {} }).show();
					return;
				}
				
				fetch_btn.setEnabled(false);
				fetch_btn.setText("Fetching...");
				
				new Thread(new Runnable() {
					@Override
					public void run() {
						gitBridge._setRemote(_remoteUrl);
						final int _result = gitBridge._fetch(_creds.get("username"), _creds.get("token"));
						
						if (getActivity() == null) return;
						getActivity().runOnUiThread(new Runnable() {
							@Override
							public void run() {
								fetch_btn.setEnabled(true);
								fetch_btn.setText("Fetch");
								
								if (_result == GitBridge.RESULT_SUCCESS) {
									com.google.android.material.snackbar.Snackbar.make(linear1, "Fetch successful", com.google.android.material.snackbar.Snackbar.LENGTH_SHORT)
									.setAction("OK", new View.OnClickListener() { @Override public void onClick(View _v) {} }).show();
								} else {
									remote_url_input_layout.setError("Fetch failed — check the URL and try again");
								}
							}
						});
					}
				}).start();
			}
		});
		
		pull_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				remote_url_input_layout.setError(null);
				target_branch_input_layout.setError(null);
				
				final HashMap<String, String> _remoteConfig = _readGitRemoteConfig();
				final String _remoteUrl = _remoteConfig != null ? _remoteConfig.get("remote_url") : "";
				String _branchRaw = _remoteConfig != null ? _remoteConfig.get("branch_name") : "";
				final String _branch = (_branchRaw == null || _branchRaw.trim().isEmpty()) ? "main" : _branchRaw.trim();
				
				if (_remoteUrl == null || _remoteUrl.trim().isEmpty()) {
					remote_url_input_layout.setError("Please enter a remote repository URL");
					return;
				}
				
				final HashMap<String, String> _creds = _getGitHubCredentials();
				if (_creds == null) {
					com.google.android.material.snackbar.Snackbar.make(linear1, "Link your GitHub account first", com.google.android.material.snackbar.Snackbar.LENGTH_SHORT)
					.setAction("OK", new View.OnClickListener() { @Override public void onClick(View _v) {} }).show();
					return;
				}
				
				pull_btn.setEnabled(false);
				pull_btn.setText("Pulling...");
				
				new Thread(new Runnable() {
					@Override
					public void run() {
						gitBridge._setRemote(_remoteUrl);
						final int _result = gitBridge._pull(_creds.get("username"), _creds.get("token"), _branch);
						
						if (getActivity() == null) return;
						getActivity().runOnUiThread(new Runnable() {
							@Override
							public void run() {
								pull_btn.setEnabled(true);
								pull_btn.setText("Pull");
								
								if (_result == GitBridge.RESULT_SUCCESS) {
									com.google.android.material.snackbar.Snackbar.make(linear1, "Pull successful", com.google.android.material.snackbar.Snackbar.LENGTH_SHORT)
									.setAction("OK", new View.OnClickListener() { @Override public void onClick(View _v) {} }).show();
								} else {
									target_branch_input_layout.setError("Pull failed — check branch name and try again");
								}
							}
						});
					}
				}).start();
			}
		});
		
		push_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				remote_url_input_layout.setError(null);
				target_branch_input_layout.setError(null);
				
				final HashMap<String, String> _remoteConfig = _readGitRemoteConfig();
				final String _remoteUrl = _remoteConfig != null ? _remoteConfig.get("remote_url") : "";
				String _branchRaw = _remoteConfig != null ? _remoteConfig.get("branch_name") : "";
				final String _branch = (_branchRaw == null || _branchRaw.trim().isEmpty()) ? "main" : _branchRaw.trim();
				
				if (_remoteUrl == null || _remoteUrl.trim().isEmpty()) {
					remote_url_input_layout.setError("Please enter a remote repository URL");
					return;
				}
				
				final HashMap<String, String> _creds = _getGitHubCredentials();
				if (_creds == null) {
					com.google.android.material.snackbar.Snackbar.make(linear1, "Link your GitHub account first", com.google.android.material.snackbar.Snackbar.LENGTH_SHORT)
					.setAction("OK", new View.OnClickListener() { @Override public void onClick(View _v) {} }).show();
					return;
				}
				
				push_btn.setEnabled(false);
				push_btn.setText("Pushing...");
				
				new Thread(new Runnable() {
					@Override
					public void run() {
						gitBridge._setRemote(_remoteUrl);
						final int _result = gitBridge._push(_creds.get("username"), _creds.get("token"), _branch);
						
						if (getActivity() == null) return;
						getActivity().runOnUiThread(new Runnable() {
							@Override
							public void run() {
								push_btn.setEnabled(true);
								push_btn.setText("Push");
								
								if (_result == GitBridge.RESULT_SUCCESS) {
									com.google.android.material.snackbar.Snackbar.make(linear1, "Push successful", com.google.android.material.snackbar.Snackbar.LENGTH_SHORT)
									.setAction("OK", new View.OnClickListener() { @Override public void onClick(View _v) {} }).show();
								} else {
									target_branch_input_layout.setError("Push failed — check credentials and branch");
								}
							}
						});
					}
				}).start();
			}
		});
	}
	
	@Override
	public void onResume() {
		super.onResume();
		_checkGitHubLinkStatus();
	}
	

	public void _anchore() {
		
	}
    
    private void _saveGitHubCredentials(String _username, String _token) {
	String _dir = "/storage/emulated/0/.androIDE/system/GitHub/";
	new java.io.File(_dir).mkdirs();

	HashMap<String, Object> _creds = new HashMap<>();
	_creds.put("username", _username);
	_creds.put("token", _token);
	_creds.put("timestamp", System.currentTimeMillis());

	FileUtil.writeFile(_dir.concat("credentials"), new Gson().toJson(_creds));
}

private HashMap<String, String> _getGitHubCredentials() {
	try {
		String _credsPath = "/storage/emulated/0/.androIDE/system/GitHub/credentials";
		java.io.File _credsFile = new java.io.File(_credsPath);
		if (!_credsFile.exists() || _credsFile.length() == 0) return null;

		String _content = FileUtil.readFile(_credsPath);
		if (_content == null || _content.trim().isEmpty()) return null;

		org.json.JSONObject _json = new org.json.JSONObject(_content);
		String _u = _json.optString("username", "");
		String _t = _json.optString("token", "");
		if (_u.trim().isEmpty() || _t.trim().isEmpty()) return null;

		HashMap<String, String> _map = new HashMap<>();
		_map.put("username", _u);
		_map.put("token", _t);
		return _map;
	} catch (Exception e) {
		e.printStackTrace();
		return null;
	}
}

private void _saveGitRemoteConfig() {
	new Thread(new Runnable() {
		@Override
		public void run() {
			try {
				String _dataDir = "/storage/emulated/0/.androIDE/data/".concat(name).concat("/");
				new java.io.File(_dataDir).mkdirs();

				HashMap<String, String> _map = new HashMap<>();
				_map.put("remote_url", remote_url_input.getText().toString().trim());
				_map.put("branch_name", target_branch_input.getText().toString().trim());

				FileUtil.writeFile(_dataDir.concat("github_remote.json"), new Gson().toJson(_map));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}).start();
}

private HashMap<String, String> _readGitRemoteConfig() {
	try {
		String _path = "/storage/emulated/0/.androIDE/data/".concat(name).concat("/github_remote.json");
		java.io.File _file = new java.io.File(_path);
		if (!_file.exists()) return null;

		String _content = FileUtil.readFile(_path);
		if (_content == null || _content.trim().isEmpty()) return null;

		java.lang.reflect.Type _type = new com.google.gson.reflect.TypeToken<HashMap<String, String>>(){}.getType();
		return new Gson().fromJson(_content, _type);
	} catch (Exception e) {
		e.printStackTrace();
		return null;
	}
}

public void _logoutGitHub() {
	new Thread(new Runnable() {
		@Override
		public void run() {
			try {
				String _credsPath = "/storage/emulated/0/.androIDE/system/GitHub/credentials";
				java.io.File _credsFile = new java.io.File(_credsPath);
				if (_credsFile.exists()) {
					_credsFile.delete();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (getActivity() == null) return;
			getActivity().runOnUiThread(new Runnable() {
				@Override
				public void run() {
					_checkGitHubLinkStatus();
				}
			});
		}
	}).start();
}
	
	
	public void _checkGitHubLinkStatus() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				final boolean[] _isLinked = {false};
				final String[] _username = {""};
				
				try {
					String _credsPath = "/storage/emulated/0/.androIDE/system/GitHub/credentials";
					java.io.File _credsFile = new java.io.File(_credsPath);
					
					if (_credsFile.exists() && _credsFile.length() > 0) {
						String _content = FileUtil.readFile(_credsPath);
						
						if (_content != null && !_content.trim().isEmpty()) {
							org.json.JSONObject _json = new org.json.JSONObject(_content);
							
							String _u = _json.optString("username", "");
							String _t = _json.optString("token", "");
							
							if (!_u.trim().isEmpty() && !_t.trim().isEmpty()) {
								_isLinked[0] = true;
								_username[0] = _u;
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					_isLinked[0] = false;
				}
				
				if (getActivity() == null) return;
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (_isLinked[0]) {
							empty_link_acc_holder.setVisibility(View.GONE);
							container_holder.setVisibility(View.VISIBLE);
							username_txt.setText(_username[0]);
						} else {
							empty_link_acc_holder.setVisibility(View.VISIBLE);
							container_holder.setVisibility(View.GONE);
						}
					}
				});
			}
		}).start();
	}
	
}
