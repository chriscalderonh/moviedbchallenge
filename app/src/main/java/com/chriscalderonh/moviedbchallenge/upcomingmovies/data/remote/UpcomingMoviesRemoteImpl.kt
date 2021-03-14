package com.chriscalderonh.moviedbchallenge.upcomingmovies.data.remote

import com.chriscalderonh.moviedbchallenge.upcomingmovies.data.remote.Constants.API_KEY
import com.chriscalderonh.moviedbchallenge.upcomingmovies.data.remote.model.MovieDetails
import com.chriscalderonh.moviedbchallenge.upcomingmovies.data.repository.UpcomingMoviesRemote
import io.reactivex.Single
import javax.inject.Inject

class UpcomingMoviesRemoteImpl @Inject constructor(private val restApi: UpcomingMoviesRestApi) :
    UpcomingMoviesRemote {

    override fun getUpcomingMovies(page: Int) =
        restApi.getUpcomingMoviesList(API_KEY, page)

    override fun getMovieDetails(movieId: String): Single<MovieDetails> =
        restApi.getMovieDetails(movieId, API_KEY)
}