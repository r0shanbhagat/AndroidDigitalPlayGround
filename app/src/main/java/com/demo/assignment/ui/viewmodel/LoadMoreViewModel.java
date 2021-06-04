package com.demo.assignment.ui.viewmodel;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.demo.assignment.core.BaseObservable;
import com.demo.assignment.core.BaseViewModel;
import com.demo.assignment.repository.ApiService;
import com.demo.assignment.repository.model.HerosModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

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

    /**
     * @param apiService:ApiService
     */
    @Inject
    public LoadMoreViewModel(@NonNull ApiService apiService) {
        this.apiService = apiService;
        mDisposable = new CompositeDisposable();
        responseData = new MutableLiveData<>();
    }


    public MutableLiveData<LoadMoreViewState> getHerosList() {
        return responseData;
    }

    /*
     * Fetch Random Jokes List from Server
     */
    public void fetchHerosList() {
        onLoading();
        Observable<List<HerosModel>> observable = apiService.getHeroesList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
        observable.subscribe(new BaseObservable<List<HerosModel>>() {
            @Override
            public void success(List<HerosModel> jokesModel) {
                onSuccess(jokesModel);
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
    private void onSuccess(List<HerosModel> herosList) {
        LoadMoreViewState.SUCCESS_STATE.setData(herosList);
        responseData.postValue(LoadMoreViewState.SUCCESS_STATE);
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

}