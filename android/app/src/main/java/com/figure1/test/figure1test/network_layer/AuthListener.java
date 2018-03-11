package com.figure1.test.figure1test.network_layer;

import org.json.JSONObject;

public interface AuthListener {
    void success(JSONObject response);
    void failure(String failureMsg);
}
