package com.chriscalderonh.moviedbchallenge.upcomingmovies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.chriscalderonh.moviedbchallenge.common.presentation.MviUi
import com.chriscalderonh.moviedbchallenge.common.ui.GlideApp
import com.chriscalderonh.moviedbchallenge.databinding.FragmentMovieDetailsBinding
import com.chriscalderonh.moviedbchallenge.upcomingmovies.di.DaggerUpcomingMoviesComponent
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.MovieDetailsViewModel
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.model.UiMovieDetails
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.uistate.MovieDetailsUiState
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.uistate.MovieDetailsUiState.ErrorGettingMovieDetails
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.uistate.MovieDetailsUiState.SuccessGettingMovieDetails
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.uistate.MovieDetailsUiState.Default
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.uistate.MovieDetailsUiState.Loading
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.userintent.MovieDetailsUserIntent
import com.chriscalderonh.moviedbchallenge.upcomingmovies.ui.Constants.GLIDE_URL_FOR_MEDIUM_IMAGES
import io.reactivex.Observable
import javax.inject.Inject

open class MovieDetailsFragment : Fragment(),
        MviUi<MovieDetailsUserIntent, MovieDetailsUiState> {

    lateinit var binding: FragmentMovieDetailsBinding
    private val args: MovieDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val movieDetailsViewModel: MovieDetailsViewModel? by lazy {
        ViewModelProvider(this, viewModelFactory)
                .get(MovieDetailsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpInjection()
        subscribeUiStatesAndProcessUserIntents()
    }

    protected open fun setUpInjection() {
        DaggerUpcomingMoviesComponent.builder()
                .build()
                .inject(this)
    }

    private fun subscribeUiStatesAndProcessUserIntents() {
        movieDetailsViewModel?.processUserIntents(userIntents())
        observeUiStates()
    }

    private fun observeUiStates() {
        movieDetailsViewModel?.liveData()?.observe(this) { uiState ->
            renderUiStates(uiState)
        }
    }

    override fun renderUiStates(uiState: MovieDetailsUiState) {
        when (uiState) {
            is SuccessGettingMovieDetails -> setScreenForShowMovieDetails(uiState.uiMovieDetails)
            is ErrorGettingMovieDetails -> setScreenForError(uiState.error)
            Loading -> setScreenForLoading(uiState.isLoading)
        }
    }

    private fun setScreenForShowMovieDetails(details: UiMovieDetails) {
        setScreenForLoading(false)
        setScreenForError(null)
        binding.apply {
            context?.let {
                val posterUrl = GLIDE_URL_FOR_MEDIUM_IMAGES + details.posterPath
                GlideApp.with(it).load(posterUrl).into(ivPosterArt)
            }
            uiMovieDetails = details
        }
    }

    private fun setScreenForLoading(loading: Boolean) {
        if (loading) {
            binding.lvMovieDetailsLoading.visibility = View.VISIBLE
        } else {
            binding.lvMovieDetailsLoading.visibility = View.GONE
        }
    }

    private fun setScreenForError(error: String?) {
        error?.let {
            setScreenForLoading(false)
            binding.evMovieDetailsError.visibility = View.VISIBLE
        } ?: run {
            binding.evMovieDetailsError.visibility = View.GONE
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun userIntents(): Observable<MovieDetailsUserIntent> = movieDetailsUserIntent()

    private fun movieDetailsUserIntent(): Observable<MovieDetailsUserIntent> = Observable.just(
            MovieDetailsUserIntent.InitialUserIntent(args.movieId)
    )
}