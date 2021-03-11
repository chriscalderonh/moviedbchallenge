package com.chriscalderonh.moviedbchallenge.di

import com.chriscalderonh.moviedbchallenge.MainApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: MainApplication) {
    @Provides
    @Singleton
    fun provideApp() = app
}