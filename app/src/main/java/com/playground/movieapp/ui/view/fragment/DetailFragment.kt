package com.playground.movieapp.ui.view.fragment

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.playground.movieapp.R
import com.playground.movieapp.core.BaseFragment
import com.playground.movieapp.data.dto.MovieDetailModel
import com.playground.movieapp.databinding.FragmentMovieDetailBinding
import com.playground.movieapp.ui.viewmodel.DetailViewModel
import com.playground.movieapp.utils.ViewState
import com.playground.movieapp.utils.ext.fromHtmlWithParams
import com.playground.movieapp.utils.ext.showToast
import com.playground.movieapp.utils.loadImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
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

        viewModel.getMovieDetailsData(safeArgs.id)

        setupBackButton()
        subscribeObservers()
    }

    private fun setupBackButton() {
        binding.ivBackButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    /**
     * subscribeObservers is an Observers function for mutable live data
     */
    private fun subscribeObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is ViewState.Loading -> {
                            showLoading()
                        }
                        is ViewState.Success -> {
                            hideLoading()
                            val movie: MovieDetailModel = it.data as MovieDetailModel
                            updateUi(movie)
                        }
                        is ViewState.Failure -> {
                            hideLoading()
                            context?.showToast(getString(R.string.error_msg))

                        }
                    }
                }
            }
        }
    }

    /**
     * Update post and title.
     *
     * @param movie Movie
     */
    private fun updatePostAndTitle(movie: MovieDetailModel) {
        binding.ivPoster.transitionName = movie.posterPath
        loadImage(binding.ivPoster, movie.posterPath)
        binding.tvTitle.text = movie.title
        binding.tvYear.text = movie.releaseDate

        binding.ivPoster.transitionName = movie.posterPath
        binding.tvTitle.transitionName = movie.title

    }

    private fun updateUi(movie: MovieDetailModel) {
        updatePostAndTitle(movie)
        binding.tvRating.text = resources.getString(R.string.rating, movie.voteAverage)
        binding.tvDescription.text = movie.overview

        binding.tvDirectors.text = context?.fromHtmlWithParams(R.string.status, movie.status)
        binding.tvActors.text = context?.fromHtmlWithParams(R.string.runtime, movie.runtime)
        if (movie.productionCountry?.isNotEmpty() == true) {
            binding.tvAwards.text =
                context?.fromHtmlWithParams(R.string.country, movie.productionCountry[0].name)
        }
    }

}