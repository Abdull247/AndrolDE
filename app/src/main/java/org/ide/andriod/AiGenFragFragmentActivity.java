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
import android.widget.EditText;
import android.widget.ImageView;
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
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.MediaType;
import okhttp3.Callback;
import okhttp3.Call;
import okhttp3.Response;

public class AiGenFragFragmentActivity extends Fragment {
	
	private Recyclerview1Adapter chatAdapter;
	
	private ArrayList<HashMap<String, Object>> chats_list = new ArrayList<>();
	
	private LinearLayout back_main;
	private RelativeLayout relativelayout1;
	private LinearLayout items_conc;
	private LinearLayout bottom_conc;
	private RecyclerView recyclerview1;
	private MaterialCardView materialCardView1;
	private MaterialCardView materialCardView2;
	private LinearLayout linear2;
	private ImageView imageview1;
	private LinearLayout linear3;
	private EditText msg_input;
	private MaterialCardView send_cd;
	private MaterialCardView more_cd;
	private LinearLayout linear4;
	private ImageView input_action_img;
	private LinearLayout linear5;
	private ImageView imageview3;
	
	private RequestNetwork net;
	private RequestNetwork.RequestListener _net_request_listener;
	
	@NonNull
	@Override
	public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
		View _view = _inflater.inflate(R.layout.ai_gen_frag_fragment, _container, false);
		initialize(_savedInstanceState, _view);
		initializeLogic();
		return _view;
	}
	
	private void initialize(Bundle _savedInstanceState, View _view) {
		back_main = _view.findViewById(R.id.back_main);
		relativelayout1 = _view.findViewById(R.id.relativelayout1);
		items_conc = _view.findViewById(R.id.items_conc);
		bottom_conc = _view.findViewById(R.id.bottom_conc);
		recyclerview1 = _view.findViewById(R.id.recyclerview1);
		materialCardView1 = _view.findViewById(R.id.materialCardView1);
		materialCardView2 = _view.findViewById(R.id.materialCardView2);
		linear2 = _view.findViewById(R.id.linear2);
		imageview1 = _view.findViewById(R.id.imageview1);
		linear3 = _view.findViewById(R.id.linear3);
		msg_input = _view.findViewById(R.id.msg_input);
		send_cd = _view.findViewById(R.id.send_cd);
		more_cd = _view.findViewById(R.id.more_cd);
		linear4 = _view.findViewById(R.id.linear4);
		input_action_img = _view.findViewById(R.id.input_action_img);
		linear5 = _view.findViewById(R.id.linear5);
		imageview3 = _view.findViewById(R.id.imageview3);
		net = new RequestNetwork((Activity) getContext());
		
		materialCardView1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		send_cd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		more_cd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		_net_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
	}
	
	private void initializeLogic() {
		msg_input.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/ooo.ttf"), 0);
		send_cd.setVisibility(View.GONE);
		send_cd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
				String messageText = msg_input.getText().toString().trim();
				
				if (!messageText.isEmpty()) {
					sendChatMessage(messageText);
					msg_input.setText("");
				}
			}
		});
		chatAdapter = new Recyclerview1Adapter(chats_list);
		recyclerview1.setLayoutManager(new LinearLayoutManager(getContext()));
		recyclerview1.setAdapter(chatAdapter);
		msg_input.addTextChangedListener(new TextWatcher() {
			private boolean showingSend = false; // track current state
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				boolean hasText = s.toString().trim().length() > 0;
				
				if (hasText && !showingSend) {
					showingSend = true;
					
					// Hide more_cd with pop-out
					more_cd.animate()
					.scaleX(0f)
					.scaleY(0f)
					.setDuration(150)
					.withEndAction(() -> {
						more_cd.setVisibility(View.GONE);
						// Show send_cd with slide-in
						send_cd.setVisibility(View.VISIBLE);
						send_cd.setTranslationX(100f);
						send_cd.animate()
						.translationX(0f)
						.setDuration(200)
						.start();
					}).start();
					
				} else if (!hasText && showingSend) {
					showingSend = false;
					
					// Hide send_cd with slide-out
					send_cd.animate()
					.translationX(100f)
					.setDuration(150)
					.withEndAction(() -> {
						send_cd.setVisibility(View.GONE);
						// Show more_cd with pop-in
						more_cd.setVisibility(View.VISIBLE);
						more_cd.setScaleX(0f);
						more_cd.setScaleY(0f);
						more_cd.animate()
						.scaleX(1f)
						.scaleY(1f)
						.setDuration(200)
						.start();
					}).start();
				}
			}
			
			@Override
			public void afterTextChanged(Editable s) { }
		});
	}
	
	public void _anchore() {
		
	}
    
    private void sendChatMessage(String messageText) {

    HashMap<String, Object> userItem = new HashMap<>();
    userItem.put("role", "user");
    userItem.put("message", messageText);
    userItem.put("processing", false);
    userItem.put("progress", "");

    chats_list.add(userItem);
    chatAdapter.notifyItemInserted(chats_list.size() - 1);

    HashMap<String, Object> aiItem = new HashMap<>();
    aiItem.put("role", "ai");
    aiItem.put("message", "");
    aiItem.put("processing", true);
    aiItem.put("progress", "Generating...");

    chats_list.add(aiItem);
    chatAdapter.notifyItemInserted(chats_list.size() - 1);

    int aiPosition = chats_list.size() - 1;

    recyclerview1.scrollToPosition(aiPosition);

    startNormalRequest(messageText, aiPosition);
}

