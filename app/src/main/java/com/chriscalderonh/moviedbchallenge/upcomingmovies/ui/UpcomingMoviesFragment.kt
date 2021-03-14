package com.chriscalderonh.moviedbchallenge.upcomingmovies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.chriscalderonh.moviedbchallenge.R
import com.chriscalderonh.moviedbchallenge.common.presentation.MviUi
import com.chriscalderonh.moviedbchallenge.databinding.FragmentUpcomingMoviesBinding
import com.chriscalderonh.moviedbchallenge.upcomingmovies.di.DaggerUpcomingMoviesComponent
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.UpcomingMoviesViewModel
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.model.UiUpcomingMovies
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.uistate.UpcomingMoviesUiState
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.uistate.UpcomingMoviesUiState.ErrorGettingMoviesList
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.uistate.UpcomingMoviesUiState.SuccessGettingMoviesList
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.uistate.UpcomingMoviesUiState.Default
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.uistate.UpcomingMoviesUiState.Loading
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.userintent.UpcomingMoviesUserIntent
import com.chriscalderonh.moviedbchallenge.upcomingmovies.ui.Constants.INITIAL_PAGE
import io.reactivex.Observable
import javax.inject.Inject

open class UpcomingMoviesFragment : Fragment(),
    MviUi<UpcomingMoviesUserIntent, UpcomingMoviesUiState> {

    lateinit var binding: FragmentUpcomingMoviesBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var upcomingMoviesAdapter: UpcomingMoviesAdapter

    private val upcomingMoviesViewModel: UpcomingMoviesViewModel? by lazy {
        ViewModelProvider(this, viewModelFactory)
            .get(UpcomingMoviesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupInjection()
        subscribeUiStatesAndProcessUserIntents()
    }

    protected open fun setupInjection() {
        DaggerUpcomingMoviesComponent.builder()
            .build()
            .inject(this)
    }

    private fun subscribeUiStatesAndProcessUserIntents() {
        upcomingMoviesViewModel?.processUserIntents(userIntents())
        observeUiStates()
    }

    private fun observeUiStates() {
        upcomingMoviesViewModel?.liveData()?.observe(this) { uiState ->
            renderUiStates(uiState)
        }
    }

    override fun renderUiStates(uiState: UpcomingMoviesUiState) {
        when (uiState) {
            is SuccessGettingMoviesList -> setScreenForShowMovies(uiState)
            is ErrorGettingMoviesList -> setScreenForError(uiState.error)
            is Default -> setDefaultAction()
            is Loading -> setScreenForLoading(uiState.isLoading)
        }
    }

    private fun setScreenForShowMovies(uiState: UpcomingMoviesUiState) {
        setScreenForLoading(uiState.isLoading)
        setScreenForError(uiState.error)
        uiState.upcomingMovies?.results?.let { list ->
            upcomingMoviesAdapter.movies = list
        }
    }

    private fun setScreenForError(error: String?) {
        error?.let {
            setScreenForLoading(false)
            binding.evMoviesError.visibility = View.VISIBLE
        } ?: run {
            binding.evMoviesError.visibility = View.GONE
        }
    }

    private fun setScreenForLoading(loading: Boolean) {
        if (loading) {
            binding.lvMoviesLoading.visibility = View.VISIBLE
        } else {
            binding.lvMoviesLoading.visibility = View.GONE
        }
    }

    private fun setDefaultAction() {
        TODO()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpcomingMoviesBinding.inflate(inflater, container, false)
        setupAdapter()
        return binding.root
    }

    private fun setupAdapter() {
        context?.let {
            val itemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
            AppCompatResources.getDrawable(it, R.drawable.rv_line_divider)?.let { drawable ->
                itemDecoration.setDrawable(drawable)
                binding.rvResults.addItemDecoration(itemDecoration)
            }
        }
        binding.rvResults.adapter = upcomingMoviesAdapter
    }

    override fun userIntents(): Observable<UpcomingMoviesUserIntent> = upcomingMoviesUserIntent()

    private fun upcomingMoviesUserIntent(): Observable<UpcomingMoviesUserIntent> = Observable.just(
        UpcomingMoviesUserIntent.InitialUserIntent(INITIAL_PAGE)
    )
}