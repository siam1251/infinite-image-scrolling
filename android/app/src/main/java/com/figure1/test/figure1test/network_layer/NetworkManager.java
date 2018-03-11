package com.figure1.test.figure1test.network_layer;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class NetworkManager {
    private static NetworkManager networkManager;
    private OkHttpClient client;
    private final String IMAGE_QUERY_TAG = "IMAGE_QUERY_TAG";

    public static NetworkManager getInstance() {
        if (networkManager == null) {
            networkManager = new NetworkManager();
        }
        return networkManager;
    }

    private NetworkManager() {
        client = new OkHttpClient();
    }

    public void cancelPreviousRequest() {
        client.getDispatcher().cancel(IMAGE_QUERY_TAG);
    }

    public void request(final AuthListener authListener, int page) {

        Request request = new Request.Builder()
                .tag(IMAGE_QUERY_TAG)
                .header("Authorization", "Client-Id e0d9382b96f900b")
                .url("https://api.imgur.com/3/gallery/user/viral/"+String.valueOf(page))
                .build();



        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                authListener.failure("failed");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) {
                    authListener.failure("failed: response is unsuccessful");
                } else {
                    // do something wih the result
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        authListener.success(object);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return;
                    }

                }
            }

        });
    }
}
