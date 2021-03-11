package com.chriscalderonh.moviedbchallenge.upcomingmovies.data.source

import com.chriscalderonh.moviedbchallenge.upcomingmovies.data.repository.UpcomingMoviesRemote
import javax.inject.Inject

class UpcomingMoviesSourceFactory @Inject constructor(
    private val upcomingMoviesRemote: UpcomingMoviesRemote){

    fun getRemote(): UpcomingMoviesRemote = upcomingMoviesRemote
}