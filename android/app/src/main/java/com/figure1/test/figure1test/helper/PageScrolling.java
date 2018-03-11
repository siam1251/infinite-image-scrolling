package com.figure1.test.figure1test.helper;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;



public abstract class PageScrolling extends RecyclerView.OnScrollListener {

    private int visibleThreshold;
    // The current offset index
    private int currentPage = 1;
    // The total number of items in the dataset after the last load
    private int previousTotalItemCount = 0;
    // True if we are still waiting for the last set of data to load.
    private boolean loading = true;
    // Sets the starting page index
    private int startingPageIndex = 0;

    private LinearLayoutManager linearLayoutManager;

    public void resetProperties() {

        currentPage = 1;
        previousTotalItemCount = 0;
        loading = true;
    }
    public PageScrolling(LinearLayoutManager layoutManager, int visibleThreshold, int startingPageIndex) {
        this.linearLayoutManager = layoutManager;
        this.visibleThreshold = visibleThreshold;
        this.startingPageIndex = startingPageIndex;
    }


    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        int firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
        int visibleItemCount = view.getChildCount();
        int totalItemCount = linearLayoutManager.getItemCount();


        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.loading = true;
            }
        }

        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
        }

        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            currentPage++;
            onLoadMore(currentPage, totalItemCount);
            loading = true;
        }
    }

    public abstract void onLoadMore(int page, int totalItemsCount);

}
