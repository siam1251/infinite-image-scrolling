package com.figure1.test.figure1test.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Gallery {
    @SerializedName("data")
    private ArrayList<GalleryPost> data;

    public ArrayList<GalleryPost> getData() {
        return data;
    }

    public void setData(ArrayList<GalleryPost> data) {
        this.data = data;
    }


}
