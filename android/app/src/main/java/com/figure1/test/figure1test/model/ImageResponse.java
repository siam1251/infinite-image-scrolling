package com.figure1.test.figure1test.model;

import com.google.gson.annotations.SerializedName;

public class ImageResponse {
    @SerializedName("link")
    private String link;

    @SerializedName("title")
    private String title;

    @SerializedName("type")
    private String type;

    public String getLink() {
        return link != null ? link:"";
    }

    public String getTitle() {
        return title != null ? title:"" ;
    }

    public String getType() {
        return type != null ? type:"";
    }



}
