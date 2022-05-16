package com.digital.playground.ui.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.digital.playground.R
import com.digital.playground.databinding.FragmentSearchBinding
import com.digital.playground.ui.viewmodel.SearchViewModel
import com.digital.playground.utils.applyAnimation
import com.digital.playground.utils.dismissKeyboard
import dagger.hilt.android.AndroidEntryPoint

/**
 * @Details SearchFragment is the starting fragment where user can enter the movie name which they want to search
 * @Author Roshan Bhagat
 */
@AndroidEntryPoint
class SearchFragment(override val layoutId: Int = R.layout.fragment_search) :
    com.digital.playground.core.BaseFragment<FragmentSearchBinding>() {
    private val viewModel: SearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.search = this
        observeSearchModel()
    }

    /**
     * onSubmitClick
     */
    fun onSubmitClick() {
        binding.model?.let {
            context?.dismissKeyboard(binding.etSearch)
            applyAnimation(NavOptions.Builder())
            val action = SearchFragmentDirections.nextAction(it.searchText)
            findNavController().navigate(action)
        }
    }

    /**
     * observeSearchModel : Search Movie
     */
    private fun observeSearchModel() {
        viewModel.searchLiveData.observe(viewLifecycleOwner) { model ->
            binding.model = model
        }
    }

}