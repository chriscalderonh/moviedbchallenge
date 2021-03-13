package com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.result

import com.chriscalderonh.moviedbchallenge.upcomingmovies.domain.model.DomainUpcomingMovies

sealed class UpcomingMoviesResult {

    sealed class MovieListResult : UpcomingMoviesResult() {
        object InProgress : MovieListResult()
        data class Success(val domainUpcomingMovies: DomainUpcomingMovies) : MovieListResult()
        data class Error(val error: Throwable) : MovieListResult()
    }
}