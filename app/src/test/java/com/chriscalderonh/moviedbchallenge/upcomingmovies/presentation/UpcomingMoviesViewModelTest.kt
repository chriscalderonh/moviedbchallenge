package com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation

import com.chriscalderonh.moviedbchallenge.RandomValuesFactory.generateInt
import com.chriscalderonh.moviedbchallenge.common.presentation.FakeExecutionThread
import com.chriscalderonh.moviedbchallenge.upcomingmovies.UpcomingMoviesFactory.generateDomainUpcomingMovies
import com.chriscalderonh.moviedbchallenge.upcomingmovies.UpcomingMoviesFactory.generateUiUpcomingMovies
import com.chriscalderonh.moviedbchallenge.upcomingmovies.domain.GetUpcomingMoviesUseCase
import com.chriscalderonh.moviedbchallenge.upcomingmovies.domain.model.DomainUpcomingMovies
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.mapper.UiUpcomingMoviesMapper
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.model.UiUpcomingMovies
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.processor.UpcomingMoviesProcessor
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.uistate.UpcomingMoviesUiState
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.userintent.UpcomingMoviesUserIntent
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.After
import org.junit.Before
import org.junit.Test

class UpcomingMoviesViewModelTest {

    private val fakeExecutionThread = FakeExecutionThread()
    private val getUpcomingMoviesUseCase = mock<GetUpcomingMoviesUseCase>()
    private val processor = UpcomingMoviesProcessor(getUpcomingMoviesUseCase, fakeExecutionThread)
    private val uiUpcomingMoviesMapper = mock<UiUpcomingMoviesMapper>()
    private val viewModel = UpcomingMoviesViewModel(processor, uiUpcomingMoviesMapper)
    private lateinit var testObserver: TestObserver<UpcomingMoviesUiState>

    @Before
    fun setUp() {
        testObserver = viewModel.uiStates().test()
    }

    @After
    fun tearDown() {
        testObserver.dispose()
    }

    @Test
    fun `given movie list from GetUpcomingMoviesUseCase, when DefaultUserIntent then SuccessGettingMoviesList`() {
        val domainUpcomingMovies = generateDomainUpcomingMovies()
        val uiUpcomingMovies = generateUiUpcomingMovies()
        val page = generateInt()
        stubUiUpcomingMoviesMapper(domainUpcomingMovies, uiUpcomingMovies)
        stubGetUpcomingMoviesUseCase(page, Single.just(domainUpcomingMovies))

        viewModel.processUserIntents(
            Observable.just(UpcomingMoviesUserIntent.InitialUserIntent(page)))

        testObserver.assertValueAt(0) { uiState -> uiState is UpcomingMoviesUiState.Default }
        testObserver.assertValueAt(1) { uiState -> uiState is UpcomingMoviesUiState.Loading }
        testObserver.assertValueAt(2) { uiState -> uiState is UpcomingMoviesUiState.SuccessGettingMoviesList }
        testObserver.assertValueAt(2) { uiState ->
            uiState.upcomingMovies == with(uiUpcomingMoviesMapper) {
                domainUpcomingMovies.fromDomainToUi()
            }
        }
    }

    @Test
    fun `given error from GetUpcomingMoviesUseCase, when DefaultUserIntent then ErrorGettingMoviesList`() {
        val page = generateInt()
        stubGetUpcomingMoviesUseCase(page, Single.error(Throwable()))

        viewModel.processUserIntents(
            Observable.just(UpcomingMoviesUserIntent.InitialUserIntent(page)))

        testObserver.assertValueAt(0) { uiState -> uiState is UpcomingMoviesUiState.Default }
        testObserver.assertValueAt(1) { uiState -> uiState is UpcomingMoviesUiState.Loading }
        testObserver.assertValueAt(2) { uiState -> uiState is UpcomingMoviesUiState.ErrorGettingMoviesList }
    }

    private fun stubGetUpcomingMoviesUseCase(page: Int, response: Single<DomainUpcomingMovies>) {
        whenever(getUpcomingMoviesUseCase.execute(page)).thenReturn(response)
    }

    private fun stubUiUpcomingMoviesMapper(domain: DomainUpcomingMovies, ui: UiUpcomingMovies) {
        whenever(with(uiUpcomingMoviesMapper) { domain.fromDomainToUi() }).thenReturn(ui)
    }
}