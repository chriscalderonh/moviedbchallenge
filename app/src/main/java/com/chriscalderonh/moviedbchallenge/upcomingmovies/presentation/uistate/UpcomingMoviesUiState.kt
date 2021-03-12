package com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.uistate

import com.chriscalderonh.moviedbchallenge.common.presentation.MviUiState
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.model.UiUpcomingMovies

sealed class UpcomingMoviesUiState(
    val isLoading: Boolean = false,
    val upcomingMovies: UiUpcomingMovies? = null,
    val error: String? = null
) : MviUiState {

    object Default : UpcomingMoviesUiState()

    object Loading : UpcomingMoviesUiState(
        isLoading = true
    )

    data class SuccessGettingMoviesList(val uiUpcomingMovies: UiUpcomingMovies) :
        UpcomingMoviesUiState(
            upcomingMovies = uiUpcomingMovies
        )

    data class ErrorGettingMoviesList(val message: String?) : UpcomingMoviesUiState(
        error = message ?: DEFAULT_ERROR_MSG
    )

    companion object {
        const val DEFAULT_ERROR_MSG = ""
    }
}