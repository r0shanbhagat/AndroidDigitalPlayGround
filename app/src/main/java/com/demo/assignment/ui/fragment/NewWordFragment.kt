package com.demo.assignment.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.demo.assignment.R
import com.demo.assignment.databinding.FragmentNewWordBinding
import com.demo.assignment.ui.viewmodel.NewWordViewModel
import com.demo.assignment.util.AppConstant
import com.demo.assignment.util.AppUtils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewWordFragment : DialogFragment() {

    private lateinit var binding: FragmentNewWordBinding
    private val viewModel: NewWordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentNewWordBinding.inflate(inflater)
        binding.view = this
        observeModel()
        return binding.root
    }

    /**
     * observeLoginModel : FirstName:LastName
     */
    private fun observeModel() {
        //Observing the response
        viewModel.getWordModel().observe(viewLifecycleOwner, { loginModel ->
            binding.model = loginModel
        })
    }

    /**
     * onSubmitClick
     */
    fun onSubmitClick(v: View) {
        AppUtils.hideSoftInput(requireActivity())
        setFragmentResult(AppConstant.REQUEST_KEY_WORD, bundleOf(
                AppConstant.WORD to binding.model?.word))
        binding.model!!.word = ""
        // findNavController().popBackStack()
        dismiss()

    }
}