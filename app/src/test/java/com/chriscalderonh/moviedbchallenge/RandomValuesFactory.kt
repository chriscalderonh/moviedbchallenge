package com.chriscalderonh.moviedbchallenge

import java.util.*
import java.util.concurrent.ThreadLocalRandom

object RandomValuesFactory {
    fun generateString(): String = UUID.randomUUID().toString()
    fun generateDouble(): Double = Math.random()
    fun generateInt(): Int = ThreadLocalRandom.current().nextInt(0, 1000 + 1)
    fun generateBoolean(): Boolean = Math.random() < 0.5
}