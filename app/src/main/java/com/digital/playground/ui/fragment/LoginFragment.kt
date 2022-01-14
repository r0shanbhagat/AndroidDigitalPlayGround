package com.digital.playground.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.digital.playground.R
import com.digital.playground.core.BaseFragment
import com.digital.playground.databinding.FragmentLoginBinding
import com.digital.playground.ui.viewmodel.LoginViewModel
import com.digital.playground.util.AppUtils
import dagger.hilt.android.AndroidEntryPoint

/**
 * @Details LoginFragment:
 * @Author Roshan Bhagat
 */
@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {
    private val loginViewModel: LoginViewModel by viewModels()

    override val layoutId: Int = R.layout.fragment_login

    override val viewModel: LoginViewModel
        get() = loginViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.login = this
        observeLoginModel()
    }

    /**
     * onSubmitClick
     */
    fun onSubmitClick(v: View) {
        AppUtils.hideSoftInput(requireActivity())
        AppUtils.applyAnimation(NavOptions.Builder())
        val action =
            LoginFragmentDirections.nextAction(binding.model!!.firstName, binding.model!!.lastName)
        v.findNavController().navigate(action)

    }

    /**
     * observeLoginModel : FirstName:LastName
     */
    private fun observeLoginModel() {
        //Observing the response
        viewModel.loginLiveData.observe(viewLifecycleOwner, { loginModel ->
            binding.model = loginModel
        })
    }

}