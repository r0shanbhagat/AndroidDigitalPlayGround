package com.digital.playground.ui.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.digital.playground.R
import com.digital.playground.core.BaseFragment
import com.digital.playground.databinding.FragmentMovieListBinding
import com.digital.playground.ui.adapter.Adapter
import com.digital.playground.ui.adapter.ItemViewModel
import com.digital.playground.ui.adapter.MovieModel
import com.digital.playground.ui.callback.IItemClick
import com.digital.playground.ui.viewmodel.MovieStateEvent
import com.digital.playground.ui.viewmodel.MovieViewModel
import com.digital.playground.utils.ViewState
import com.digital.playground.utils.applyAnimation
import com.digital.playground.utils.isListNotEmpty
import com.digital.playground.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
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

    private val viewModel: MovieViewModel by viewModels()
    private val safeArgs: MovieListFragmentArgs by navArgs()

    @Inject
    lateinit var movieAdapter: Adapter

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
        viewModel.setStateIntent(MovieStateEvent.GetMoviesList(safeArgs.searchText))
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
        movieAdapter.callback = object : IItemClick<ItemViewModel> {
            override fun onItemClick(item: ItemViewModel) {
                applyAnimation(NavOptions.Builder())
                val movie: MovieModel = item as MovieModel
                val action = MovieListFragmentDirections.actionSearchFragmentToDetailFragment(movie)
                findNavController().navigate(action)
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





