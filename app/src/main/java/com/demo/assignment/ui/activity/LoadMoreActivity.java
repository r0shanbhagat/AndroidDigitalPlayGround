package com.demo.assignment.ui.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.assignment.R;
import com.demo.assignment.core.BaseActivity;
import com.demo.assignment.core.BaseAdapter;
import com.demo.assignment.databinding.ActivityLoadMoreBinding;
import com.demo.assignment.repository.logging.NoInternetException;
import com.demo.assignment.repository.model.HerosModel;
import com.demo.assignment.ui.adapter.LoadMoreAdapter;
import com.demo.assignment.ui.viewmodel.LoadMoreViewModel;
import com.demo.assignment.util.EndlessRecyclerViewScrollListener;
import com.demo.assignment.util.SkeletonScreenView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoadMoreActivity extends BaseActivity<ActivityLoadMoreBinding, LoadMoreViewModel> {

    private LoadMoreAdapter mAdapter;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_load_more;
    }

    @Override
    protected LoadMoreViewModel createViewModel() {
        return new ViewModelProvider(this).get(LoadMoreViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        observeResponse();
        viewModel.fetchHerosList();
    }

    /**
     * Init the Recycler view here
     */
    private void initData() {
        mAdapter = new LoadMoreAdapter(new ArrayList<>());
        binding.rvItemList.setAdapter(mAdapter);
        SkeletonScreenView.show(binding.rvItemList, mAdapter, R.layout.layout_default_item_skeleton);
        setupRecyclerLoadMore();
        mAdapter.setItemClickListener(viewModel -> {
            HerosModel productItemModel = viewModel;

        });

        mAdapter.setOnLoadMoreListener(binding.rvItemList, new BaseAdapter.OnLoadMoreListener() {
            @Override
            protected void onLoadMore() {
                super.onLoadMore();
                Log.i("TAG", "onLoadMore");
            }

            @Override
            protected void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                super.onLoadMore(page, totalItemsCount, view);
                Log.i("TAG", "page" + page + "totalItemsCount" + totalItemsCount);

            }
        });
    }

    private void observeResponse() {
        viewModel.getHerosList().observe(this, dataState -> {
            if (null == dataState) return;
            switch (dataState.getCurrentState()) {
                case 0:
                    ///ShowBaseProgress from common
                    //  LoadingDialog.show(this);
                    break;
                case 1:
                    if (null != mAdapter) {
                        SkeletonScreenView.hide();
                        mAdapter.updateList(dataState.getData());
                    }
                    break;
                case -1:
                    showInfoDialog(dataState.getError());
                    break;
            }
        });
    }

    private void setupRecyclerLoadMore() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvItemList.setLayoutManager(layoutManager);
        binding.rvItemList.setNestedScrollingEnabled(false);
        binding.rvItemList.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            final Animation buttonIn = AnimationUtils.loadAnimation(LoadMoreActivity.this, R.anim.button_bottom_in);
            final Animation buttonOut = AnimationUtils.loadAnimation(LoadMoreActivity.this, R.anim.button_bottom_out);

            @Override
            public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 2 && binding.rlFooterItem.getVisibility() == View.VISIBLE) {
                    binding.rlFooterItem.startAnimation(buttonIn);
                    binding.rlFooterItem.setVisibility(View.GONE);
                } else if (dy <= 2 && binding.rlFooterItem.getVisibility() != View.VISIBLE) {
                    binding.rlFooterItem.setVisibility(View.VISIBLE);
                    binding.rlFooterItem.startAnimation(buttonOut);
                }
            }

            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Log.i("TAG", "Load More");
            }

        });

    }

    /**
     * Showing No Network Dailog
     */
    private void showInfoDialog(Throwable throwable) {
        String msg = getString(R.string.error_msg);
        if (throwable instanceof NoInternetException) {
            msg = getString(R.string.no_internet_msg);
        }
        new AlertDialog
                .Builder(this)
                .setMessage(msg)
                .setTitle("Alert")
                .setPositiveButton("OK", null)
                .create().show();
    }

}