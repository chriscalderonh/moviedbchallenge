package com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.userintent

import com.chriscalderonh.moviedbchallenge.common.presentation.MviUserIntent

sealed class UpcomingMoviesUserIntent : MviUserIntent {

    data class InitialUserIntent(val page: Int): UpcomingMoviesUserIntent()
}