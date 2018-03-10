package com.figure1.imagescroll.network;

import com.squareup.okhttp.Response;

import org.json.JSONObject;

public interface AuthListener {
    void success(JSONObject response);
    void failure(String failureMsg);
}
