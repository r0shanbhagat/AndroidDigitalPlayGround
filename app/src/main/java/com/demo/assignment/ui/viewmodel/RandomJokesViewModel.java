package com.demo.assignment.ui.viewmodel;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.demo.assignment.core.BaseObservable;
import com.demo.assignment.repository.ApiService;
import com.demo.assignment.repository.model.RandomJokesModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@HiltViewModel
public class RandomJokesViewModel extends ViewModel {
    private final ApiService apiService;
    private final MutableLiveData<RandomJokesViewState> mJokeState;
    private final MutableLiveData<List<RandomJokesModel>> jokesList;
    private final String mFirstName;
    private final String mLastName;
    private CompositeDisposable mDisposable;

    /**
     * @param apiService:ApiService
     */
    @Inject
    public RandomJokesViewModel(@NotNull SavedStateHandle savedStateHandle, @NonNull ApiService apiService) {
        this.apiService = apiService;
        mDisposable = new CompositeDisposable();
        mJokeState = new MutableLiveData<>();
        jokesList = new MutableLiveData<>(new ArrayList<>());
        mFirstName = savedStateHandle.get("FirstName");
        mLastName = savedStateHandle.get("LastName");
    }

    public MutableLiveData<RandomJokesViewState> getJokesData() {
        return mJokeState;
    }

    /**
     * @return List<RandomJokesModel>
     */
    public List<RandomJokesModel> getJokesList() {
        return jokesList.getValue();
    }

    /*
     * Fetch Random Jokes List from Server
     */
    public void fetchJokesList() {
        onLoading();
        Map<String, String> data = new HashMap<>();
        data.put("firstName", mFirstName);
        data.put("lastName", mLastName);
        Observable<RandomJokesModel> observable = apiService.getJokesList(data)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
        observable.subscribe(new BaseObservable<RandomJokesModel>() {
            @Override
            public void success(RandomJokesModel jokesModel) {
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
        mJokeState.postValue(RandomJokesViewState.LOADING_STATE);
    }

    /*
     * Successfully fetched the Jokes from Server
     * @param jokesModel :CustomModel for Jokes Object
     */
    private void onSuccess(RandomJokesModel jokesModel) {
        getJokesList().add(jokesModel);
        //RandomJokesViewState.SUCCESS_STATE.setData(jokesModel);
        mJokeState.postValue(RandomJokesViewState.SUCCESS_STATE);
    }

    /**
     * Failure while fetching the jokes from server
     *
     * @param error :Error
     */
    private void onFailure(Throwable error) {
        RandomJokesViewState.ERROR_STATE.setError(error);
        mJokeState.postValue(RandomJokesViewState.ERROR_STATE);
    }

    public void resetLiveData() {
        mJokeState.postValue(null);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (null != mDisposable) {
            mDisposable.clear();
            mDisposable = null;
        }
    }

}