package com.chriscalderonh.moviedbchallenge.upcomingmovies.data.remote.mapper

import com.chriscalderonh.moviedbchallenge.upcomingmovies.data.remote.model.MovieDetails
import com.chriscalderonh.moviedbchallenge.upcomingmovies.domain.model.DomainMovieDetails
import javax.inject.Inject

class MovieDetailsMapper @Inject constructor() {

    fun MovieDetails.fromRemoteToDomain() = DomainMovieDetails(
        posterPath = posterPath,
        title = title,
        tagline = tagline,
        overview = overview,
        releaseDate = releaseDate,
        runtime = runtime
    )
}