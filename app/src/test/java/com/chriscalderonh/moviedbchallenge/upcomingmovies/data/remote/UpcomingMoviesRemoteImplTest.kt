package com.chriscalderonh.moviedbchallenge.upcomingmovies.data.remote

import com.chriscalderonh.moviedbchallenge.RandomValuesFactory.generateInt
import com.chriscalderonh.moviedbchallenge.upcomingmovies.UpcomingMoviesFactory.generateUpcomingMovies
import com.chriscalderonh.moviedbchallenge.upcomingmovies.data.remote.Constants.API_KEY
import com.chriscalderonh.moviedbchallenge.upcomingmovies.data.remote.model.UpcomingMovies
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Test

class UpcomingMoviesRemoteImplTest {
    private val restApi = mock<UpcomingMoviesRestApi>()
    private val upcomingMoviesRemoteImpl = UpcomingMoviesRemoteImpl(restApi)

    @Test
    fun `given page, when getUpcomingMovies, then returns data`() {
        val apiKey = API_KEY
        val page = generateInt()
        val upcomingMovies = generateUpcomingMovies()
        stubRestApiGetUpcomingMovies(apiKey, page, Single.just(upcomingMovies))

        val testObserver = upcomingMoviesRemoteImpl.getUpcomingMovies(page).test()

        testObserver.assertValue(upcomingMovies)
    }

    private fun stubRestApiGetUpcomingMovies(apiKey: String, page: Int, response: Single<UpcomingMovies>) {
        whenever(restApi.getUpcomingMoviesList(apiKey, page)).thenReturn(response)
    }
}