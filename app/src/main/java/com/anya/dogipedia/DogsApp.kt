package com.anya.dogipedia

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class DogsApp : Application() {

    companion object {
        lateinit var instance: DogsApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        initLogging()
    }

    private fun initLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}