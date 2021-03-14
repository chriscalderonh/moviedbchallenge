package com.chriscalderonh.moviedbchallenge.upcomingmovies.data

import com.chriscalderonh.moviedbchallenge.RandomValuesFactory.generateInt
import com.chriscalderonh.moviedbchallenge.RandomValuesFactory.generateString
import com.chriscalderonh.moviedbchallenge.upcomingmovies.UpcomingMoviesFactory.generateDomainMovieDetails
import com.chriscalderonh.moviedbchallenge.upcomingmovies.UpcomingMoviesFactory.generateDomainUpcomingMovies
import com.chriscalderonh.moviedbchallenge.upcomingmovies.UpcomingMoviesFactory.generateMovieDetails
import com.chriscalderonh.moviedbchallenge.upcomingmovies.UpcomingMoviesFactory.generateUpcomingMovies
import com.chriscalderonh.moviedbchallenge.upcomingmovies.data.remote.mapper.MovieDetailsMapper
import com.chriscalderonh.moviedbchallenge.upcomingmovies.data.remote.mapper.UpcomingMoviesMapper
import com.chriscalderonh.moviedbchallenge.upcomingmovies.data.remote.model.MovieDetails
import com.chriscalderonh.moviedbchallenge.upcomingmovies.data.remote.model.UpcomingMovies
import com.chriscalderonh.moviedbchallenge.upcomingmovies.data.repository.UpcomingMoviesRemote
import com.chriscalderonh.moviedbchallenge.upcomingmovies.data.source.UpcomingMoviesSourceFactory
import com.chriscalderonh.moviedbchallenge.upcomingmovies.domain.model.DomainMovieDetails
import com.chriscalderonh.moviedbchallenge.upcomingmovies.domain.model.DomainUpcomingMovies
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class UpcomingMoviesDataRepositoryTest {
    private val remote = mock<UpcomingMoviesRemote>()
    private val factory = mock<UpcomingMoviesSourceFactory>()
    private val upcomingMoviesMapper = mock<UpcomingMoviesMapper>()
    private val movieDetailsMapper = mock<MovieDetailsMapper>()
    private val dataRepository =
        UpcomingMoviesDataRepository(factory, upcomingMoviesMapper, movieDetailsMapper)

    @Before
    fun setUp() {
        stubFactoryGetRemote()
    }

    private fun stubFactoryGetRemote() {
        whenever(factory.getRemote()).thenReturn(remote)
    }

    @Test
    fun `given page, when getUpcomingMovies, then returns data`() {
        val page = generateInt()
        val upcomingMovies = generateUpcomingMovies()
        val domainUpcomingMovies = generateDomainUpcomingMovies()
        stubUpcomingMoviesMapper(upcomingMovies, domainUpcomingMovies)
        stubGetUpcomingMovies(page, Single.just(upcomingMovies))

        val testObserver = dataRepository.getUpcomingMovies(page).test()

        testObserver.assertValue(domainUpcomingMovies)
    }

    @Test
    fun `given movieId, when getMovieDetails, then returns data`() {
        val movieId = generateString()
        val movieDetails = generateMovieDetails()
        val domainMovieDetails = generateDomainMovieDetails()
        stubMovieDetailsMapper(movieDetails, domainMovieDetails)
        stubGetMovieDetails(movieId, Single.just(movieDetails))

        val testObserver = dataRepository.getMovieDetails(movieId).test()

        testObserver.assertValue(domainMovieDetails)
    }

    private fun stubGetUpcomingMovies(page: Int, response: Single<UpcomingMovies>) {
        whenever(remote.getUpcomingMovies(page)).thenReturn(response)
    }

    private fun stubUpcomingMoviesMapper(remote: UpcomingMovies, domain: DomainUpcomingMovies) {
        whenever(with(upcomingMoviesMapper) { remote.fromRemoteToDomain() }).thenReturn(domain)
    }

    private fun stubGetMovieDetails(movieId: String, response: Single<MovieDetails>) {
        whenever(remote.getMovieDetails(movieId)).thenReturn(response)
    }

    private fun stubMovieDetailsMapper(remote: MovieDetails, domain: DomainMovieDetails) {
        whenever(with(movieDetailsMapper) { remote.fromRemoteToDomain() }).thenReturn(domain)
    }
}