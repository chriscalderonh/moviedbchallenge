package com.chriscalderonh.moviedbchallenge.upcomingmovies.data.remote.mapper

import com.chriscalderonh.moviedbchallenge.upcomingmovies.data.remote.model.Result
import com.chriscalderonh.moviedbchallenge.upcomingmovies.data.remote.model.UpcomingMovies
import com.chriscalderonh.moviedbchallenge.upcomingmovies.domain.model.DomainResult
import com.chriscalderonh.moviedbchallenge.upcomingmovies.domain.model.DomainUpcomingMovies
import javax.inject.Inject

class UpcomingMoviesMapper @Inject constructor() {

    fun UpcomingMovies.fromRemoteToDomain() = DomainUpcomingMovies(
        page = page,
        results = results?.fromRemoteToDomain(),
        totalPages = totalPages,
        totalResults = totalResults
    )

    private fun List<Result>.fromRemoteToDomain(): List<DomainResult> {
        return map { it.fromRemoteToDomain() }
    }

    private fun Result.fromRemoteToDomain() = DomainResult(
        id = id,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title
    )
}