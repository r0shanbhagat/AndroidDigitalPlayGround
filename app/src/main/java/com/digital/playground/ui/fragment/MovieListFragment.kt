package com.digital.playground.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.digital.playground.R
import com.digital.playground.core.BaseFragment
import com.digital.playground.databinding.FragmentMovieListBinding
import com.digital.playground.ui.adapter.Adapter
import com.digital.playground.ui.adapter.ItemViewModel
import com.digital.playground.ui.adapter.MovieListingModel
import com.digital.playground.ui.callback.IItemClick
import com.digital.playground.ui.dialog.DialogUtil
import com.digital.playground.ui.viewmodel.MovieListViewModel
import com.digital.playground.ui.viewmodel.MovieStateEvent
import com.digital.playground.util.AppUtils
import com.digital.playground.util.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * @Details MovieListFragment:
 * @Author Roshan Bhagat
 */
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MovieListFragment : BaseFragment<FragmentMovieListBinding, MovieListViewModel>() {
    private val movieViewModel: MovieListViewModel by viewModels()
    private lateinit var adapter: Adapter

    override val layoutId: Int = R.layout.fragment_movie_list

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
        setupListener()

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isEnabled = false
            viewModel.setStateEvent(MovieStateEvent.GetMoviesList)
        }
    }

    private fun setupRecyclerView() {
        adapter = Adapter()
        binding.rvMoviesList.adapter = adapter

    }

    private fun setupListener() {
        binding.errorView.viewModel = viewModel
        binding.errorView.incNoNetwork.btnRetry.setOnClickListener {
            viewModel.setStateEvent(MovieStateEvent.GetMoviesList)
        }

        adapter.callback = object : IItemClick<ItemViewModel> {
            override fun onItemClick(item: ItemViewModel) {
                val movieModel: MovieListingModel = item as MovieListingModel
                DialogUtil.show(requireContext(), movieModel.title)
            }
        }

    }

    @Suppress("UNCHECKED_CAST")
    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                    displayLoading(true)
                }
                is DataState.Success -> {
                    displayLoading(false)
                    populateRecyclerView(dataState.data as List<MovieListingModel>)
                }
                is DataState.Error -> {
                    displayError()
                }
            }
        })
    }


    private fun populateRecyclerView(moviesList: List<MovieListingModel>) {
        if (AppUtils.isListNotEmpty(moviesList)) {
            adapter.updateItems(moviesList)
        } else {
            viewModel.errorState.set(MovieListViewModel.EMPTY_DATA)
        }
    }


    private fun displayLoading(isLoading: Boolean) {
        if (isLoading) {
            viewModel.errorState.set(MovieListViewModel.LOADING)
            showShimmer(binding.incShimmer.frameLayoutShimmer, binding.rvMoviesList)
        } else {
            binding.swipeRefreshLayout.isRefreshing = false
            binding.swipeRefreshLayout.isEnabled = true
            hideShimmer(binding.incShimmer.frameLayoutShimmer, binding.rvMoviesList)
        }
    }

    private fun displayError() {
        displayLoading(false)
        if (AppUtils.isListNotEmpty(adapter.listItems)) {
            DialogUtil.show(requireContext(), getString(R.string.error_msg))
        } else {
            viewModel.errorState.set(MovieListViewModel.ERROR)
        }

    }


}