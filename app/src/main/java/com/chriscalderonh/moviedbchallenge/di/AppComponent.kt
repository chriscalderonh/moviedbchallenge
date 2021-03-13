package com.chriscalderonh.moviedbchallenge.di

import com.chriscalderonh.moviedbchallenge.MainApplication
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(app: MainApplication)
}