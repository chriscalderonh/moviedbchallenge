package com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chriscalderonh.moviedbchallenge.common.presentation.MviPresentation
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.action.MovieDetailsAction
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.action.MovieDetailsAction.GetMovieDetailsAction
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.mapper.UiMovieDetailsMapper
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.processor.MovieDetailsProcessor
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.result.MovieDetailsResult
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.uistate.MovieDetailsUiState
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.userintent.MovieDetailsUserIntent
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
        private val movieDetailsProcessor: MovieDetailsProcessor,
        private val uiMovieDetailsMapper: UiMovieDetailsMapper
) : ViewModel(), MviPresentation<MovieDetailsUserIntent, MovieDetailsUiState> {

    private val userIntentSubject: PublishSubject<MovieDetailsUserIntent> =
            PublishSubject.create()
    private val disposable = CompositeDisposable()
    private val liveDataUiState = MutableLiveData<MovieDetailsUiState>()
    private val uiStatesObservable: Observable<MovieDetailsUiState> = compose()

    init {
        disposable += uiStatesObservable.subscribe(liveDataUiState::setValue) { }
    }

    private fun compose(): Observable<MovieDetailsUiState> = userIntentSubject
            .map { userIntent -> transformUserIntentsIntoActions(userIntent) }
            .compose(movieDetailsProcessor.actionProcessor)
            .scan(MovieDetailsUiState.Default, reducer)

    private val reducer: BiFunction<MovieDetailsUiState, MovieDetailsResult, MovieDetailsUiState>
        get() = BiFunction { _: MovieDetailsUiState, result: MovieDetailsResult ->
            when (result) {
                is MovieDetailsResult.GetMovieDetailsResult -> when (result) {
                    MovieDetailsResult.GetMovieDetailsResult.InProgress -> MovieDetailsUiState.Loading
                    is MovieDetailsResult.GetMovieDetailsResult.Success ->
                        MovieDetailsUiState.SuccessGettingMovieDetails(
                                with(uiMovieDetailsMapper) { result.domainMovieDetails.fromDomainToUi() })
                    is MovieDetailsResult.GetMovieDetailsResult.Error ->
                        MovieDetailsUiState.ErrorGettingMovieDetails(result.error.message)
                }
            }
        }

    private fun transformUserIntentsIntoActions(userIntent: MovieDetailsUserIntent): MovieDetailsAction =
            when (userIntent) {
                is MovieDetailsUserIntent.InitialUserIntent ->
                    GetMovieDetailsAction(userIntent.movieId)
            }


    override fun processUserIntents(userIntents: Observable<MovieDetailsUserIntent>) {
        userIntents.subscribe(userIntentSubject)
    }

    override fun uiStates(): Observable<MovieDetailsUiState> = uiStatesObservable

    fun liveData(): LiveData<MovieDetailsUiState> = liveDataUiState

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }
}