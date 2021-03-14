package com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.action

sealed class MovieDetailsAction {

    data class GetMovieDetailsAction(val movieId: String) : MovieDetailsAction()
}