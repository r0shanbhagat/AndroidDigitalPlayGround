package com.demo.assignment.core;

import androidx.lifecycle.ViewModel;

import io.reactivex.rxjava3.disposables.CompositeDisposable;


public class BaseViewModel extends ViewModel {
    protected CompositeDisposable disposable;

    @Override
    protected void onCleared() {
        super.onCleared();
        if (null != disposable) {
            disposable.clear();
            disposable = null;
        }
    }
}
