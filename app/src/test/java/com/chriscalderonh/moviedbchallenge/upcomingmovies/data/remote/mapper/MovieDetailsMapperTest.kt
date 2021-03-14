package com.chriscalderonh.moviedbchallenge.upcomingmovies.data.remote.mapper

import com.chriscalderonh.moviedbchallenge.upcomingmovies.UpcomingMoviesFactory.generateMovieDetails
import junit.framework.Assert.assertEquals
import org.junit.Test

class MovieDetailsMapperTest {
    val mapper = MovieDetailsMapper()

    @Test
    fun `given MovieDetails, when fromRemoteToDomainThen DomainMovieDetails`() {
        val movieDetails = generateMovieDetails()
        val domainMovieDetails = with(mapper) { movieDetails.fromRemoteToDomain() }
        assertEquals("posterPath", movieDetails.posterPath, domainMovieDetails.posterPath)
        assertEquals("title", movieDetails.title, domainMovieDetails.title)
        assertEquals("tagline", movieDetails.tagline, domainMovieDetails.tagline)
        assertEquals("overview", movieDetails.overview, domainMovieDetails.overview)
        assertEquals("releaseDate", movieDetails.releaseDate, domainMovieDetails.releaseDate)
        assertEquals("runtime", movieDetails.runtime, domainMovieDetails.runtime)
    }
}