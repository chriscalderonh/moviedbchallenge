package com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.processor

import com.chriscalderonh.moviedbchallenge.common.presentation.ExecutionThread
import com.chriscalderonh.moviedbchallenge.upcomingmovies.domain.GetMovieDetailsUseCase
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.action.MovieDetailsAction
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.action.MovieDetailsAction.GetMovieDetailsAction
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.result.MovieDetailsResult
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.result.MovieDetailsResult.GetMovieDetailsResult
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class MovieDetailsProcessor @Inject constructor(
        private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
        private val executionThread: ExecutionThread
) {

    var actionProcessor: ObservableTransformer<MovieDetailsAction, MovieDetailsResult>
        private set

    init {
        actionProcessor = ObservableTransformer { observableAction ->
            observableAction.publish { action ->
                action.ofType(GetMovieDetailsAction::class.java)
                        .compose(getMovieDetailsProcessor)
                        .cast(MovieDetailsResult::class.java)
            }
        }
    }

    private val getMovieDetailsProcessor =
            ObservableTransformer<GetMovieDetailsAction, GetMovieDetailsResult> { observableAction ->
                observableAction.switchMap { action ->
                    getMovieDetailsUseCase.execute(action.movieId)
                            .toObservable()
                            .map { result ->
                                GetMovieDetailsResult.Success(result)
                            }
                            .cast(GetMovieDetailsResult::class.java)
                            .onErrorReturn(GetMovieDetailsResult::Error)
                            .subscribeOn(executionThread.schedulerForSubscribing())
                            .observeOn(executionThread.schedulerForObserving())
                            .startWith(GetMovieDetailsResult.InProgress)
                }
            }
}