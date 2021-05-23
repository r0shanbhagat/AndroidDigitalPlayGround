package com.demo.assignment.ui.fragment;

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.demo.assignment.databinding.FragmentLoginBinding
import com.demo.assignment.ui.viewmodel.LoginViewModel
import com.demo.assignment.util.AppUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding;
    private lateinit var viewModel: LoginViewModel

    // implement the TextWatcher callback listener
    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // check whether both the fields are empty or not
            binding.model?.isSubmitEnable = (!TextUtils.isEmpty(binding.etFirstName.text)
                    && !TextUtils.isEmpty(binding.etLastName.text))
        }

        override fun afterTextChanged(s: Editable) {}
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.login = this
        binding.etLastName.addTextChangedListener(textWatcher)
        binding.etFirstName.addTextChangedListener(textWatcher)
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