package com.chriscalderonh.moviedbchallenge.common.presentation

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class FakeExecutionThread : ExecutionThread {

    override fun schedulerForObserving(): Scheduler = Schedulers.trampoline()

    override fun schedulerForSubscribing(): Scheduler = Schedulers.trampoline()

}