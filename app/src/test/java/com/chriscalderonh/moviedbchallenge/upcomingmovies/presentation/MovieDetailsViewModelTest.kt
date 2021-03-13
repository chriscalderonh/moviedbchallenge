package com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation

import com.chriscalderonh.moviedbchallenge.RandomValuesFactory
import com.chriscalderonh.moviedbchallenge.common.presentation.FakeExecutionThread
import com.chriscalderonh.moviedbchallenge.upcomingmovies.UpcomingMoviesFactory.generateDomainMovieDetails
import com.chriscalderonh.moviedbchallenge.upcomingmovies.UpcomingMoviesFactory.generateUiMovieDetails
import com.chriscalderonh.moviedbchallenge.upcomingmovies.domain.GetMovieDetailsUseCase
import com.chriscalderonh.moviedbchallenge.upcomingmovies.domain.model.DomainMovieDetails
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.mapper.UiMovieDetailsMapper
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.model.UiMovieDetails
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.processor.MovieDetailsProcessor
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.uistate.MovieDetailsUiState
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.userintent.MovieDetailsUserIntent
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.After
import org.junit.Before
import org.junit.Test

class MovieDetailsViewModelTest {

    private val fakeExecutionThread = FakeExecutionThread()
    private val getMovieDetailsUseCase = mock<GetMovieDetailsUseCase>()
    private val processor = MovieDetailsProcessor(getMovieDetailsUseCase, fakeExecutionThread)
    private val uiMovieDetailsMapper = mock<UiMovieDetailsMapper>()
    private val viewModel = MovieDetailsViewModel(processor, uiMovieDetailsMapper)
    private lateinit var testObserver: TestObserver<MovieDetailsUiState>

    @Before
    fun setUp() {
        testObserver = viewModel.uiStates().test()
    }

    @After
    fun tearDown() {
        testObserver.dispose()
    }

    @Test
    fun `given movie details from GetMovieDetailsUseCase, when DefaultUserIntent then SuccessGettingMovieDetails`() {
        val domainMovieDetails = generateDomainMovieDetails()
        val uiMovieDetails = generateUiMovieDetails()
        val movieId = RandomValuesFactory.generateString()
        stubUiMovieDetailsMapper(domainMovieDetails, uiMovieDetails)
        stubGetMovieDetailsUseCase(movieId, Single.just(domainMovieDetails))

        viewModel.processUserIntents(
                Observable.just(MovieDetailsUserIntent.InitialUserIntent(movieId)))

        testObserver.assertValueAt(0) { uiState -> uiState is MovieDetailsUiState.Default }
        testObserver.assertValueAt(1) { uiState -> uiState is MovieDetailsUiState.Loading }
        testObserver.assertValueAt(2) { uiState -> uiState is MovieDetailsUiState.SuccessGettingMovieDetails }
        testObserver.assertValueAt(2) { uiState ->
            uiState.movieDetails == with(uiMovieDetailsMapper) {
                domainMovieDetails.fromDomainToUi()
            }
        }
    }

    @Test
    fun `given error from GetMovieDetailsUseCase, when DefaultUserIntent then ErrorGettingMovieDetails`() {
        val movieId = RandomValuesFactory.generateString()
        stubGetMovieDetailsUseCase(movieId, Single.error(Throwable()))

        viewModel.processUserIntents(
                Observable.just(MovieDetailsUserIntent.InitialUserIntent(movieId)))

        testObserver.assertValueAt(0) { uiState -> uiState is MovieDetailsUiState.Default }
        testObserver.assertValueAt(1) { uiState -> uiState is MovieDetailsUiState.Loading }
        testObserver.assertValueAt(2) { uiState -> uiState is MovieDetailsUiState.ErrorGettingMovieDetails }
    }

    private fun stubGetMovieDetailsUseCase(movieId: String, response: Single<DomainMovieDetails>) {
        whenever(getMovieDetailsUseCase.execute(movieId)).thenReturn(response)
    }

    private fun stubUiMovieDetailsMapper(domain: DomainMovieDetails, ui: UiMovieDetails) {
        whenever(with(uiMovieDetailsMapper) { domain.fromDomainToUi() }).thenReturn(ui)
    }
}