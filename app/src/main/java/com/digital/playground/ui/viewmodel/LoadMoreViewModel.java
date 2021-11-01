package com.digital.playground.ui.viewmodel;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava3.PagingRx;

import com.digital.playground.core.BaseViewModel;
import com.digital.playground.paging.MoviePagingSource;
import com.digital.playground.repository.ApiService;
import com.digital.playground.repository.model.MoviesModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Flowable;
import kotlinx.coroutines.CoroutineScope;

@HiltViewModel
public class LoadMoreViewModel extends BaseViewModel {
    private final ApiService apiService;
    private Flowable<PagingData<MoviesModel>> pagingDataFlow;

    /**
     * @param apiService:ApiService
     */
    @Inject
    public LoadMoreViewModel(@NonNull ApiService apiService) {
        this.apiService = apiService;
        fetchMoviesList();

    }

    public Flowable<PagingData<MoviesModel>> getMoviesList() {
        return pagingDataFlow;
    }

    /*
     * Fetch Random Jokes List from Server
     */
    public void fetchMoviesList() {
        // Define Paging Source
        MoviePagingSource moviePagingSource = new MoviePagingSource(apiService);

        // Create new Pager
        Pager<Integer, MoviesModel> pager = new Pager(
                // Create new paging config
                new PagingConfig(20, // pageSize - Count of items in one page
                        20, // prefetchDistance - Number of items to prefetch
                        false, // enablePlaceholders - Enable placeholders for data which is not yet loaded
                        20, // initialLoadSize - Count of items to be loaded initially
                        20 * 499),// maxSize - Count of total items to be shown in recyclerview
                () -> moviePagingSource); // set paging source

        // inti Flowable
        pagingDataFlow = PagingRx.getFlowable(pager);
        CoroutineScope coroutineScope = ViewModelKt.getViewModelScope(this);
        PagingRx.cachedIn(pagingDataFlow, coroutineScope);
    }

}