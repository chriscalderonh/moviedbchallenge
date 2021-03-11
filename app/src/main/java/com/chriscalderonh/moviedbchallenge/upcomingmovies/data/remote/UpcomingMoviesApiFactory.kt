package com.chriscalderonh.moviedbchallenge.upcomingmovies.data.remote

import com.chriscalderonh.moviedbchallenge.common.net.ApiClient

class UpcomingMoviesApiFactory : ApiClient<UpcomingMoviesRestApi>() {

    init {
        restAPI = UpcomingMoviesRestApi::class.java
        baseURL = "https://api.themoviedb.org/3/"
    }

    fun makeRestApi(): UpcomingMoviesRestApi = apiService
}