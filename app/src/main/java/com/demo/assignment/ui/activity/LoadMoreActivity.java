package com.demo.assignment.ui.activity;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;

import com.demo.assignment.R;
import com.demo.assignment.core.BaseActivity;
import com.demo.assignment.databinding.ActivityLoadMoreBinding;
import com.demo.assignment.repository.model.MoviesModel;
import com.demo.assignment.ui.adapter.LoadMoreAdapter;
import com.demo.assignment.ui.adapter.MoviesLoadStateAdapter;
import com.demo.assignment.ui.callback.IItemClick;
import com.demo.assignment.ui.viewmodel.LoadMoreViewModel;

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