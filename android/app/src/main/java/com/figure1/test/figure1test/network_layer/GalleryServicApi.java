package com.figure1.test.figure1test.network_layer;

import com.figure1.test.figure1test.model.Gallery;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface GalleryServicApi {
    @GET("gallery/hot/viral/{page}")
    Call<Gallery> getGallery(@Path("page") int page);

}
