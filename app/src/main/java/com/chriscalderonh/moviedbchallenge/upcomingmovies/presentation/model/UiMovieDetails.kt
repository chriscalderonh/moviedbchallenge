package com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.model

data class UiMovieDetails (
    val posterPath: String,
    val title: String,
    val tagline: String,
    val overview: String,
    val releaseDate: String,
    val runtime: Int
)