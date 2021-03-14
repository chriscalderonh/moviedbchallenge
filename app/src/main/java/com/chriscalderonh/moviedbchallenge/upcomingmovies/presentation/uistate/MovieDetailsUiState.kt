package com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.uistate

import com.chriscalderonh.moviedbchallenge.common.presentation.MviUiState
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.model.UiMovieDetails
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.model.UiUpcomingMovies

sealed class MovieDetailsUiState(
        val isLoading: Boolean = false,
        val movieDetails: UiMovieDetails? = null,
        val error: String? = null
) : MviUiState {

    object Default : MovieDetailsUiState()

    object Loading : MovieDetailsUiState(
            isLoading = true
    )

    data class SuccessGettingMovieDetails(val uiMovieDetails: UiMovieDetails) :
            MovieDetailsUiState(
                    movieDetails = uiMovieDetails
            )

    data class ErrorGettingMovieDetails(val message: String?) : MovieDetailsUiState(
            error = message ?: DEFAULT_ERROR_MSG
    )

    companion object {
        const val DEFAULT_ERROR_MSG = ""
    }
}
