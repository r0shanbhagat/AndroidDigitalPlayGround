package com.demo.assignment.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.demo.assignment.core.BaseViewModel;
import com.demo.assignment.database.Word;
import com.demo.assignment.database.WordRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class WordViewModel extends BaseViewModel {

    private final WordRepository mRepository;
    private final MutableLiveData<WordViewState> mWordState;


    @Inject
    public WordViewModel(WordRepository repository) {
        mRepository = repository;
        disposable = new CompositeDisposable();
        mWordState = new MutableLiveData<>();

    }

    public MutableLiveData<WordViewState> getAllWords() {
        return mWordState;
    }

    /**
     * fetchWordList from DB
     */
    public void fetchWordList() {
        mWordState.postValue(WordViewState.LOADING_STATE);
        Single<List<Word>> observable = mRepository.getAllWords()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
        observable.subscribe(new SingleObserver<List<Word>>() {
            @Override
            public void onSuccess(@NonNull List<Word> words) {
                WordViewState.SUCCESS_STATE.setData(words);
                mWordState.postValue(WordViewState.SUCCESS_STATE);
            }

            @Override
            public void onError(@NonNull Throwable error) {
                WordViewState.ERROR_STATE.setError(error);
                mWordState.postValue(WordViewState.ERROR_STATE);
            }

            @Override
            public void onSubscribe(@NonNull Disposable dispo) {
                disposable.add(dispo);
            }
        });
    }

    /**
     * @param word:Word
     */
    public void insert(Word word) {
        mRepository.insert(word);
    }
}