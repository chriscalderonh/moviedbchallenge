package com.chriscalderonh.moviedbchallenge.upcomingmovies.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.chriscalderonh.moviedbchallenge.common.ui.GlideApp
import com.chriscalderonh.moviedbchallenge.databinding.ViewResultItemBinding
import com.chriscalderonh.moviedbchallenge.upcomingmovies.presentation.model.UiResult
import com.chriscalderonh.moviedbchallenge.upcomingmovies.ui.Constants.GLIDE_URL_FOR_SMALL_IMAGES
import javax.inject.Inject

class UpcomingMoviesAdapter @Inject constructor() :
    RecyclerView.Adapter<UpcomingMoviesAdapter.ViewHolder>() {

    var movies: List<UiResult> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ViewResultItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = movies[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = movies.size

    inner class ViewHolder(private val binding: ViewResultItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UiResult) {
            binding.apply {
                tvMovieName.text = item.title
                tvReleaseDate.text = item.releaseDate
                val posterUrl = GLIDE_URL_FOR_SMALL_IMAGES + item.posterPath
                GlideApp.with(ivPosterArt.context).load(posterUrl).into(ivPosterArt)
                clItem.setOnClickListener { view ->
                    val directions = UpcomingMoviesFragmentDirections.actionUpcomingMoviesFragmentToMovieDetailsFragment(item.id.toString())
                    view.findNavController().navigate(directions)
                }
            }
        }
    }
}