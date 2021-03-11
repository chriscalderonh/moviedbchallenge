package com.chriscalderonh.moviedbchallenge.upcomingmovies.domain.model

data class DomainUpcomingMovies(
    val page: Int?,
    val results: List<DomainResult>?,
    val totalPages: Int?,
    val totalResults: Int?
)

data class DomainResult(
    val id: Int?,
    val posterPath: String?,
    val releaseDate: String?,
    val title: String?
)