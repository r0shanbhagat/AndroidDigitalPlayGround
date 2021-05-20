package com.demo.assignment.ui.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.demo.assignment.repository.ApiService;
import com.demo.assignment.repository.BaseNetworkObservable;
import com.demo.assignment.repository.NetworkRepository;
import com.demo.assignment.repository.model.RandomJokesModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RandomJokesViewModel extends AndroidViewModel {
    private final MutableLiveData<RandomJokesViewState> responseData;
    private final MutableLiveData<String> firstName;
    private final MutableLiveData<String> lastName;
    private final MutableLiveData<List<RandomJokesModel>> jokesList;
    private CompositeDisposable mDisposable;

    /**
     * @param application :Application Context
     */
    public RandomJokesViewModel(@NonNull Application application) {
        super(application);
        responseData = new MutableLiveData<>();
        mDisposable = new CompositeDisposable();
        firstName = new MutableLiveData<>();
        lastName = new MutableLiveData<>();
        jokesList = new MutableLiveData<>(new ArrayList<>());
    }

    public MutableLiveData<RandomJokesViewState> getJokesData() {
        return responseData;
    }

    /**
     * @return List<RandomJokesModel>
     */
    public List<RandomJokesModel> getJokesList() {
        return jokesList.getValue();
    }

    /**
     * getFirstName
     *
     * @return FirstName
     */
    public MutableLiveData<String> getFirstName() {
        return firstName;
    }

    /**
     * getLastName
     *
     * @return LastName
     */
    public MutableLiveData<String> getLastName() {
        return lastName;
    }

    /*
     * Fetch Random Jokes List from Server
     */
    public void fetchJokesList() {
        onLoading();
        ApiService apiService = NetworkRepository.getService(getApplication());
        Map<String, String> data = new HashMap<>();
        data.put("firstName", getFirstName().getValue());
        data.put("lastName", getLastName().getValue());
        Observable<RandomJokesModel> observable = apiService.getJokesList(data)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
        observable.subscribe(new BaseNetworkObservable<RandomJokesModel>() {
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
        responseData.postValue(RandomJokesViewState.LOADING_STATE);
    }

    /*
     * Successfully fetched the Jokes from Server
     * @param jokesModel :CustomModel for Jokes Object
     */
    private void onSuccess(RandomJokesModel jokesModel) {
        getJokesList().add(jokesModel);
        responseData.postValue(RandomJokesViewState.SUCCESS_STATE);
    }

    /**
     * Failure while fetching the jokes from server
     *
     * @param throwable :Error
     */
    private void onFailure(Throwable throwable) {
        RandomJokesViewState.ERROR_STATE.setError(throwable);
        responseData.postValue(RandomJokesViewState.ERROR_STATE);
    }

    public void resetLiveData() {
        responseData.postValue(null);
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