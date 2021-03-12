package com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chriscalderonh.moviedbchallenge.common.presentation.MviPresentation
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.action.UpcomingMoviesAction
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.action.UpcomingMoviesAction.MovieListAction
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.mapper.UiUpcomingMoviesMapper
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.processor.UpcomingMoviesProcessor
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.result.UpcomingMoviesResult
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.result.UpcomingMoviesResult.MovieListResult
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.uistate.UpcomingMoviesUiState
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.userintent.UpcomingMoviesUserIntent
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class UpcomingMoviesViewModel @Inject constructor(
    private val upcomingMoviesProcessor: UpcomingMoviesProcessor,
    private val uiUpcomingMoviesMapper: UiUpcomingMoviesMapper
) : ViewModel(), MviPresentation<UpcomingMoviesUserIntent, UpcomingMoviesUiState> {

    private val userIntentSubject: PublishSubject<UpcomingMoviesUserIntent> =
        PublishSubject.create()
    private val disposable = CompositeDisposable()
    private val liveDataUiState = MutableLiveData<UpcomingMoviesUiState>()
    private val uiStatesObservable: Observable<UpcomingMoviesUiState> = compose()

    init {
        disposable += uiStatesObservable.subscribe(liveDataUiState::setValue) { }
    }

    private fun compose(): Observable<UpcomingMoviesUiState> = userIntentSubject
        .map { userIntent -> transformUserIntentsIntoActions(userIntent) }
        .compose(upcomingMoviesProcessor.actionProcessor)
        .scan(UpcomingMoviesUiState.Default, reducer)

    private val reducer: BiFunction<UpcomingMoviesUiState, UpcomingMoviesResult, UpcomingMoviesUiState>
        get() = BiFunction { _: UpcomingMoviesUiState, result: UpcomingMoviesResult ->
            when (result) {
                is MovieListResult -> when (result) {
                    MovieListResult.InProgress -> UpcomingMoviesUiState.Loading
                    is MovieListResult.Success ->
                        UpcomingMoviesUiState.SuccessGettingMoviesList(
                            with(uiUpcomingMoviesMapper) { result.domainUpcomingMovies.fromDomainToUi() })
                    is MovieListResult.Error ->
                        UpcomingMoviesUiState.ErrorGettingMoviesList(result.error.message)
                }
            }
        }

    private fun transformUserIntentsIntoActions(userIntent: UpcomingMoviesUserIntent): UpcomingMoviesAction =
        when (userIntent) {
            is UpcomingMoviesUserIntent.InitialUserIntent ->
                MovieListAction(userIntent.page)
        }


    override fun processUserIntents(userIntents: Observable<UpcomingMoviesUserIntent>) {
        userIntents.subscribe(userIntentSubject)
    }

    override fun uiStates(): Observable<UpcomingMoviesUiState> = uiStatesObservable

    fun liveData(): LiveData<UpcomingMoviesUiState> = liveDataUiState

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}