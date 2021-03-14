package com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.userintent

import com.chriscalderonh.moviedbchallenge.common.presentation.MviUserIntent

sealed class MovieDetailsUserIntent : MviUserIntent {

    data class InitialUserIntent(val movieId: String): MovieDetailsUserIntent()
}