package com.chriscalderonh.moviedbchallenge.upcomingmovies.data

import com.chriscalderonh.moviedbchallenge.RandomValuesFactory.generateInt
import com.chriscalderonh.moviedbchallenge.upcomingmovies.UpcomingMoviesFactory.generateDomainUpcomingMovies
import com.chriscalderonh.moviedbchallenge.upcomingmovies.UpcomingMoviesFactory.generateUpcomingMovies
import com.chriscalderonh.moviedbchallenge.upcomingmovies.data.remote.mapper.UpcomingMoviesMapper
import com.chriscalderonh.moviedbchallenge.upcomingmovies.data.remote.model.UpcomingMovies
import com.chriscalderonh.moviedbchallenge.upcomingmovies.data.repository.UpcomingMoviesRemote
import com.chriscalderonh.moviedbchallenge.upcomingmovies.data.source.UpcomingMoviesSourceFactory
import com.chriscalderonh.moviedbchallenge.upcomingmovies.domain.model.DomainUpcomingMovies
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class UpcomingMoviesDataRepositoryTest {
    private val remote = mock<UpcomingMoviesRemote>()
    private val factory = mock<UpcomingMoviesSourceFactory>()
    private val mapper = mock<UpcomingMoviesMapper>()
    private val dataRepository = UpcomingMoviesDataRepository(factory, mapper)

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

    private fun stubGetUpcomingMovies(page: Int, response: Single<UpcomingMovies>) {
        whenever(remote.getUpcomingMovies(page)).thenReturn(response)
    }

    private fun stubUpcomingMoviesMapper(remote: UpcomingMovies, domain: DomainUpcomingMovies) {
        whenever(with(mapper) { remote.fromRemoteToDomain() }).thenReturn(domain)
    }

}