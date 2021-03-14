package com.chriscalderonh.moviedbchallenge.upcomingmovies.data.remote

import com.chriscalderonh.moviedbchallenge.upcomingmovies.data.remote.model.MovieDetails
import com.chriscalderonh.moviedbchallenge.upcomingmovies.data.remote.model.UpcomingMovies
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UpcomingMoviesRestApi {

    @GET("movie/upcoming?language=es&sort_by=popularity.desc")
    fun getUpcomingMoviesList(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Single<UpcomingMovies>

    @GET("movie/{movie_id}?language=es")
    fun getMovieDetails(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String
    ): Single<MovieDetails>
}