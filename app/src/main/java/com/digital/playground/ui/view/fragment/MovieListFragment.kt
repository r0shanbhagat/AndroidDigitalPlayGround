package com.digital.playground.ui.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.digital.playground.core.BaseFragment
import com.digital.playground.ui.adapter.Adapter
import com.digital.playground.ui.adapter.ItemViewModel
import com.digital.playground.ui.adapter.MovieModel
import com.digital.playground.ui.callback.IItemClick
import com.digital.playground.ui.viewmodel.MovieListViewModel
import com.digital.playground.utils.ViewState
import com.digital.playground.utils.applyAnimation
import com.digital.playground.utils.ext.showToast
import com.digital.playground.utils.isListNotEmpty
import com.playground.movieapp.R
import com.playground.movieapp.databinding.FragmentMovieListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
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
) : BaseFragment<FragmentMovieListBinding>() {

    private val viewModel by viewModels<MovieListViewModel>()

    @Inject
    lateinit var movieAdapter: Adapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    /**
     * Init.
     */
    private fun init() {
        setupRecyclerView()
        subscribeObservers()
        setupListener()
    }


    /**
     * Setup recycler view.
     */
    private fun setupRecyclerView() {
        binding.rvMoviesList.apply {
            adapter = movieAdapter
        }
    }

    /**
     * Setup listener.
     */
    private fun setupListener() {
        movieAdapter.callback = object : IItemClick<ItemViewModel> {
            override fun onItemClick(item: ItemViewModel) {
                applyAnimation(NavOptions.Builder())
                val movie: MovieModel = item as MovieModel
                findNavController().navigate(
                    MovieListFragmentDirections.actionMovieListFragmentToDetailFragment(
                        movie.id
                    )
                )
            }
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
                            val moviesList: List<MovieModel> = it.data as List<MovieModel>
                            if (isListNotEmpty(moviesList)) {
                                movieAdapter.updateItems(moviesList)
                            }
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
}





