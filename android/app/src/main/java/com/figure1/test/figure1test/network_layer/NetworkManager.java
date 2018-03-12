package com.figure1.test.figure1test.network_layer;

import android.util.Log;

import com.figure1.test.figure1test.model.GalleryResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManager {
    private static String TAG = "NetworkManager";

    private static NetworkManager networkManager;
    static final String BASE_URL = "https://api.imgur.com/3/gallery/";
    static final String clientID = "Client-Id e0d9382b96f900b";

    private static GsonConverterFactory gsonConverterFactory;
    private static OkHttpClient client;
    private static Retrofit retrofit;

    private GalleryServiceApi galleryServiceApi;

    public static NetworkManager getInstance() {
        if (networkManager == null) {
            networkManager = new NetworkManager();
        }
        return networkManager;
    }
    private GsonConverterFactory getGsonConverterFactory() {
        if (gsonConverterFactory == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            gsonConverterFactory = GsonConverterFactory.create(gson);
        }
        return gsonConverterFactory;
    }



    private OkHttpClient getClient() {
        if (client == null) {
            client = new OkHttpClient().
                    newBuilder().
                    addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            Request originalRequest = chain.request();

                            Request.Builder builder = originalRequest.newBuilder().header("Authorization",
                                    clientID);

                            Request newRequest = builder.build();
                            return chain.proceed(newRequest);
                        }
                    }).build();
        }
        return client;
    }


    private Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client(getClient())
                    .baseUrl(BASE_URL)
                    .addConverterFactory(getGsonConverterFactory())
                    .build();
        }
        return retrofit;
    }

    private GalleryServiceApi getGalleryServicApi() {
        if (galleryServiceApi == null) {
            galleryServiceApi = getRetrofit().create(GalleryServiceApi.class);
        }
        return galleryServiceApi;
    }

    public void cancelPreviousRequest() {
        getClient().dispatcher().cancelAll();
    }
    public void requestGalleryImageLoad(final AuthListener authListener, int page) {

        Call<GalleryResponse> call = getGalleryServicApi().getGallery( page);
        call.enqueue(new Callback<GalleryResponse>() {
            @Override
            public void onResponse(Call<GalleryResponse> call, Response<GalleryResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "success");
                    GalleryResponse galleryResponse = response.body();
                    authListener.success(galleryResponse);
                }
            }

            @Override
            public void onFailure(Call<GalleryResponse> call, Throwable t) {
                authListener.failure("auth request failed");
            }
        });

    }
}
