package com.figure1.test.figure1test.network_layer;

import org.json.JSONObject;

import retrofit2.Response;

public interface AuthListener {
    void success(Object response);
    void failure(String failureMsg);
}
