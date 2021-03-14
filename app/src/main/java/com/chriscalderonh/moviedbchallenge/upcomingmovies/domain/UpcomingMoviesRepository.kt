package com.chriscalderonh.moviedbchallenge.upcomingmovies.domain

import com.chriscalderonh.moviedbchallenge.upcomingmovies.domain.model.DomainMovieDetails
import com.chriscalderonh.moviedbchallenge.upcomingmovies.domain.model.DomainUpcomingMovies
import io.reactivex.Single

interface UpcomingMoviesRepository {

    fun getUpcomingMovies(page: Int): Single<DomainUpcomingMovies>

    fun getMovieDetails(movieId: String): Single<DomainMovieDetails>
}