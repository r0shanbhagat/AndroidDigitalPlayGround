package com.digital.playground.ui.view.fragment

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.digital.playground.R
import com.digital.playground.core.BaseFragment
import com.digital.playground.data.model.Movie
import com.digital.playground.databinding.FragmentMovieDetailBinding
import com.digital.playground.ui.adapter.MovieModel
import com.digital.playground.ui.viewmodel.DetailViewModel
import com.digital.playground.utils.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class DetailFragment(
    override val layoutId: Int = R.layout.fragment_movie_detail
) : BaseFragment<FragmentMovieDetailBinding>() {

    private val viewModel by viewModels<DetailViewModel>()

    private val safeArgs: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedElementEnterTransition = TransitionInflater
            .from(context)
            .inflateTransition(android.R.transition.move)
        postponeEnterTransition(250, TimeUnit.MILLISECONDS)

        val movie = safeArgs.movie
        updatePostAndTitle(movie)

        viewModel.getMovieDetailsData(movie.imdb)

        setupBackButton()
        setupMovieObserver()
    }

    private fun setupBackButton() {
        binding.ivBackButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    /**
     * subscribeObservers is an Observers function for mutable live data
     */
    private fun setupMovieObserver() {
        viewModel.movieResult.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is ViewState.Loading -> {
                    binding.progressBar.visible()
                }
                is ViewState.Success -> {
                    binding.progressBar.gone()
                    val movie: Movie = dataState.data as Movie
                    updateUi(movie)
                    binding.progressBar.gone()
                }
                is ViewState.Failure -> {
                    binding.progressBar.gone()
                    context?.showToast(getString(R.string.error_msg))

                }
            }
        }
    }

    private fun updatePostAndTitle(movie: MovieModel) {

        binding.ivPoster.transitionName = movie.image

        Glide
            .with(this)
            .load(movie.image)
            .centerCrop()
            .placeholder(R.drawable.image_not_found)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.ivPoster)

        binding.tvTitle.text = movie.title
        binding.tvYear.text = movie.year

        binding.ivPoster.transitionName = movie.image
        binding.tvTitle.transitionName = movie.title

    }

    private fun updateUi(movie: Movie) {

        binding.tvRating.text = resources.getString(R.string.rating, movie.imdbRating)
        binding.tvDescription.text = movie.Plot

        binding.tvDirectors.text = context?.fromHtmlWithParams(R.string.directors, movie.Director)
        binding.tvActors.text = context?.fromHtmlWithParams(R.string.actors, movie.Actors)
        binding.tvAwards.text = context?.fromHtmlWithParams(R.string.awards, movie.Awards)
    }

}