package com.digital.playground.ui.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.digital.playground.R
import com.digital.playground.core.BaseFragment
import com.digital.playground.databinding.FragmentMovieListBinding
import com.digital.playground.ui.adapter.Adapter
import com.digital.playground.ui.adapter.ItemViewModel
import com.digital.playground.ui.adapter.MovieModel
import com.digital.playground.ui.callback.IItemClick
import com.digital.playground.ui.viewmodel.MovieStateEvent
import com.digital.playground.ui.viewmodel.MovieViewModel
import com.digital.playground.utils.DataState
import com.digital.playground.utils.DialogUtil
import com.digital.playground.utils.isListNotEmpty
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


/**
 * @Details MovieListFragment:Main fragment where user interact with UI :
 *  1. Fetch the Movie
 *  2.Populate the data on UI
 * @Author Roshan Bhagat
 * @constructor
 */
@AndroidEntryPoint
class MovieListFragment(
    override val layoutId: Int = R.layout.fragment_movie_list
) : BaseFragment<FragmentMovieListBinding, MovieViewModel>() {

    private val blogParseViewModel: MovieViewModel by viewModels()

    @Inject
    lateinit var movieAdapter: Adapter

    override val viewModel: MovieViewModel
        get() = blogParseViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        if (savedInstanceState == null) {
            fetchMovieList()
        }
    }

    /**
     * Init.
     */
    private fun init() {
        setupRecyclerView()
        subscribeObservers()
        setupListener()

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isEnabled = false
            viewModel.setStateIntent(MovieStateEvent.GetMoviesList)
        }
    }


    /**
     * Fetch movie content
     *
     */
    private fun fetchMovieList() {
        /**
         * MVI approach provides more flexibility to perform multiple operation/intent from same state .
         * This way we can remove number of boilerplate code from our repo and easily achieve the asynchronous Programming
         */
        viewModel.setStateIntent(MovieStateEvent.GetMoviesList)
    }


    /**
     * Setup recycler view.
     */
    private fun setupRecyclerView() {
        binding.rvMoviesList.adapter = movieAdapter

    }

    /**
     * Setup listener.
     */
    private fun setupListener() {
        binding.errorView.viewModel = viewModel
        binding.errorView.incNoNetwork.btnRetry.setOnClickListener {
            viewModel.setStateIntent(MovieStateEvent.GetMoviesList)
        }

        movieAdapter.callback = object : IItemClick<ItemViewModel> {
            override fun onItemClick(item: ItemViewModel) {
                val movieModel: MovieModel = item as MovieModel
                DialogUtil.show(requireContext(), movieModel.title)
            }
        }

    }

    /**
     * subscribeObservers is an Observers function for mutable live data
     */
    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                    displayLoading(true)
                }
                is DataState.Success -> {
                    displayLoading(false)
                    populateMovieList(dataState.data as List<MovieModel>)
                }
                is DataState.Error -> {
                    displayError()
                }
            }
        }
    }

    /**
     * Populate movie list.
     *
     * @param moviesList Movies list
     */
    private fun populateMovieList(moviesList: List<MovieModel>) {
        if (isListNotEmpty(moviesList)) {
            movieAdapter.updateItems(moviesList)
        } else {
            viewModel.errorState.set(MovieViewModel.EMPTY_DATA)
        }
    }


    /**
     * Display loading.
     *
     * @param isLoading Is loading
     */
    private fun displayLoading(isLoading: Boolean) {
        if (isLoading) {
            viewModel.errorState.set(MovieViewModel.LOADING)
            showShimmer(binding.incShimmer.frameLayoutShimmer, binding.rvMoviesList)
        } else {
            binding.swipeRefreshLayout.isRefreshing = false
            binding.swipeRefreshLayout.isEnabled = true
            hideShimmer(binding.incShimmer.frameLayoutShimmer, binding.rvMoviesList)
        }
    }

    /**
     * Display error.
     */
    private fun displayError() {
        displayLoading(false)
        if (isListNotEmpty(movieAdapter.listItems)) {
            DialogUtil.show(requireContext(), getString(R.string.error_msg))
        } else {
            viewModel.errorState.set(MovieViewModel.ERROR)
        }

    }


}


