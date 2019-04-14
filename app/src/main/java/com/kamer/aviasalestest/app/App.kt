package com.kamer.aviasalestest.app

import android.annotation.SuppressLint
import android.app.Application
import com.kamer.aviasalestest.dependency.ServiceLocator


class App : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var serviceLocator: ServiceLocator
            private set
    }

    override fun onCreate() {
        super.onCreate()
        serviceLocator = ServiceLocator(this)
    }

}