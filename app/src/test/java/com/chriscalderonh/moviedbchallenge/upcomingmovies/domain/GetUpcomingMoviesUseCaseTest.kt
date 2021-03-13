package com.chriscalderonh.moviedbchallenge.upcomingmovies.domain

import com.chriscalderonh.moviedbchallenge.RandomValuesFactory.generateInt
import com.chriscalderonh.moviedbchallenge.upcomingmovies.UpcomingMoviesFactory.generateDomainUpcomingMovies
import com.chriscalderonh.moviedbchallenge.upcomingmovies.domain.model.DomainUpcomingMovies
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Test

class GetUpcomingMoviesUseCaseTest {

    private val repository = mock<UpcomingMoviesRepository>()
    private val useCase = GetUpcomingMoviesUseCase(repository)

    @Test
    fun `given response, when execute, then returns data`() {
        val page = generateInt()
        val domainUpcomingMovies = generateDomainUpcomingMovies()
        stubGetUpcomingMovies(page, Single.just(domainUpcomingMovies))

        val testObserver = useCase.execute(page).test()

        testObserver.assertValue(domainUpcomingMovies)
    }

    private fun stubGetUpcomingMovies(page: Int, response: Single<DomainUpcomingMovies>) {
        whenever(repository.getUpcomingMovies(page)).thenReturn(response)
    }
}
