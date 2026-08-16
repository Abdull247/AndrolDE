package org.ide.andriod;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import okhttp3.*;
import okio.BufferedSource;

public class StreamingRequestNetworkController {

    private static StreamingRequestNetworkController instance;
    private OkHttpClient client;

    public static synchronized StreamingRequestNetworkController getInstance() {
        if (instance == null) {
            instance = new StreamingRequestNetworkController();
        }
        return instance;
    }

    private OkHttpClient getClient() {
        if (client == null) {
            client = new OkHttpClient.Builder()
                    .connectTimeout(30000, TimeUnit.MILLISECONDS)
                    .readTimeout(0, TimeUnit.MILLISECONDS)
                    .build();
        }
        return client;
    }

    public void execute(
            final StreamingRequestNetwork request,
            String method,
            String url,
            final String tag,
            final StreamingRequestNetwork.StreamingListener listener
    ) {

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"),
                new Gson().toJson(request.getParams())
        );

        Request.Builder builder = new Request.Builder()
                .url(url)
                .method(method, body);

        for (HashMap.Entry<String, Object> header : request.getHeaders().entrySet()) {
            builder.addHeader(header.getKey(), String.valueOf(header.getValue()));
        }

        Request httpRequest = builder.build();

        getClient().newCall(httpRequest).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, final IOException e) {
                request.getActivity().runOnUiThread(() ->
                        listener.onError(tag, e.getMessage())
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (!response.isSuccessful()) {
                    request.getActivity().runOnUiThread(() ->
                            listener.onError(tag, "HTTP " + response.code())
                    );
                    return;
                }

                BufferedSource source = response.body().source();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(source.inputStream())
                );

                String line;
                String currentEvent = null;
                StringBuilder dataBuilder = new StringBuilder();

                while ((line = reader.readLine()) != null) {

                    if (line.startsWith("event:")) {
                        currentEvent = line.substring(6).trim();
                    } else if (line.startsWith("data:")) {
                        dataBuilder.append(line.substring(5).trim());
                    } else if (line.isEmpty()) {

                        final String eventToSend = currentEvent;
                        final String dataToSend = dataBuilder.toString();

                        if (eventToSend != null && !dataToSend.isEmpty()) {
                            request.getActivity().runOnUiThread(() ->
                                    listener.onEvent(tag, eventToSend, dataToSend)
                            );
                        }

                        dataBuilder.setLength(0);
                    }
                }

                request.getActivity().runOnUiThread(() ->
                        listener.onComplete(tag)
                );
            }
        });
    }
}