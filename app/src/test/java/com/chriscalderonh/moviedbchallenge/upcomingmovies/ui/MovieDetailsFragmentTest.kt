package com.chriscalderonh.moviedbchallenge.upcomingmovies.ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chriscalderonh.moviedbchallenge.R
import com.chriscalderonh.moviedbchallenge.RandomValuesFactory.generateString
import com.chriscalderonh.moviedbchallenge.common.presentation.ViewModelUtil
import com.chriscalderonh.moviedbchallenge.upcomingmovies.UpcomingMoviesFactory.generateUiMovieDetails
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.MovieDetailsViewModel
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.uistate.MovieDetailsUiState
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(AndroidJUnit4::class)
class MovieDetailsFragmentTest {

    private lateinit var fragmentScenario: FragmentScenario<TestMovieDetailsFragment>

    @Before
    fun setUp() {
        val fragmentArgs = Bundle().apply {
            putString("movieId", generateString())
        }
        fragmentScenario = launchFragmentInContainer(fragmentArgs, R.style.Theme_AppCompat)
    }

    @Test
    fun `given loading state, when post value, then show loading component`() {
        val state = MovieDetailsUiState.Loading
        sendStateToFragment(state)

        Espresso.onView(withId(R.id.lvMovieDetailsLoading))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun `given error getting movie details state, when post value, then show error component`() {
        val error = generateString()
        val state = MovieDetailsUiState.ErrorGettingMovieDetails(error)
        sendStateToFragment(state)

        Espresso.onView(withId(R.id.evMovieDetailsError))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun `given success getting details, when post value, then show details and hide other components`() {
        val movieDetails = generateUiMovieDetails()
        val state = MovieDetailsUiState.SuccessGettingMovieDetails(movieDetails)
        sendStateToFragment(state)

        Espresso.onView(withId(R.id.lvMovieDetailsLoading))
            .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.isDisplayed())))
        Espresso.onView(withId(R.id.evMovieDetailsError))
            .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.isDisplayed())))

        Espresso.onView(withId(R.id.tvMovieTitle))
            .check(ViewAssertions.matches(withText(movieDetails.title)))
        Espresso.onView(withId(R.id.tvMovieTagline))
            .check(ViewAssertions.matches(withText(movieDetails.tagline)))
        Espresso.onView(withId(R.id.tvDescription))
            .check(ViewAssertions.matches(withText(movieDetails.overview)))
        Espresso.onView(withId(R.id.tvReleaseDate))
            .check(ViewAssertions.matches(withText(movieDetails.releaseDate)))
        Espresso.onView(withId(R.id.tvRuntime))
            .check(ViewAssertions.matches(withText(movieDetails.runtime.toString())))
    }

    private fun sendStateToFragment(state: MovieDetailsUiState) {
        fragmentScenario.onFragment {
            it.liveData.postValue(state)
        }
    }

    class TestMovieDetailsFragment : MovieDetailsFragment() {

        private val viewModelFake = mock<MovieDetailsViewModel>()
        val liveData: MutableLiveData<MovieDetailsUiState> = MutableLiveData()

        override fun setUpInjection() {
            stubViewModel()
            this.viewModelFactory = ViewModelUtil.createFor(viewModelFake)
        }

        private fun stubViewModel() {
            whenever(viewModelFake.liveData()).thenReturn(liveData)
        }
    }
}