package com.chriscalderonh.moviedbchallenge.upcomingmovies.domain

import com.chriscalderonh.moviedbchallenge.upcomingmovies.data.remote.model.UpcomingMovies
import javax.inject.Inject

class GetUpcomingMoviesUseCase @Inject constructor(private val repository: UpcomingMoviesRepository) {

    fun execute(page: Int) = repository.getUpcomingMovies(page)
}