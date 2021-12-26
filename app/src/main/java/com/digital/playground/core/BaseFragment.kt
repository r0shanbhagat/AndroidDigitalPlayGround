package com.digital.playground.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.digital.playground.ui.dialog.ProgressDialog

/**
 * @Details BaseFragment contains the common functionality and inherit by
 * all other fragment in project.
 * @Author Roshan Bhagat
 * @Date 07-Nov-2021
 */
abstract class BaseFragment<B : ViewDataBinding, VM : BaseViewModel> : Fragment() {
    protected lateinit var binding: B
    protected lateinit var mViewModel: VM
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
        //   binding.setVariable(BR., mViewModel)
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


    fun showLoading(cancelable: Boolean = false) {
        progressDialog.show()
        progressDialog.setCanceledOnTouchOutside(cancelable)
        progressDialog.setCancelable(cancelable)
    }

    fun hideLoading() {
        progressDialog.dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (this::binding.isInitialized) {
            binding.unbind()
        }
    }


}


//
//    fun getToolbar(): ViewDataBinding? {
//        return when (activity) {
//            is UserActivity -> (activity as UserActivity).getToolbar()
//            is BMDashboardActivity -> (activity as BMDashboardActivity).getToolbar()
//            else -> null
//        }
//    }
//
//    fun setToolbarType(
//        toolbarType: ToolbarType,
//        screenTitle: String,
//        subtitle: String,
//        isBackBlack: Boolean,
//        rightIcon: Int?,
//        rightMostIcon: Int?
//    ) {
//        when (activity) {
//            is UserActivity -> (activity as UserActivity).setToolBarType(
//                toolbarType,
//                screenTitle,
//                subtitle,
//                isBackBlack,
//                rightIcon,
//                rightMostIcon
//            )
//            is BMDashboardActivity -> (activity as BMDashboardActivity).setToolBarType(
//                toolbarType,
//                screenTitle,
//                subtitle,
//                isBackBlack,
//                rightIcon,
//                rightMostIcon
//            )
//        }
//    }
//
//    /**
//     * @method used to navigate from one fragment to other with an animation and bundle data
//     * @param target is the id of fragment to be pushed into stack
//     * @param bundle is the data container from calling fragment to target
//     * @param popUpTo is nullable which is the id of fragment to which pop is required
//     * @param isInclusivePop if this is true, popUpTo fragment will be included in pop process
//     */
////    fun navigateToFragment(
////        target: Int,
////        bundle: Bundle,
////        popUpTo: Int?,
////        isInclusivePop: Boolean = false,
////        applyAnimation: Boolean = true,
////        showEnterAnim: Boolean = true
////    ) {
////        val navOptionBuilder = NavOptions.Builder()
////
////        if (popUpTo != null) {
////            navOptionBuilder.setPopUpTo(popUpTo, isInclusivePop)
////        }
////        if (applyAnimation)
////            if (showEnterAnim) {
////                UtilityHelper.applyAnimation(navOptionBuilder)
////            } else {
////                UtilityHelper.applyReverseAnimation(navOptionBuilder)
////            }
////        val navOptions = navOptionBuilder.build()
////        Navigation.findNavController(mViewDataBinding.root).navigate(
////            target,
////            bundle,
////            navOptions
////        )
////    }
////
////    /**
////     * @method used to navigate from one fragment to other with an animation without bundle data
////     * @param target is the id of fragment to be pushed into stack
////     * @param popUpTo is nullable which is the id of fragment to which pop is required
////     * @param isInclusivePop if this is true, popUpTo fragment will be included in pop process
////     */
////    fun navigateToFragment(
////        target: Int,
////        popUpTo: Int? = null,
////        isInclusivePop: Boolean = false,
////        applyAnimation: Boolean = true,
////        showEnterAnim: Boolean = true
////    ) {
////
////        val navOptionBuilder = NavOptions.Builder()
////
////        if (popUpTo != null) {
////            navOptionBuilder.setPopUpTo(popUpTo, isInclusivePop)
////        }
////        if (applyAnimation)
////            if (showEnterAnim) {
////                UtilityHelper.applyAnimation(navOptionBuilder)
////            } else {
////                UtilityHelper.applyReverseAnimation(navOptionBuilder)
////            }
////        val navOptions = navOptionBuilder.build()
////
////        Navigation.findNavController(mViewDataBinding.root).navigate(
////            target,
////            null,
////            navOptions
////        )
////    }
//
////    /**
////     * @method shows the shimmer layout_ad_space_shake and starts the animation on it,
////     * also hides the main view container of your view
////     * @param shimmerViewContainer is the shimmer view in your xml view
////     * @param layoutMain is your main root layout_ad_space_shake
////     * */
////    fun showShimmer(
////        shimmerViewContainer: ShimmerFrameLayout,
////        layoutMain: View
////    ) {
////        shimmerViewContainer.visibility = View.VISIBLE
////        layoutMain.visibility = View.INVISIBLE
////        shimmerViewContainer.startShimmerAnimation()
////    }
////
////    /**
////     * @method hides the shimmer layout_ad_space_shake and stops the animation on it,
////     * also shows back the main view container of your view
////     * @param shimmerViewContainer is the shimmer view in your xml view
////     * @param layoutMain is your main root layout_ad_space_shake
////     * */
////    fun hideShimmer(
////        shimmerViewContainer: ShimmerFrameLayout,
////        layoutMain: View
////    ) {
////        shimmerViewContainer.visibility = View.GONE
////        layoutMain.visibility = View.VISIBLE
////        shimmerViewContainer.stopShimmerAnimation()
////    }
//
//
//
//}