package com.demo.assignment.ui.fragment;

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.demo.assignment.R
import com.demo.assignment.databinding.FragmentLoginBinding
import com.demo.assignment.repository.model.LoginModel
import com.demo.assignment.util.AppConstant
import com.demo.assignment.util.AppUtil


class LoginFragment : Fragment() {
    private val loginModel = LoginModel()
    private var binding: FragmentLoginBinding? = null

    // implement the TextWatcher callback listener
    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // check whether both the fields are empty or not
            binding!!.model?.isSubmitEnable = (!TextUtils.isEmpty(binding!!.etFirstName.text)
                    && !TextUtils.isEmpty(binding!!.etLastName.text))
        }

        override fun afterTextChanged(s: Editable) {}
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding!!.login = this
        binding!!.model = loginModel
        binding!!.etLastName.addTextChangedListener(textWatcher)
        binding!!.etFirstName.addTextChangedListener(textWatcher)
        return binding!!.root
    }

    /**
     * onSubmitClick
     */
    fun onSubmitClick() {
        AppUtil.hideSoftInput(requireActivity())
        AppUtil.applyAnimation(NavOptions.Builder())
        // find navigation
        val bundle = Bundle()
        bundle.putString(AppConstant.ARGS_FIRST_NAME, binding!!.model?.firstName)
        bundle.putString(AppConstant.ARGS_LAST_NAME,
                binding!!.model?.lastName)
        Navigation.findNavController(requireView()).navigate(R.id.next_action, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}