package com.digital.playground.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.digital.playground.R
import com.digital.playground.core.BaseFragment
import com.digital.playground.databinding.FragmentMovieListBinding
import com.digital.playground.repository.model.Movie
import com.digital.playground.ui.adapter.MovieAdapter
import com.digital.playground.ui.viewmodel.MovieListViewModel
import com.digital.playground.ui.viewmodel.MovieStateEvent
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
        setupRecyclerView()
        subscribeObservers()
        viewModel.setStateEvent(MovieStateEvent.GetMoviesList)
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.setStateEvent(MovieStateEvent.GetMoviesList)
            navigateToFragment(R.id.movieListFragment)
        }
    }

    private fun setupRecyclerView() {
        adapter = MovieAdapter()
        binding.rvMoviesList.adapter = adapter
    }


    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is DataState.Success<List<Movie>> -> {
                    displayLoading(false)
                    populateRecyclerView(dataState.data)
                }
                is DataState.Loading -> {
                    displayLoading(true)
                }
                is DataState.Error -> {
                    displayLoading(false)
                    displayError(dataState.exception.message)
                }
            }
        })
    }


    private fun populateRecyclerView(moviesList: List<Movie>) {
        if (moviesList.isNotEmpty())
            adapter.setMovieList(moviesList)
    }

    private fun displayError(message: String?) {
        if (message.isNullOrEmpty()) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(requireContext(), "Unknown error", Toast.LENGTH_LONG).show()
        }
    }

    private fun displayLoading(isLoading: Boolean) {
        if (isLoading) {
            showLoading()
        } else {
            hideLoading()
        }
        binding.swipeRefreshLayout.isRefreshing = isLoading
    }

}