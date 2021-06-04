package com.demo.assignment.util;


import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import org.jetbrains.annotations.NotNull;

public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    private final int startingPageIndex = 0;
    RecyclerView.LayoutManager mLayoutManager;
    private int visibleThreshold = 5;
    private int currentPage = 0;
    private int previousTotalItemCount = 0;
    private boolean loading = true;

    public EndlessRecyclerViewScrollListener(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            visibleThreshold = visibleThreshold * gridLayoutManager.getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            final StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            visibleThreshold = visibleThreshold * staggeredGridLayoutManager.getSpanCount();
        }

        this.mLayoutManager = layoutManager;


    }

    public int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }

    @Override
    public void onScrolled(@NotNull RecyclerView view, int dx, int dy) {
        int lastVisibleItemPosition = 0;
        int totalItemCount = mLayoutManager.getItemCount();

        if (mLayoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) mLayoutManager).findLastVisibleItemPositions(null);
            // get maximum element within the list
            lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);
        } else if (mLayoutManager instanceof GridLayoutManager) {
            lastVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        } else if (mLayoutManager instanceof LinearLayoutManager) {
            lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        }

        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.loading = true;
            }
        }
        // If it’s still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
        }

        if (!loading && (lastVisibleItemPosition + visibleThreshold) > totalItemCount) {
            currentPage++;
            onLoadMore(currentPage, totalItemCount, view);
            loading = true;
        }
    }

    // Call this method whenever performing new searches
    public void resetState() {
        this.currentPage = this.startingPageIndex;
        this.previousTotalItemCount = 0;
        this.loading = true;
    }

    // Defines the process for actually loading more data based on page
    public abstract void onLoadMore(int page, int totalItemsCount, RecyclerView view);


}


/*  ########################USAGE VIEW #############################
private void handleRecyclerView() {
        WrapContentLinearLayoutManager mLinearLayoutManager = new WrapContentLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapterSearch);
        EndlessRecyclerViewScrollListener onScrollListener = new EndlessRecyclerViewScrollListener(mLinearLayoutManager) {
            Animation buttonIn = AnimationUtils.loadAnimation(filterSortLayout.getContext(), R.anim.button_bottom_in);
            Animation buttonOut = AnimationUtils.loadAnimation(filterSortLayout.getContext(), R.anim.button_bottom_out);

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 2 && filterSortLayout.getVisibility() == View.VISIBLE) {
                    filterSortLayout.startAnimation(buttonIn);
                    filterSortLayout.setVisibility(View.GONE);
                } else if (dy <= 2 && filterSortLayout.getVisibility() != View.VISIBLE) {
                    filterSortLayout.setVisibility(View.VISIBLE);
                    filterSortLayout.startAnimation(buttonOut);
                }
            }

            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                startLimit = endLimit;
                endLimit = endLimit + 20;
                addItemList();
            }

        };
        recyclerView.addOnScrollListener(onScrollListener);

    }


    ###################################################
    When you are going to update the data
     recyclerView.post(new Runnable() {
            public void run() {
                adapterSearch.notifyItemInserted(searchViewModelList.size() - 1);

            }
        });

#############################################3

You can also use pull to refresh in recycler with adding listener

 */