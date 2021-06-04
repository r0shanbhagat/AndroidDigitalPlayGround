package com.demo.assignment.ui.viewmodel;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.demo.assignment.R;
import com.demo.assignment.core.BaseObservable;
import com.demo.assignment.core.BaseViewModel;
import com.demo.assignment.repository.ApiService;
import com.demo.assignment.repository.model.TopRatedMovies;
import com.demo.assignment.util.AppUtils;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@HiltViewModel
public class LoadMoreViewModel extends BaseViewModel {
    private final ApiService apiService;
    private final MutableLiveData<LoadMoreViewState> responseData;

    //To Maintain the Load More behaviour
    private final ObservableField<Boolean> loadingStatus;
    private final ObservableField<Integer> currentPageCount;
    private final ObservableField<Integer> totalPageCount;

    /**
     * @param apiService:ApiService
     */
    @Inject
    public LoadMoreViewModel(@NonNull ApiService apiService) {
        this.apiService = apiService;
        mDisposable = new CompositeDisposable();
        responseData = new MutableLiveData<>();
        loadingStatus = new ObservableField<>(false);
        currentPageCount = new ObservableField<>(1);
        totalPageCount = new ObservableField<>(0);
    }


    public MutableLiveData<LoadMoreViewState> getHerosList() {
        return responseData;
    }

    /*
     * Fetch Random Jokes List from Server
     */
    public void fetchMoviesList(Context context) {
        onLoading();
        Observable<TopRatedMovies> observable = apiService.getTopRatedMovies(
                context.getString(R.string.my_api_key),
                "en_US",
                Objects.requireNonNull(currentPageCount.get()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
        observable.subscribe(new BaseObservable<TopRatedMovies>() {
            @Override
            public void success(TopRatedMovies topRatedMovies) {
                onSuccess(topRatedMovies);
            }

            @Override
            public void failure(Throwable error) {
                onFailure(error);
            }

            @Override
            public void onSubscribe(@NotNull Disposable dispo) {
                mDisposable.add(dispo);
            }
        });
    }

    /**
     * Show Loading State
     */
    private void onLoading() {
        responseData.postValue(LoadMoreViewState.LOADING_STATE);
    }

    /*
     * Successfully fetched the data from Server
     * @param herosList :CustomModel for heros Object
     */
    private void onSuccess(TopRatedMovies topRatedMovies) {
        if (null != topRatedMovies && AppUtils.isListNotEmpty(topRatedMovies.getMoviesModels())) {
            totalPageCount.set(topRatedMovies.getTotalPages());
            LoadMoreViewState.SUCCESS_STATE.setData(topRatedMovies);
            responseData.postValue(LoadMoreViewState.SUCCESS_STATE);
        } else {
            responseData.postValue(LoadMoreViewState.EMPTY_STATE);
        }
    }

    /**
     * Failure while fetching the Data from server
     *
     * @param error :Error
     */
    private void onFailure(Throwable error) {
        LoadMoreViewState.ERROR_STATE.setError(error);
        responseData.postValue(LoadMoreViewState.ERROR_STATE);
    }

    public void updatePageCount() {
        loadingStatus.set(false);
        currentPageCount.set(Objects.requireNonNull(currentPageCount.get()) + 1);
    }

    public boolean isLoading() {
        return Objects.requireNonNull(loadingStatus.get());
    }

    public void setLoadingStatus(boolean isLoading) {
        loadingStatus.set(isLoading);
    }

    public boolean isLastPage() {
        return Objects.requireNonNull(currentPageCount.get()).equals(Objects.requireNonNull(totalPageCount.get()));
    }


    /**
     * @param throwable to identify the type of error
     * @return appropriate error message
     */
    public String fetchErrorMessage(Context mContext, Throwable throwable) {
        String errorMsg = mContext.getResources().getString(R.string.error_msg_unknown);

        if (!AppUtils.isNetworkConnected(mContext)) {
            errorMsg = mContext.getResources().getString(R.string.error_msg_no_internet);
        } else if (throwable instanceof TimeoutException) {
            errorMsg = mContext.getResources().getString(R.string.error_msg_timeout);
        }

        return errorMsg;
    }


}