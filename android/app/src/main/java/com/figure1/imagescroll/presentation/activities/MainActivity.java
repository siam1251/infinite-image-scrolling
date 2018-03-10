package com.figure1.imagescroll.presentation.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.figure1.imagescroll.model.ImageDataModel;
import com.figure1.imagescroll.network.NetworkManager;
import com.figure1.imagescroll.presentation.adapters.ImageScrollAdapter;
import com.figure1.imagescroll.presentation.listeners.OnDataUpdateListener;
import com.figure1.imagescroll.view_models.DataViewModels;
import com.figure1.test.figure1test.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnDataUpdateListener {
    private DataViewModels viewModel;
    private ArrayList<ImageDataModel> dataModels;
    private RecyclerView recyclerView;
    private ImageScrollAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(DataViewModels.class);
        attachUI();
        setupAdapter();
        viewModel.setDataUpdateListener(this);
        NetworkManager.getInstance().request(viewModel);
    }

    private void setupAdapter() {
        if (recyclerView == null) return;
        adapter = new ImageScrollAdapter(viewModel.getImageList());

        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);


        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void attachUI() {
        recyclerView = findViewById(R.id.deal_list_recycler_view);
    }




    @Override
    public void onDataUpdate() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });

    }
}
