package com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.mapper

import com.chriscalderonh.moviedbchallenge.upcomingmovies.UpcomingMoviesFactory.generateDomainUpcomingMovies
import junit.framework.Assert.assertEquals
import org.junit.Test

class UiUpcomingMoviesMapperTest {

    private val mapper = UiUpcomingMoviesMapper()

    @Test
    fun `given DomainUpcomingMovies, when fromDomainToUi, then UiUpcomingMovies`() {
        val domainUpcomingMovies = generateDomainUpcomingMovies()

        val uiUpcomingMovies = with(mapper) {
            domainUpcomingMovies.fromDomainToUi()
        }

        assertEquals("page", domainUpcomingMovies.page, uiUpcomingMovies.page)
        domainUpcomingMovies.results?.forEachIndexed { index, result ->
            assertEquals("id", result.id, uiUpcomingMovies.results?.get(index)?.id)
            assertEquals(
                "posterPath",
                result.posterPath,
                uiUpcomingMovies.results?.get(index)?.posterPath
            )
            assertEquals(
                "releaseDate",
                result.releaseDate,
                uiUpcomingMovies.results?.get(index)?.releaseDate
            )
            assertEquals("title", result.title, uiUpcomingMovies.results?.get(index)?.title)
        }
        assertEquals("totalPages", domainUpcomingMovies.totalPages, uiUpcomingMovies.totalPages)
        assertEquals(
            "totalResults",
            domainUpcomingMovies.totalResults,
            uiUpcomingMovies.totalResults
        )
    }
}