package com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.mapper

import com.chriscalderonh.moviedbchallenge.common.presentation.Constants.DEFAULT_INT_VALUE
import com.chriscalderonh.moviedbchallenge.upcomingmovies.domain.model.DomainResult
import com.chriscalderonh.moviedbchallenge.upcomingmovies.domain.model.DomainUpcomingMovies
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.model.UiResult
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.model.UiUpcomingMovies
import javax.inject.Inject

class UiUpcomingMoviesMapper @Inject constructor() {

    fun DomainUpcomingMovies.fromDomainToUi() = UiUpcomingMovies(
        page = page ?: DEFAULT_INT_VALUE,
        results = results?.fromDomainToUi(),
        totalPages = totalPages ?: DEFAULT_INT_VALUE,
        totalResults = totalResults ?: DEFAULT_INT_VALUE
    )

    private fun List<DomainResult>.fromDomainToUi(): List<UiResult> {
        return map { it.fromDomainToUi() }
    }

    private fun DomainResult.fromDomainToUi() = UiResult(
        id = id ?: DEFAULT_INT_VALUE,
        posterPath = posterPath.orEmpty(),
        releaseDate = releaseDate.orEmpty(),
        title = title.orEmpty()
    )
}