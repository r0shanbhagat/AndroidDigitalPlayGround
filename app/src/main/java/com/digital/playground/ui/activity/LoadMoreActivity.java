package com.digital.playground.ui.activity;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;

import com.digital.playground.R;
import com.digital.playground.core.BaseActivity;
import com.digital.playground.databinding.ActivityLoadMoreBinding;
import com.digital.playground.repository.model.MoviesModel;
import com.digital.playground.ui.adapter.LoadMoreAdapter;
import com.digital.playground.ui.adapter.MoviesLoadStateAdapter;
import com.digital.playground.ui.callback.IItemClick;
import com.digital.playground.ui.viewmodel.LoadMoreViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoadMoreActivity extends BaseActivity<ActivityLoadMoreBinding, LoadMoreViewModel> {
    @Inject
    LoadMoreAdapter mAdapter;

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
        initList();
        setClickListener();
        observeResponse();
        // viewModel.fetchMoviesList();
    }

    /**
     * Init the Data here
     */
    private void initList() {
        binding.rvItemList.setItemAnimator(new DefaultItemAnimator());
        binding.rvItemList.setAdapter(
                mAdapter.withLoadStateFooter(new MoviesLoadStateAdapter(new IItemClick<MoviesModel>() {
                    @Override
                    public void retryPageLoad() {
                        mAdapter.retry();
                    }
                })));

    }

    private void observeResponse() {
        viewModel.getMoviesList().subscribe(moviePagingData -> {
            // submit new data to recyclerview adapter
            mAdapter.submitData(getLifecycle(), moviePagingData);
        });


    }

    /**
     * setClickListener
     */
    private void setClickListener() {
        binding.swipeContainer.setOnRefreshListener(() -> {
            // fetchMovies();
            binding.swipeContainer.setRefreshing(false);

        });

        mAdapter.setItemClickListener(new IItemClick<MoviesModel>() {
            @Override
            public void onItemClick(MoviesModel item) {
                //TODO Handle ITEM click
            }
        });
    }


}