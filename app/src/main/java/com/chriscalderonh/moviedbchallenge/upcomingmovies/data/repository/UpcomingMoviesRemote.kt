package com.chriscalderonh.moviedbchallenge.upcomingmovies.data.repository

import com.chriscalderonh.moviedbchallenge.upcomingmovies.data.remote.model.UpcomingMovies
import io.reactivex.Single

interface UpcomingMoviesRemote {

    fun getUpcomingMovies(page: Int): Single<UpcomingMovies>
}