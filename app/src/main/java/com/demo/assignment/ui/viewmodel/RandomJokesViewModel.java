package com.demo.assignment.ui.viewmodel;


import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.demo.assignment.core.BaseObservable;
import com.demo.assignment.core.BaseViewModel;
import com.demo.assignment.repository.ApiService;
import com.demo.assignment.repository.NetworkRepository;
import com.demo.assignment.repository.model.RandomJokesModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RandomJokesViewModel extends BaseViewModel {
    private final ApiService apiService;
    private final MutableLiveData<RandomJokesViewState> mJokeState;
    private final MutableLiveData<List<RandomJokesModel>> jokesList;
    private final String mFirstName;
    private final String mLastName;

    /**
     * @param firstName         :FirstName
     * @param lastName:LastName
     */
    public RandomJokesViewModel(@NonNull Context context, @NonNull String firstName, @NonNull String lastName) {
        this.apiService = NetworkRepository.getService(context);
        disposable = new CompositeDisposable();
        mJokeState = new MutableLiveData<>();
        jokesList = new MutableLiveData<>(new ArrayList<>());
        mFirstName = firstName;
        mLastName = lastName;
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
                disposable.add(dispo);
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

    /**
     * A creator is used to inject the project ID into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        private Context context;
        private String firstName, lastName;

        public Factory(@NonNull Context context, @NonNull String firstName, @NonNull String lastName) {
            this.context = context;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        @Override
        @NonNull
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(RandomJokesViewModel.class)) {
                //noinspection unchecked
                return (T) new RandomJokesViewModel(context, firstName, lastName);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }

}