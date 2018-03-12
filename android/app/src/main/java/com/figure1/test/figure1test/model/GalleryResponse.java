package com.figure1.test.figure1test.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GalleryResponse {
    @SerializedName("data")
    private ArrayList<GalleryPostResponse> data;

    public ArrayList<GalleryPostResponse> getData() {
        return data;
    }



}
