package com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.processor

import com.chriscalderonh.moviedbchallenge.common.presentation.ExecutionThread
import com.chriscalderonh.moviedbchallenge.upcomingmovies.domain.GetUpcomingMoviesUseCase
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.action.UpcomingMoviesAction
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.action.UpcomingMoviesAction.MovieListAction
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.result.UpcomingMoviesResult
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.result.UpcomingMoviesResult.MovieListResult
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class UpcomingMoviesProcessor @Inject constructor(
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val executionThread: ExecutionThread
) {

    var actionProcessor: ObservableTransformer<UpcomingMoviesAction, UpcomingMoviesResult>
        private set

    init {
        actionProcessor = ObservableTransformer { observableAction ->
            observableAction.publish { action ->
                action.ofType(MovieListAction::class.java)
                    .compose(getUpcomingMoviesProcessor)
                    .cast(UpcomingMoviesResult::class.java)
            }
        }
    }

    private val getUpcomingMoviesProcessor =
        ObservableTransformer<MovieListAction, MovieListResult> { observableAction ->
            observableAction.switchMap { action ->
                getUpcomingMoviesUseCase.execute(action.page)
                    .toObservable()
                    .map { result ->
                        MovieListResult.Success(result)
                    }
                    .cast(MovieListResult::class.java)
                    .onErrorReturn(MovieListResult::Error)
                    .subscribeOn(executionThread.schedulerForSubscribing())
                    .observeOn(executionThread.schedulerForObserving())
                    .startWith(MovieListResult.InProgress)
            }
        }
}