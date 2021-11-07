package com.digital.playground.core

import com.digital.playground.util.AppUtils.showException
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseObservable<T> : Observer<T> {
    override fun onSubscribe(dispo: Disposable) {
        /* Disposable */
    }

    override fun onNext(t: T) {
        success(t)
    }

    override fun onError(t: Throwable) {
        failure(t)
        showException(t)
    }

    override fun onComplete() {}
    abstract fun success(response: T)
    abstract fun failure(t: Throwable)
}