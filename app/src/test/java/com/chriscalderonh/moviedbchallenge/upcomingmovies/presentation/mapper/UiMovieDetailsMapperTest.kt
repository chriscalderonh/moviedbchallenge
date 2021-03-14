package com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.mapper

import com.chriscalderonh.moviedbchallenge.upcomingmovies.UpcomingMoviesFactory.generateDomainMovieDetails
import junit.framework.Assert.assertEquals
import org.junit.Test

class UiMovieDetailsMapperTest {

    val mapper = UiMovieDetailsMapper()

    @Test
    fun `given DomainMovieDetails, when fromDomainToUi, then UiMovieDetails`() {
        val domainMovieDetails = generateDomainMovieDetails()
        val uiMovieDetails = with(mapper) { domainMovieDetails.fromDomainToUi() }
        assertEquals("posterPath", domainMovieDetails.posterPath, uiMovieDetails.posterPath)
        assertEquals("title", domainMovieDetails.title, uiMovieDetails.title)
        assertEquals("tagline", domainMovieDetails.tagline, uiMovieDetails.tagline)
        assertEquals("overview", domainMovieDetails.overview, uiMovieDetails.overview)
        assertEquals("releaseDate", domainMovieDetails.releaseDate, uiMovieDetails.releaseDate)
        assertEquals("runtime", domainMovieDetails.runtime, uiMovieDetails.runtime)
    }
}