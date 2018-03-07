package com.figure1.imagescroll.network;

import com.squareup.okhttp.Response;

public interface AuthListener {
    void success(Response response);
    void failure(String failureMsg);
}
