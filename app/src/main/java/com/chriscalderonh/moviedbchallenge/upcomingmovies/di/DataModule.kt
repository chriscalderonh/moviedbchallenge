package com.chriscalderonh.moviedbchallenge.upcomingmovies.di

import com.chriscalderonh.moviedbchallenge.upcomingmovies.data.UpcomingMoviesDataRepository
import com.chriscalderonh.moviedbchallenge.upcomingmovies.domain.UpcomingMoviesRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

    @Binds
    abstract fun bindUpcomingMoviesDataRepository(
        dataRepository: UpcomingMoviesDataRepository
    ): UpcomingMoviesRepository
}