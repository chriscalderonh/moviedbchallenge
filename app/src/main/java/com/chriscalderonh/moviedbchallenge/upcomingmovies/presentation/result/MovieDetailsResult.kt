package com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.result

import com.chriscalderonh.moviedbchallenge.upcomingmovies.domain.model.DomainMovieDetails

sealed class MovieDetailsResult {

    sealed class GetMovieDetailsResult : MovieDetailsResult() {
        object InProgress : GetMovieDetailsResult()
        data class Success(val domainMovieDetails: DomainMovieDetails) : GetMovieDetailsResult()
        data class Error(val error: Throwable) : GetMovieDetailsResult()
    }
}
