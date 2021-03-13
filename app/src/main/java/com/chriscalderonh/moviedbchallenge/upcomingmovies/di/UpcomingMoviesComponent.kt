package com.chriscalderonh.moviedbchallenge.upcomingmovies.di

import com.chriscalderonh.moviedbchallenge.di.ActivityScope
import com.chriscalderonh.moviedbchallenge.di.AppComponent
import com.chriscalderonh.moviedbchallenge.upcomingmovies.ui.MoviesActivity
import com.chriscalderonh.moviedbchallenge.upcomingmovies.ui.UpcomingMoviesFragment
import dagger.Component

@ActivityScope
@Component(
    modules = [DataModule::class, RemoteModule::class, PresentationModule::class]
)
interface  UpcomingMoviesComponent {

    fun inject(moviesActivity: MoviesActivity)

    fun inject(upcomingMoviesFragment: UpcomingMoviesFragment)
}