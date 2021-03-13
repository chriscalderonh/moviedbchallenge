package com.chriscalderonh.moviedbchallenge.upcomingmovies.data

import com.chriscalderonh.moviedbchallenge.upcomingmovies.data.remote.mapper.MovieDetailsMapper
import com.chriscalderonh.moviedbchallenge.upcomingmovies.data.remote.mapper.UpcomingMoviesMapper
import com.chriscalderonh.moviedbchallenge.upcomingmovies.data.source.UpcomingMoviesSourceFactory
import com.chriscalderonh.moviedbchallenge.upcomingmovies.domain.UpcomingMoviesRepository
import com.chriscalderonh.moviedbchallenge.upcomingmovies.domain.model.DomainMovieDetails
import com.chriscalderonh.moviedbchallenge.upcomingmovies.domain.model.DomainUpcomingMovies
import io.reactivex.Single
import javax.inject.Inject

class UpcomingMoviesDataRepository @Inject constructor(
    private val factory: UpcomingMoviesSourceFactory,
    private val upcomingMoviesMapper: UpcomingMoviesMapper,
    private val movieDetailsMapper: MovieDetailsMapper
)  : UpcomingMoviesRepository{

    override fun getUpcomingMovies(page: Int): Single<DomainUpcomingMovies> = factory
        .getRemote()
        .getUpcomingMovies(page)
        .map { upcomingMovies ->
            with(upcomingMoviesMapper) {
                upcomingMovies.fromRemoteToDomain()
            }
        }

    override fun getMovieDetails(movieId: String): Single<DomainMovieDetails> = factory
        .getRemote()
        .getMovieDetails(movieId)
        .map { movieDetails ->
            with(movieDetailsMapper) {
                movieDetails.fromRemoteToDomain()
            }
        }
}