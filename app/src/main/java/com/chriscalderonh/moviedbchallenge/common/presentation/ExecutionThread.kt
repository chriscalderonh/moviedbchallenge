package com.chriscalderonh.moviedbchallenge.common.presentation

import io.reactivex.Scheduler

interface ExecutionThread {

    fun schedulerForObserving(): Scheduler

    fun schedulerForSubscribing(): Scheduler
}