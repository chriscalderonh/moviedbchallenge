package com.chriscalderonh.moviedbchallenge.upcomingmovies.data.remote.mapper

import com.chriscalderonh.moviedbchallenge.upcomingmovies.UpcomingMoviesFactory.generateUpcomingMovies
import junit.framework.Assert.assertEquals
import org.junit.Test

class UpcomingMoviesMapperTest {
    private val upcomingMoviesMapper = UpcomingMoviesMapper()

    @Test
    fun `given UpcomingMovies, when fromRemoteToDomain, then DomainUpcomingMovies`() {

        val upcomingMovies = generateUpcomingMovies()

        val domainUpcomingMovies = with(upcomingMoviesMapper) {
            upcomingMovies.fromRemoteToDomain()
        }

        assertEquals("page", upcomingMovies.page, domainUpcomingMovies.page)
        upcomingMovies.results?.forEachIndexed { index, result ->
            assertEquals("id", result.id, domainUpcomingMovies.results?.get(index)?.id)
            assertEquals(
                "posterPath",
                result.posterPath,
                domainUpcomingMovies.results?.get(index)?.posterPath
            )
            assertEquals(
                "id",
                result.releaseDate,
                domainUpcomingMovies.results?.get(index)?.releaseDate
            )
            assertEquals("id", result.title, domainUpcomingMovies.results?.get(index)?.title)
        }
        assertEquals("totalPages", upcomingMovies.totalPages, domainUpcomingMovies.totalPages)
        assertEquals("totalResults", upcomingMovies.totalResults, domainUpcomingMovies.totalResults)
    }
}