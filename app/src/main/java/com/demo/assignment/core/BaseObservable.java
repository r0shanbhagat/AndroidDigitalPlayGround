package com.demo.assignment.core;

import com.demo.assignment.util.AppUtils;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

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
