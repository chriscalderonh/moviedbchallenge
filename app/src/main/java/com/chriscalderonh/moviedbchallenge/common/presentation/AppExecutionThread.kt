package com.chriscalderonh.moviedbchallenge.common.presentation

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AppExecutionThread @Inject constructor() : ExecutionThread {

    override fun schedulerForObserving(): Scheduler = AndroidSchedulers.mainThread()

    override fun schedulerForSubscribing(): Scheduler = Schedulers.io()

}