private void startNormalRequest(String messageText, int aiPosition) {

    try {

        // ✅ Increased timeouts for AI responses
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                .readTimeout(120, java.util.concurrent.TimeUnit.SECONDS)
                .writeTimeout(120, java.util.concurrent.TimeUnit.SECONDS)
                .build();

        JSONObject json = new JSONObject();
        json.put("mode", "chat");
        json.put("session_id", "session_001");
        json.put("message", messageText);
        json.put("stream", false);

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"),
                json.toString()
        );

        Request request = new Request.Builder()
                .url("https://error404-gpts.vercel.app/api/ai/chat")
                .post(body)
                .addHeader("Content-Type", "application/json")
                //contact me +2347063349393 for agentic ai API key
                .addHeader("x-api-key", "YOUR_API_KEY")
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

                Log.e("AI_NETWORK_ERROR", e.toString());

                if (getActivity() == null) return;

                getActivity().runOnUiThread(() -> {

                    chats_list.get(aiPosition).put("processing", false);
                    chats_list.get(aiPosition).put("progress", "Network Error");

                    chatAdapter.notifyItemChanged(aiPosition);
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String responseBody = response.body().string();

                Log.d("AI_RAW_RESPONSE", responseBody);

                if (getActivity() == null) return;

                getActivity().runOnUiThread(() -> {

                    try {

                        JSONObject obj = new JSONObject(responseBody);

                        if (obj.has("type") &&
                                obj.getString("type").equals("structured_document")) {

                            JSONArray contentArray = obj.getJSONArray("content");

                            chats_list.get(aiPosition)
                                    .put("message", contentArray.toString());

                            chats_list.get(aiPosition).put("processing", false);
                            chats_list.get(aiPosition).put("progress", "");

                            chatAdapter.notifyItemChanged(aiPosition);
                            recyclerview1.scrollToPosition(aiPosition);

                            logLongResponse("AI_STRUCTURED_RESPONSE",
                                    contentArray.toString());
                        } else {

                            // ✅ Fallback if backend returns unexpected JSON
                            chats_list.get(aiPosition)
                                    .put("message", responseBody);

                            chats_list.get(aiPosition).put("processing", false);
                            chats_list.get(aiPosition).put("progress", "");

                            chatAdapter.notifyItemChanged(aiPosition);
                        }

                    } catch (Exception e) {

                        e.printStackTrace();

                        chats_list.get(aiPosition).put("processing", false);
                        chats_list.get(aiPosition).put("progress", "Parsing Error");

                        chatAdapter.notifyItemChanged(aiPosition);
                    }
                });
            }
        });

    } catch (Exception e) {
        e.printStackTrace();
    }
}

