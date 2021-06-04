package com.demo.assignment.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.assignment.R;
import com.demo.assignment.core.BaseActivity;
import com.demo.assignment.databinding.ActivityLoadMoreBinding;
import com.demo.assignment.repository.model.MoviesModel;
import com.demo.assignment.ui.adapter.LoadMoreAdapter;
import com.demo.assignment.ui.callback.IItemClick;
import com.demo.assignment.ui.viewmodel.LoadMoreViewModel;
import com.demo.assignment.util.AppUtils;
import com.demo.assignment.util.RecyclerPagingListener;
import com.demo.assignment.util.SkeletonScreenView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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
        init();
        observeResponse();
        viewModel.fetchMoviesList(this);
    }

    /**
     * Init the Data here
     */
    private void init() {
        mAdapter = new LoadMoreAdapter(new ArrayList<>());
        binding.rvItemList.setItemAnimator(new DefaultItemAnimator());
        binding.rvItemList.setAdapter(mAdapter);
        //Show SkeletonScreenView
        SkeletonScreenView.show(binding.rvItemList, mAdapter, R.layout.layout_default_item_skeleton);
        setupLoadMore();
        setClickListener();
    }

    private void observeResponse() {
        viewModel.getHerosList().observe(this, dataState -> {
            if (null == dataState) return;
            switch (dataState.getCurrentState()) {
                case 0:
                    viewModel.setLoadingStatus(true);
                    break;
                case 1:
                    handleSuccess(dataState.getData().getMoviesModels());
                    break;
                case -1:
                    mAdapter.showRetry(true, viewModel.fetchErrorMessage(this, dataState.getError()));
                    break;
            }
        });
    }

    /**
     * setClickListener
     */
    private void setClickListener() {
        mAdapter.setItemClickListener(new IItemClick<MoviesModel>() {
            @Override
            public void onItemClick(MoviesModel item) {
                //TODO Handle ITEM click
            }

            @Override
            public void retryPageLoad() {
                viewModel.fetchMoviesList(LoadMoreActivity.this);
            }
        });
    }

    /**
     * @param moviesList:List
     */
    private void handleSuccess(List<MoviesModel> moviesList) {
        SkeletonScreenView.hide();
        mAdapter.removeLoadingFooter();
        viewModel.updatePageCount();
        mAdapter.updateList(moviesList);
        if (!viewModel.isLastPage()) {
            mAdapter.addLoadingFooter();
        }
    }


    private void setupLoadMore() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.rvItemList.setLayoutManager(linearLayoutManager);
        binding.rvItemList.addOnScrollListener(new RecyclerPagingListener(linearLayoutManager) {
            final Animation buttonIn = AnimationUtils.loadAnimation(LoadMoreActivity.this, R.anim.button_bottom_in);
            final Animation buttonOut = AnimationUtils.loadAnimation(LoadMoreActivity.this, R.anim.button_bottom_out);

            @Override
            public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //Handling Bottom Layout view
                if (dy > 2 && binding.rlFooterItem.getVisibility() == View.VISIBLE) {
                    binding.rlFooterItem.startAnimation(buttonIn);
                    binding.rlFooterItem.setVisibility(View.GONE);
                } else if (dy <= 2 && binding.rlFooterItem.getVisibility() != View.VISIBLE) {
                    binding.rlFooterItem.setVisibility(View.VISIBLE);
                    binding.rlFooterItem.startAnimation(buttonOut);
                }
            }

            @Override
            protected void loadMoreItems() {
                AppUtils.showLog("TAG", "loadMoreItems");
                viewModel.fetchMoviesList(LoadMoreActivity.this);
            }

            @Override
            public boolean isLastPage() {
                return viewModel.isLastPage();
            }

            @Override
            public boolean isLoading() {
                return viewModel.isLoading();
            }
        });
    }

}