package com.digital.playground.ui.view.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.playground.movieapp.databinding.ProgressDialogBinding

/**
 * @Details ProgressDialog:
 * @Author Roshan Bhagat
 *
 * @param context
 */
class ProgressDialog(context: Context) : Dialog(context) {

    val binding: ProgressDialogBinding

    init {
        window?.setLayout(
            android.view.ViewGroup.LayoutParams.MATCH_PARENT,
            android.view.ViewGroup.LayoutParams.MATCH_PARENT
        )

        binding = ProgressDialogBinding.inflate(
            LayoutInflater.from(context), null, false
        )
        setContentView(binding.root)
        this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }


    /**
     * Set the text of the FeedBack
     * @param text of the FeedBack
     * @return the instance of ProgressDialog to make a chain of function easily
     */
    fun setText(text: String) {
        binding.tvMessage.text = text
    }

}