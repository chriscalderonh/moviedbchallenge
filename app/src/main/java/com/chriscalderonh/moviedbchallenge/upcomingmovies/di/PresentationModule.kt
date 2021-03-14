package com.chriscalderonh.moviedbchallenge.upcomingmovies.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chriscalderonh.moviedbchallenge.common.presentation.AppExecutionThread
import com.chriscalderonh.moviedbchallenge.common.presentation.ExecutionThread
import com.chriscalderonh.moviedbchallenge.common.presentation.ViewModelFactory
import com.chriscalderonh.moviedbchallenge.di.ViewModelKey
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.MovieDetailsViewModel
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.UpcomingMoviesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class PresentationModule {

    @Binds
    abstract fun bindExecutionThread(executionThread: AppExecutionThread): ExecutionThread

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(UpcomingMoviesViewModel::class)
    abstract fun bindUpcomingMoviesViewModel(
        viewModel: UpcomingMoviesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailsViewModel::class)
    abstract fun bindMovieDetailsViewModel(
        viewModel: MovieDetailsViewModel): ViewModel
}