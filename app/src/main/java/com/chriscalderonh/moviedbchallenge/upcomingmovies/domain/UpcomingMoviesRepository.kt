package com.chriscalderonh.moviedbchallenge.upcomingmovies.domain

import com.chriscalderonh.moviedbchallenge.upcomingmovies.domain.model.DomainUpcomingMovies
import io.reactivex.Single

interface UpcomingMoviesRepository {

    fun getUpcomingMovies(page: Int): Single<DomainUpcomingMovies>
}