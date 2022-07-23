package com.playground.movieapp.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.facebook.shimmer.ShimmerFrameLayout
import com.playground.movieapp.ui.view.dialog.ProgressDialog


/**
 * @Details BaseFragment contains the common functionality and inherit by
 * all other fragment in project.
 * @Author Roshan Bhagat
 * @param B
 * @param VM
 * @constructor Create Base fragment
 */
abstract class BaseFragment<B : ViewDataBinding> : Fragment() {
    protected lateinit var binding: B
    private lateinit var progressDialog: ProgressDialog


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        progressDialog = ProgressDialog(requireContext())

        return binding.root
    }

    /**
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layoutId: Int


    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    open val bindingVariable: Int = 0

    /**
     * Show loading
     *
     * @param cancelable
     */
    fun showLoading(cancelable: Boolean = false) {
        progressDialog.show()
        progressDialog.setCanceledOnTouchOutside(cancelable)
        progressDialog.setCancelable(cancelable)
    }

    /**
     * Hide loading
     *
     */
    fun hideLoading() {
        progressDialog.dismiss()
    }

    /**
     * Is loading
     *
     * @return
     */
    fun isLoading(): Boolean {
        return progressDialog.isShowing
    }

    /**
     * View model factory
     *
     * @param VM
     * @param f
     * @receiver
     */
    @Suppress("UNCHECKED_CAST")
    protected inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T = f() as T
        }

    /**
     * @method shows the shimmer view ,
     * and hides the main view container
     * @param shimmerViewContainer is the shimmer view in your xml view
     * @param layoutMain is your main root layout
     * */
    fun showShimmer(
        shimmerViewContainer: ShimmerFrameLayout,
        layoutMain: View
    ) {
        shimmerViewContainer.visibility = View.VISIBLE
        layoutMain.visibility = View.INVISIBLE
        shimmerViewContainer.startShimmer()
    }

    /**
     * @method hides the shimmer layout_ad_space_shake and stops the animation on it,
     * also shows back the main view container of your view
     * @param shimmerViewContainer is the shimmer view in your xml view
     * @param layoutMain is your main root layout_ad_space_shake
     * */
    fun hideShimmer(
        shimmerViewContainer: ShimmerFrameLayout,
        layoutMain: View
    ) {
        shimmerViewContainer.visibility = View.GONE
        layoutMain.visibility = View.VISIBLE
        shimmerViewContainer.stopShimmer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (this::binding.isInitialized) {
            binding.unbind()
        }
    }


}