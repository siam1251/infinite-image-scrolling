package com.figure1.test.figure1test.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GalleryPostResponse {
    @SerializedName("link")
    private String link;

    @SerializedName("title")
    private String title;

    @SerializedName("type")
    private String type;

    @SerializedName("images")
    private List<ImageResponse> images;

    public String getLink() {
        return link != null ? link: "";
    }

    public String getTitle() {
        return title != null ? title: "";
    }

    public String getType() {
        return type != null ? type: "";
    }

    public List<ImageResponse> getImages() {
        return images != null ? images: new ArrayList<ImageResponse>();
    }




}
