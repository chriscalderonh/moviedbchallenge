package com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.model

data class UiUpcomingMovies(
    val page: Int,
    val results: List<UiResult>?,
    val totalPages: Int,
    val totalResults: Int
)

data class UiResult(
    val id: Int,
    val posterPath: String,
    val releaseDate: String,
    val title: String
)
