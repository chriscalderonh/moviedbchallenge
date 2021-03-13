package com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.mapper

import com.chriscalderonh.moviedbchallenge.common.presentation.Constants.DEFAULT_INT_VALUE
import com.chriscalderonh.moviedbchallenge.upcomingmovies.domain.model.DomainMovieDetails
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.model.UiMovieDetails
import javax.inject.Inject

class UiMovieDetailsMapper @Inject constructor() {

    fun DomainMovieDetails.fromDomainToUi() = UiMovieDetails(
        posterPath = posterPath.orEmpty(),
        title = title.orEmpty(),
        tagline = tagline.orEmpty(),
        overview = overview.orEmpty(),
        releaseDate = releaseDate.orEmpty(),
        runtime = runtime ?: DEFAULT_INT_VALUE
    )
}