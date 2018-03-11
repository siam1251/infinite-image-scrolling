package com.figure1.network_layer;

import org.json.JSONObject;

public interface AuthListener {
    void success(JSONObject response);
    void failure(String failureMsg);
}
