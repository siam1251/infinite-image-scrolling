package com.figure1.imagescroll.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.figure1.imagescroll.network.AuthListener;
import com.figure1.imagescroll.network.NetworkManager;
import com.figure1.test.figure1test.R;
import com.squareup.okhttp.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NetworkManager.getInstance().request(new AuthListener() {
            @Override
            public void success(Response response) {

            }

            @Override
            public void failure(String failureMsg) {

            }
        });
    }
}
