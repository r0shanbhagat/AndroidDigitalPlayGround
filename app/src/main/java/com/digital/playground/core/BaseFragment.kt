package com.digital.playground.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.digital.playground.ui.dialog.ProgressDialog
import com.digital.playground.util.AppUtils
import com.facebook.shimmer.ShimmerFrameLayout

/**
 * @Details BaseFragment contains the common functionality and inherit by
 * all other fragment in project.
 * @Author Roshan Bhagat
 * @Date 07-Nov-2021
 */
abstract class BaseFragment<B : ViewDataBinding, VM : BaseViewModel> : Fragment() {
    protected lateinit var binding: B
    private lateinit var mViewModel: VM
    private lateinit var progressDialog: ProgressDialog


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        mViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.executePendingBindings()
        binding.setVariable(bindingVariable, mViewModel)
        progressDialog = ProgressDialog(requireContext())

        return binding.root
    }

    /**
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layoutId: Int

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract val viewModel: VM

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    open val bindingVariable: Int = 0

    fun showLoading(cancelable: Boolean = false) {
        progressDialog.show()
        progressDialog.setCanceledOnTouchOutside(cancelable)
        progressDialog.setCancelable(cancelable)
    }

    fun hideLoading() {
        progressDialog.dismiss()
    }

    fun isLoading(): Boolean {
        return progressDialog.isShowing
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (this::binding.isInitialized) {
            binding.unbind()
        }
    }


    /**
     * @method used to navigate from one fragment to other with an animation and bundle data
     * @param target is the id of fragment to be pushed into stack
     * @param bundle is the data container from calling fragment to target
     * @param popUpTo is nullable which is the id of fragment to which pop is required
     * @param isInclusivePop if this is true, popUpTo fragment will be included in pop process
     */
    fun navigateToFragment(
        target: Int,
        bundle: Bundle? = null,
        popUpTo: Int? = null,
        isInclusivePop: Boolean = false,
        applyAnimation: Boolean = true,
        showEnterAnim: Boolean = true
    ) {
        val navOptionBuilder = NavOptions.Builder()

        if (popUpTo != null) {
            navOptionBuilder.setPopUpTo(popUpTo, isInclusivePop)
        }
        if (applyAnimation)
            if (showEnterAnim) {
                AppUtils.applyAnimation(navOptionBuilder)
            } else {
                AppUtils.applyReverseAnimation(navOptionBuilder)
            }
        val navOptions = navOptionBuilder.build()
        Navigation.findNavController(binding.root).navigate(
            target,
            bundle,
            navOptions
        )
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
}