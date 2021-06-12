package com.demo.assignment.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.demo.assignment.databinding.FragmentLoginBinding
import com.demo.assignment.repository.model.LoginModel
import com.demo.assignment.ui.viewmodel.LoginViewModel
import com.demo.assignment.util.AppUtils


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this,
                LoginViewModel.Factory(LoginModel())).get(LoginViewModel::class.java)

        binding.login = this
        observeLoginModel()
        return binding.root
    }

    /**
     * onSubmitClick
     */
    fun onSubmitClick(v: View) {
        AppUtils.hideSoftInput(requireActivity())
        AppUtils.applyAnimation(NavOptions.Builder())
        val action = LoginFragmentDirections.nextAction(binding.model!!.firstName, binding.model!!.lastName)
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