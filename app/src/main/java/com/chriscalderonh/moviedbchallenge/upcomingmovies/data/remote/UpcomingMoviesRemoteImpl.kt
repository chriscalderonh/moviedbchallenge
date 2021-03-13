package com.chriscalderonh.moviedbchallenge.upcomingmovies.data.remote

import com.chriscalderonh.moviedbchallenge.upcomingmovies.data.remote.Constants.API_KEY
import com.chriscalderonh.moviedbchallenge.upcomingmovies.data.repository.UpcomingMoviesRemote
import javax.inject.Inject

class UpcomingMoviesRemoteImpl @Inject constructor(private val restRestApi: UpcomingMoviesRestApi) :
    UpcomingMoviesRemote {

    override fun getUpcomingMovies(page: Int) =
        restRestApi.getUpcomingMoviesList(API_KEY, page)
}