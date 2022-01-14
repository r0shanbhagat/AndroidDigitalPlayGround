package com.digital.playground.ui.dialog

import android.app.Dialog
import android.content.Context
import com.digital.playground.util.AppUtils.showException

/**
 * @Details LoadingDialog: class is responsible for showing the Loader in User screen.
 * @Author Roshan Bhagat
 */
object LoadingDialog {
    private var mainDialog: Dialog? = null

    /*
     * Show Loading Dialog
     * @param context the context
     */
    fun show(context: Context, cancelable: Boolean = false) {
        try {
            dismiss()
            mainDialog = ProgressDialog(context)
            mainDialog?.setCanceledOnTouchOutside(cancelable)
            mainDialog?.setCancelable(cancelable)
            mainDialog?.show()
        } catch (e: Exception) {
            showException(e)
        }
    }

    /**
     * Dismiss existing Loading dialog.
     */
    fun dismiss() {
        try {
            if (null != mainDialog && mainDialog!!.isShowing) {
                mainDialog!!.dismiss()
            }
        } catch (e: Exception) {
            showException(e)
        }
        mainDialog = null
    }
}