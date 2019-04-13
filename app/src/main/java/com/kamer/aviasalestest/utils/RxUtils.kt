package com.kamer.aviasalestest.utils

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.CompositeDisposable


inline fun LifecycleOwner.setupRx(crossinline setup: (CompositeDisposable) -> Unit) {
    this.lifecycle.addObserver(RxLifecycleObserver { setup(it) })
}

class RxLifecycleObserver(private val setup: (CompositeDisposable) -> Unit) : LifecycleObserver {
    private var compositeDisposable = CompositeDisposable()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start() {
        setup(compositeDisposable)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop() {
        compositeDisposable.dispose()
        compositeDisposable = CompositeDisposable()
    }
}