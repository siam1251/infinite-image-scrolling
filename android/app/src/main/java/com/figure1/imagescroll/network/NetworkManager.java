package com.figure1.imagescroll.network;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import org.apache.http.HttpEntity;
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

    private static String convertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
