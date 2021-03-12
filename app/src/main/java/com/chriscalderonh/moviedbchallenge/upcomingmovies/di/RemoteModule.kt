package com.chriscalderonh.moviedbchallenge.upcomingmovies.di

import com.chriscalderonh.moviedbchallenge.upcomingmovies.data.remote.UpcomingMoviesApiFactory
import com.chriscalderonh.moviedbchallenge.upcomingmovies.data.remote.UpcomingMoviesRemoteImpl
import com.chriscalderonh.moviedbchallenge.upcomingmovies.data.remote.UpcomingMoviesRestApi
import com.chriscalderonh.moviedbchallenge.upcomingmovies.data.repository.UpcomingMoviesRemote
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class RemoteModule {
    @Binds
    abstract fun bindSearchRemote(upcomingMoviesRemoteImpl: UpcomingMoviesRemoteImpl): UpcomingMoviesRemote

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun provideUpcomingMoviesRestApi(): UpcomingMoviesRestApi {
            return UpcomingMoviesApiFactory().makeRestApi()
        }
    }
}