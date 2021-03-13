package com.chriscalderonh.moviedbchallenge.upcomingmovies.domain

import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(private val repository: UpcomingMoviesRepository) {

    fun execute(movieId: String) = repository.getMovieDetails(movieId)
}