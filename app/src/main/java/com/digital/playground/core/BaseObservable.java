package com.digital.playground.core;

import com.digital.playground.util.AppUtils;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public abstract class BaseObservable<T> implements Observer<T> {

    @Override
    public void onSubscribe(@NonNull Disposable dispo) {
        /* Disposable */
    }

    @Override
    public void onNext(@NonNull T t) {
        success(t);
    }

    @Override
    public void onError(@NonNull Throwable t) {
        failure(t);
        AppUtils.showException(t);
    }

    @Override
    public void onComplete() {
    }

    public abstract void success(T response);

    public abstract void failure(Throwable t);
}
