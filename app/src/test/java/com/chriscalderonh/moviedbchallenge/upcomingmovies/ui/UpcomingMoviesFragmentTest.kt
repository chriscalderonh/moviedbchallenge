package com.chriscalderonh.moviedbchallenge.upcomingmovies.ui

import android.os.Build
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chriscalderonh.moviedbchallenge.CustomViewAction
import com.chriscalderonh.moviedbchallenge.R
import com.chriscalderonh.moviedbchallenge.RandomValuesFactory.generateString
import com.chriscalderonh.moviedbchallenge.common.presentation.ViewModelUtil
import com.chriscalderonh.moviedbchallenge.common.ui.RecyclerViewMatcher
import com.chriscalderonh.moviedbchallenge.upcomingmovies.UpcomingMoviesFactory.generateUiUpcomingMovies
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.UpcomingMoviesViewModel
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.uistate.UpcomingMoviesUiState
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(AndroidJUnit4::class)
class UpcomingMoviesFragmentTest {

    private lateinit var fragmentScenario: FragmentScenario<TestUpcomingMoviesFragment>

    @Before
    fun setUp() {
        fragmentScenario = launchFragmentInContainer()
    }

    @Test
    fun `given loading state, when post value, then show loading component`() {
        val state = UpcomingMoviesUiState.Loading
        sendStateToFragment(state)

        Espresso.onView(withId(R.id.lvMoviesLoading))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun `given error getting movie list state, when post value, then show error component`() {
        val error = generateString()
        val state = UpcomingMoviesUiState.ErrorGettingMoviesList(error)
        sendStateToFragment(state)

        Espresso.onView(withId(R.id.evMoviesError))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun `given success getting movies, when post value, then show movies and hide other components`() {
        val upcomingMovies = generateUiUpcomingMovies()
        val state = UpcomingMoviesUiState.SuccessGettingMoviesList(upcomingMovies)
        sendStateToFragment(state)

        Espresso.onView(withId(R.id.lvMoviesLoading))
            .check(ViewAssertions.matches(not(ViewMatchers.isDisplayed())))
        Espresso.onView(withId(R.id.evMoviesError))
            .check(ViewAssertions.matches(not(ViewMatchers.isDisplayed())))

        upcomingMovies.results?.forEachIndexed { index, result ->
            Espresso.onView(withId(R.id.rvResults)).perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(index)
            )

            Espresso.onView(
                RecyclerViewMatcher
                    .withRecyclerView(R.id.rvResults)
                    .atPosition(index)
            ).check(ViewAssertions.matches(ViewMatchers.hasDescendant(withText(result.title))))
        }
    }

    @Test
    fun `given success getting movies, when click on movie, then go to movie details`() {
        val navController = mock<NavController>()
        val upcomingMovies = generateUiUpcomingMovies()
        val state = UpcomingMoviesUiState.SuccessGettingMoviesList(upcomingMovies)
        fragmentScenario.onFragment {
            Navigation.setViewNavController(it.requireView(), navController)
            it.liveData.postValue(state)
        }

        Espresso.onView(withId(R.id.rvResults))
            .perform(RecyclerViewActions.actionOnItemAtPosition<UpcomingMoviesAdapter.ViewHolder>(
                0, CustomViewAction.clickChildViewWithId(R.id.clItem)))
        upcomingMovies.results?.let {
            val direction = UpcomingMoviesFragmentDirections.actionUpcomingMoviesFragmentToMovieDetailsFragment(it[0].id.toString())
            verify(navController).navigate(direction)
        }
    }

    private fun sendStateToFragment(state: UpcomingMoviesUiState) {
        fragmentScenario.onFragment {
            it.liveData.postValue(state)
        }
    }

    class TestUpcomingMoviesFragment : UpcomingMoviesFragment() {

        private val viewModelFake = mock<UpcomingMoviesViewModel>()
        val liveData: MutableLiveData<UpcomingMoviesUiState> = MutableLiveData()

        override fun setupInjection() {
            stubViewModel()
            this.viewModelFactory = ViewModelUtil.createFor(viewModelFake)
            this.upcomingMoviesAdapter = UpcomingMoviesAdapter()
        }

        private fun stubViewModel() {
            whenever(viewModelFake.liveData()).thenReturn(liveData)
        }
    }
}