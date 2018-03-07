package com.figure1.imagescroll.network;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class NetworkManager {
    private static NetworkManager networkManager;
    private OkHttpClient client;

    public static NetworkManager getInstance() {
        if (networkManager == null) {
            networkManager = new NetworkManager();
        }
        return networkManager;
    }

    private NetworkManager() {
        client = new OkHttpClient();
    }

    public void request(final AuthListener authListener) {
        Request request = new Request.Builder()
                .header("Authorization", "Client-Id e0d9382b96f900b")
                .url("https://api.imgur.com/3/gallery/hot/")
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
                    authListener.success(response);
                }
            }

        });
    }
}
