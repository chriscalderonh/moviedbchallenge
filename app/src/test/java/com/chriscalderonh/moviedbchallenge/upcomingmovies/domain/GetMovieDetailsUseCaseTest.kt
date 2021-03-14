package com.chriscalderonh.moviedbchallenge.upcomingmovies.domain

import com.chriscalderonh.moviedbchallenge.RandomValuesFactory.generateString
import com.chriscalderonh.moviedbchallenge.upcomingmovies.UpcomingMoviesFactory.generateDomainMovieDetails
import com.chriscalderonh.moviedbchallenge.upcomingmovies.domain.model.DomainMovieDetails
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Test

class GetMovieDetailsUseCaseTest {

    private val repository = mock<UpcomingMoviesRepository>()
    private val useCase = GetMovieDetailsUseCase(repository)

    @Test
    fun `given response, when execute, then returns data`() {
        val movieId = generateString()
        val domainMovieDetails = generateDomainMovieDetails()
        stubGetMovieDetails(movieId, Single.just(domainMovieDetails))

        val testObserver = useCase.execute(movieId).test()

        testObserver.assertValue(domainMovieDetails)
    }

    private fun stubGetMovieDetails(movieId: String, response: Single<DomainMovieDetails>) {
        whenever(repository.getMovieDetails(movieId)).thenReturn(response)
    }
}