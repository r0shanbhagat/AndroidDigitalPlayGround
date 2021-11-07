package com.digital.playground.ui.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.digital.playground.R
import com.digital.playground.core.BaseFragment
import com.digital.playground.core.ViewState
import com.digital.playground.databinding.FragmentMovieListBinding
import com.digital.playground.repository.logging.NoInternetException
import com.digital.playground.repository.model.MovieModel
import com.digital.playground.ui.adapter.MovieAdapter
import com.digital.playground.ui.dialog.DialogUtil.show
import com.digital.playground.ui.viewmodel.MovieListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : BaseFragment<FragmentMovieListBinding, MovieListViewModel>() {

    private val adapter = MovieAdapter()


    override val layoutId: Int
        get() = R.layout.fragment_movie_list

    override val viewModel: MovieListViewModel
        get() = ViewModelProvider(this).get(MovieListViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvMoviesList.adapter = adapter
        observeApiResponse()
        viewModel.getMoviesList()
    }

    /**
     * observeApiResponse
     */
    private fun observeApiResponse() {
        mViewModel?.movieLiveData?.observe(viewLifecycleOwner) { viewState ->
            if (null == viewState) return@observe
            when (viewState.currentState) {
                ViewState.LOADING -> {
                    showLoading()
                }
                ViewState.SUCCESS -> {
                    hideLoading()
                    adapter.setMovieList(viewState.data as List<MovieModel>)
                }
                ViewState.FAILED -> {
                    hideLoading()
                    showInfoDialog(viewState.error!!)
                }
            }
        }
    }


    /**
     * Showing No Network Dailog
     */
    private fun showInfoDialog(throwable: Throwable) {
        if (isAdded) {
            var msg = getString(R.string.error_msg)
            if (throwable is NoInternetException) {
                msg = getString(R.string.no_internet_msg)
            }
            show(
                context, msg
            ) { _: DialogInterface?, _: Int -> }
        }
    }
}