private void logLongResponse(String tag, String message) {

    if (message == null) return;

    int maxLogSize = 2000; // Logcat limit safe size
    for (int i = 0; i <= message.length() / maxLogSize; i++) {

        int start = i * maxLogSize;
        int end = Math.min((i + 1) * maxLogSize, message.length());

        Log.d(tag, message.substring(start, end));
    }
}
	
	public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
		
		ArrayList<HashMap<String, Object>> _data;
		
		public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = getActivity().getLayoutInflater();
			View _v = _inflater.inflate(R.layout.ai_chat_item, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout user_response_holder = _view.findViewById(R.id.user_response_holder);
			final LinearLayout ai_response_holder = _view.findViewById(R.id.ai_response_holder);
			final com.google.android.material.card.MaterialCardView msg_card = _view.findViewById(R.id.msg_card);
			final LinearLayout linear3 = _view.findViewById(R.id.linear3);
			final TextView textview1 = _view.findViewById(R.id.textview1);
			final com.google.android.material.card.MaterialCardView gen_process_cd = _view.findViewById(R.id.gen_process_cd);
			final LinearLayout ai_content_container = _view.findViewById(R.id.ai_content_container);
			final LinearLayout linear5 = _view.findViewById(R.id.linear5);
			final LinearLayout linear4 = _view.findViewById(R.id.linear4);
			final com.airbnb.lottie.LottieAnimationView lottie1 = _view.findViewById(R.id.lottie1);
			final TextView ai_progress_text = _view.findViewById(R.id.ai_progress_text);
			final com.google.android.material.card.MaterialCardView copy_cd = _view.findViewById(R.id.copy_cd);
			final com.google.android.material.card.MaterialCardView share_cd = _view.findViewById(R.id.share_cd);
			final LinearLayout model_data_holder = _view.findViewById(R.id.model_data_holder);
			final LinearLayout linear6 = _view.findViewById(R.id.linear6);
			final ImageView imageview1 = _view.findViewById(R.id.imageview1);
			final LinearLayout linear7 = _view.findViewById(R.id.linear7);
			final ImageView imageview2 = _view.findViewById(R.id.imageview2);
			final ImageView imageview3 = _view.findViewById(R.id.imageview3);
			final TextView textview3 = _view.findViewById(R.id.textview3);
			
			textview1.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"fonts/enlight.ttf"), 0);
			
			String role = String.valueOf(_data.get(_position).get("role"));
			String message = String.valueOf(_data.get(_position).get("message"));
			
			Object processingObj = _data.get(_position).get("processing");
			boolean processing = processingObj != null && (Boolean) processingObj;
			
			String progress = "";
			if (_data.get(_position).get("progress") != null) {
				progress = String.valueOf(_data.get(_position).get("progress"));
			}
			
			if (role.equals("user")) {
				
				user_response_holder.setVisibility(View.VISIBLE);
				ai_response_holder.setVisibility(View.GONE);
				textview1.setText(message);
				
			} else {
				
				user_response_holder.setVisibility(View.GONE);
				ai_response_holder.setVisibility(View.VISIBLE);
				
				// ✅ Render structured content
				MarkdownRenderer.renderStructured(
				getContext(),
				message,
				ai_content_container
				);
				
				if (processing || !progress.isEmpty()) {
					gen_process_cd.setVisibility(View.VISIBLE);
					ai_progress_text.setText(progress);
				} else {
					gen_process_cd.setVisibility(View.GONE);
				}
			}
			
			// ✅ Long press to copy
			final String finalMessage = message;
			
			ai_response_holder.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					
					android.content.ClipboardManager clipboard =
					(android.content.ClipboardManager)
					getContext().getSystemService(Context.CLIPBOARD_SERVICE);
					
					android.content.ClipData clip =
					android.content.ClipData.newPlainText("AI Response", finalMessage);
					
					clipboard.setPrimaryClip(clip);
					
					Toast.makeText(getContext(), "Copied", Toast.LENGTH_SHORT).show();
					return true;
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