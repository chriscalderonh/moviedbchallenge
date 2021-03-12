package com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.action

sealed class UpcomingMoviesAction {

    data class MovieListAction(val page: Int) : UpcomingMoviesAction()
}