package com.figure1.test.figure1test.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GalleryPost {
    @SerializedName("link")
    private String link;

    @SerializedName("title")
    private String title;

    @SerializedName("type")
    private String type;

    @SerializedName("images")
    private ArrayList<ImageResponse> images;


}
