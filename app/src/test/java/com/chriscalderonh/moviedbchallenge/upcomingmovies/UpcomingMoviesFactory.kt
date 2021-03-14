package com.chriscalderonh.moviedbchallenge.upcomingmovies

import com.chriscalderonh.moviedbchallenge.RandomValuesFactory.generateBoolean
import com.chriscalderonh.moviedbchallenge.RandomValuesFactory.generateDouble
import com.chriscalderonh.moviedbchallenge.RandomValuesFactory.generateInt
import com.chriscalderonh.moviedbchallenge.RandomValuesFactory.generateString
import com.chriscalderonh.moviedbchallenge.upcomingmovies.data.remote.model.Dates
import com.chriscalderonh.moviedbchallenge.upcomingmovies.data.remote.model.MovieDetails
import com.chriscalderonh.moviedbchallenge.upcomingmovies.data.remote.model.Result
import com.chriscalderonh.moviedbchallenge.upcomingmovies.data.remote.model.UpcomingMovies
import com.chriscalderonh.moviedbchallenge.upcomingmovies.domain.model.DomainMovieDetails
import com.chriscalderonh.moviedbchallenge.upcomingmovies.domain.model.DomainResult
import com.chriscalderonh.moviedbchallenge.upcomingmovies.domain.model.DomainUpcomingMovies
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.model.UiMovieDetails
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.model.UiResult
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.model.UiUpcomingMovies

object UpcomingMoviesFactory {

    fun generateUpcomingMovies() = UpcomingMovies(
        generateDates(),
        generateInt(),
        generateResults(),
        generateInt(),
        generateInt()
    )

    private fun generateDates() = Dates(
        generateString(),
        generateString()
    )

    private fun generateResults() = (0..10).map { generateResult() }

    private fun generateResult() = Result(
        generateBoolean(),
        generateString(),
        arrayListOf(),
        generateInt(),
        generateString(),
        generateString(),
        generateString(),
        generateDouble(),
        generateString(),
        generateString(),
        generateString(),
        generateBoolean(),
        generateDouble(),
        generateInt()
    )

    fun generateDomainUpcomingMovies() = DomainUpcomingMovies(
        generateInt(),
        generateDomainResults(),
        generateInt(),
        generateInt()
    )

    private fun generateDomainResults() = (0..10).map { generateDomainResult() }

    private fun generateDomainResult() = DomainResult(
        generateInt(),
        generateString(),
        generateString(),
        generateString()
    )

    fun generateUiUpcomingMovies() = UiUpcomingMovies(
        generateInt(),
        generateUiResults(),
        generateInt(),
        generateInt()
    )

    private fun generateUiResults() = (0..10).map { generateUiResult() }

    private fun generateUiResult() = UiResult(
        generateInt(),
        generateString(),
        generateString(),
        generateString()
    )

    fun generateMovieDetails() = MovieDetails(
        generateBoolean(),
        generateString(),
        generateString(),
        generateInt(),
        arrayListOf(),
        generateString(),
        generateInt(),
        generateString(),
        generateString(),
        generateString(),
        generateString(),
        generateDouble(),
        generateString(),
        arrayListOf(),
        arrayListOf(),
        generateString(),
        generateInt(),
        generateInt(),
        arrayListOf(),
        generateString(),
        generateString(),
        generateString(),
        generateBoolean(),
        generateDouble(),
        generateInt()
    )

    fun generateDomainMovieDetails() = DomainMovieDetails(
        generateString(),
        generateString(),
        generateString(),
        generateString(),
        generateString(),
        generateInt()
    )

    fun generateUiMovieDetails() = UiMovieDetails(
        generateString(),
        generateString(),
        generateString(),
        generateString(),
        generateString(),
        generateInt()
    )
}