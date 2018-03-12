package com.figure1.test.figure1test.presentation.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.figure1.test.figure1test.helper.PageScrolling;
import com.figure1.test.figure1test.model.ImageDataModel;
import com.figure1.test.figure1test.network_layer.NetworkManager;
import com.figure1.test.figure1test.presentation.adapters.ImageScrollAdapter;
import com.figure1.test.figure1test.presentation.listeners.OnDataUpdateListener;
import com.figure1.test.figure1test.presentation.presenters.DataViewModels;
import com.figure1.test.figure1test.R;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnDataUpdateListener, SwipeRefreshLayout.OnRefreshListener {
    private DataViewModels viewModel;
    private ArrayList<ImageDataModel> dataModels;
    private RecyclerView recyclerView;
    private ImageScrollAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private PageScrolling scrollListener;
    private int scrollingVisibleThreshold = 5;
    private int startingIndexPage = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(DataViewModels.class);
        attachUI();
        setupAdapter();
        viewModel.setDataUpdateListener(this);
        NetworkManager.getInstance().request(viewModel, startingIndexPage);
    }

    private void setupAdapter() {
        if (recyclerView == null) return;
        adapter = new ImageScrollAdapter(viewModel.getImageList());
        recyclerView.setAdapter(adapter);
    }

    private void attachUI() {
        swipeRefreshLayout = findViewById(R.id.image_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);



        recyclerView = findViewById(R.id.image_list_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        scrollListener = new PageScrolling(layoutManager, scrollingVisibleThreshold, startingIndexPage+1) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                NetworkManager.getInstance().request(viewModel, page);
            }
        };
        recyclerView.addOnScrollListener(scrollListener);
    }




    @Override
    public void onDataUpdate(final int insertionIndex, final int count) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                adapter.notifyItemRangeInserted(insertionIndex, count);
                //adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onRefresh() {
        NetworkManager.getInstance().cancelPreviousRequest();
        scrollListener.resetProperties();
        swipeRefreshLayout.setRefreshing(true);
        viewModel.getImageList().clear();
        adapter.notifyDataSetChanged();
        NetworkManager.getInstance().request(viewModel, startingIndexPage);
    }
}
