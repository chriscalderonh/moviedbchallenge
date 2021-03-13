package com.chriscalderonh.moviedbchallenge

import android.app.Application
import com.chriscalderonh.moviedbchallenge.di.AppComponent
import com.chriscalderonh.moviedbchallenge.di.AppModule
import com.chriscalderonh.moviedbchallenge.di.DaggerAppComponent

class MainApplication : Application() {
    private val component: AppComponent by lazy {
        DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }
}