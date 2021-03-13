package com.chriscalderonh.moviedbchallenge.upcomingmovies.domain.model

data class DomainMovieDetails (
    val posterPath: String?,
    val title: String?,
    val tagline: String?,
    val overview: String?,
    val releaseDate: String?,
    val runtime: Int?
)