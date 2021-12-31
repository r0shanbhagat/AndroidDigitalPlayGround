package com.digital.playground.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.digital.playground.R
import com.digital.playground.core.BaseFragment
import com.digital.playground.databinding.FragmentMovieListBinding
import com.digital.playground.repository.model.Movie
import com.digital.playground.ui.adapter.MovieAdapter
import com.digital.playground.ui.viewmodel.MovieListViewModel
import com.digital.playground.ui.viewmodel.MovieStateEvent
import com.digital.playground.util.AppUtils
import com.digital.playground.util.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MovieListFragment : BaseFragment<FragmentMovieListBinding, MovieListViewModel>() {
    private val movieViewModel: MovieListViewModel by viewModels()
    private lateinit var adapter: MovieAdapter

    override val layoutId: Int
        get() = R.layout.fragment_movie_list

    override val viewModel: MovieListViewModel
        get() = movieViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        if (savedInstanceState == null) {
            viewModel.setStateEvent(MovieStateEvent.GetMoviesList)
        }
    }

    private fun init() {
        setupRecyclerView()
        subscribeObservers()
        displayError()

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.setStateEvent(MovieStateEvent.GetMoviesList)
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun setupRecyclerView() {
        adapter = MovieAdapter()
        binding.rvMoviesList.adapter = adapter

    }


    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                    displayLoading(true)
                }
                is DataState.Success -> {
                    displayLoading(false)
                    populateRecyclerView(dataState.data as List<Movie>)
                }
                is DataState.Error -> {
                    displayLoading(false)
                    viewModel.errorState.set(MovieListViewModel.ERROR)

                }
            }
        })
    }


    private fun populateRecyclerView(moviesList: List<Movie>) {
        if (AppUtils.isListNotEmpty(moviesList)) {
            adapter.setMovieList(moviesList)
        } else {
            viewModel.errorState.set(MovieListViewModel.EMPTY_DATA)
        }
    }


    private fun displayLoading(isLoading: Boolean) {
        if (isLoading) {
            viewModel.errorState.set(MovieListViewModel.LOADING)
            adapter.clearList()
            showShimmer(binding.incShimmer.frameLayoutShimmer, binding.rvMoviesList)
        } else {
            hideShimmer(binding.incShimmer.frameLayoutShimmer, binding.rvMoviesList)
        }
    }

    private fun displayError() {
        binding.errorView.viewModel = viewModel
        binding.errorView.incNoNetwork.btnRetry.setOnClickListener {
            viewModel.setStateEvent(MovieStateEvent.GetMoviesList)
        }

    }

}