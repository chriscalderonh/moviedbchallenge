package com.chriscalderonh.moviedbchallenge.upcomingmovies.di

import com.chriscalderonh.moviedbchallenge.di.ActivityScope
import dagger.Component

@ActivityScope
@Component(
    modules = [DataModule::class, RemoteModule::class, PresentationModule::class]
)
interface  UpcomingMoviesComponent {
}