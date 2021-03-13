package com.chriscalderonh.moviedbchallenge.upcomingmovies.data.source

import com.chriscalderonh.moviedbchallenge.upcomingmovies.data.repository.UpcomingMoviesRemote
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert.assertNotNull
import org.junit.Test

class UpcomingMoviesSourceFactoryTest {
    private val remote = mock<UpcomingMoviesRemote>()
    private val factory = UpcomingMoviesSourceFactory(remote)

    @Test
    fun `when getRemote, then not null`() {
        assertNotNull(factory.getRemote())
    }
}