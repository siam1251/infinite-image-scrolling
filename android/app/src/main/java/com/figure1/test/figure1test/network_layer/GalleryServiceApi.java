package com.figure1.test.figure1test.network_layer;

import com.figure1.test.figure1test.model.GalleryResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GalleryServiceApi {
    @GET("user/viral/{page}")
    Call<GalleryResponse> getGallery(@Path("page") int page);

}
