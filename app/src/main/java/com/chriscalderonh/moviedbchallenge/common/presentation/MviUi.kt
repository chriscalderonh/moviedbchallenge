package com.chriscalderonh.moviedbchallenge.common.presentation

import io.reactivex.Observable

interface MviUi<TUserIntent: MviUserIntent, in TUiState: MviUiState> {

    fun userIntents(): Observable<TUserIntent>

    fun renderUiStates(uiState: TUiState)

}