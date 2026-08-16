package org.ide.andriod;

import android.app.Activity;
import java.util.HashMap;

public class StreamingRequestNetwork {

    private HashMap<String, Object> params = new HashMap<>();
    private HashMap<String, Object> headers = new HashMap<>();
    private Activity activity;

    public StreamingRequestNetwork(Activity activity) {
        this.activity = activity;
    }

    public void setHeaders(HashMap<String, Object> headers) {
        this.headers = headers;
    }

    public void setParams(HashMap<String, Object> params) {
        this.params = params;
    }

    public HashMap<String, Object> getParams() {
        return params;
    }

    public HashMap<String, Object> getHeaders() {
        return headers;
    }

    public Activity getActivity() {
        return activity;
    }

    public void startStreaming(String method, String url, String tag, StreamingListener listener) {
        StreamingRequestNetworkController.getInstance()
                .execute(this, method, url, tag, listener);
    }

    public interface StreamingListener {
        void onEvent(String tag, String event, String data);
        void onComplete(String tag);
        void onError(String tag, String message);
    }
}