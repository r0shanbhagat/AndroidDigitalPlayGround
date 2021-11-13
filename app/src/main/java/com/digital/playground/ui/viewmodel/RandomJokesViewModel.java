package com.digital.playground.ui.viewmodel;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.digital.playground.core.BaseObservable;
import com.digital.playground.core.BaseViewModel;
import com.digital.playground.core.ViewState;
import com.digital.playground.repository.ApiService;
import com.digital.playground.repository.model.RandomJokesModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class RandomJokesViewModel extends BaseViewModel {
    private final ApiService apiService;
    private final MutableLiveData<ViewState> mJokeState;
    private final MutableLiveData<List<RandomJokesModel>> jokesList;
    private final String mFirstName;
    private final String mLastName;

    /**
     * @param firstName         :FirstName
     * @param lastName:LastName
     */
    public RandomJokesViewModel(ApiService apiService, @NonNull String firstName, @NonNull String lastName) {
        this.apiService = apiService;
        disposable = new CompositeDisposable();
        mJokeState = new MutableLiveData<>();
        jokesList = new MutableLiveData<>(new ArrayList<>());
        mFirstName = firstName;
        mLastName = lastName;
    }

    public MutableLiveData<ViewState> getJokesData() {
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
        observable.subscribe(new BaseObservable<>() {
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
                disposable.add(dispo);
            }
        });
    }

    /**
     * Show Loading State
     */
    private void onLoading() {
        mJokeState.postValue(new RandomJokesViewState(RandomJokesViewState.LOADING));
    }

    /*
     * Successfully fetched the Jokes from Server
     * @param jokesModel :CustomModel for Jokes Object
     */
    private void onSuccess(RandomJokesModel jokesModel) {
        getJokesList().add(jokesModel);
        RandomJokesViewState viewState = new RandomJokesViewState(RandomJokesViewState.SUCCESS);
        viewState.setData(jokesModel);
        mJokeState.postValue(viewState);
    }

    /**
     * Failure while fetching the jokes from server
     *
     * @param error :Error
     */
    private void onFailure(Throwable error) {
        mJokeState.postValue(new RandomJokesViewState(RandomJokesViewState.FAILED));
    }

    public void resetLiveData() {
        mJokeState.postValue(null);
    }

    /**
     * A creator is used to inject the project ID into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        private final ApiService apiService;
        private final String firstName;
        private final String lastName;

        public Factory(@NonNull ApiService apiService, @NonNull String firstName, @NonNull String lastName) {
            this.apiService = apiService;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        @Override
        @NonNull
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(RandomJokesViewModel.class)) {
                //noinspection unchecked
                return (T) new RandomJokesViewModel(apiService, firstName, lastName);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }

}