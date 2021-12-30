package com.digital.playground.ui.dialog

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import com.digital.playground.util.AppUtils.showException

object DialogUtil {
    private var mainDialog: AlertDialog? = null

    /*
     * Show Loading Dialog
     * @param context the context
     */
    fun show(context: Context?, msg: String?, onClickListener: DialogInterface.OnClickListener?) {
        try {
            dismissDialog()
            mainDialog = AlertDialog.Builder(context!!)
                .setMessage(msg)
                .setTitle("Alert")
                .setPositiveButton("OK", onClickListener)
                .create()
            mainDialog!!.show()
        } catch (e: Exception) {
            showException(e)
        }
    }

    /**
     * Dismiss existing Loading dialog.
     */
    fun dismissDialog() {
        try {
            if (mainDialog?.isShowing == true) {
                mainDialog!!.dismiss()
            }
        } catch (e: Exception) {
            showException(e)
        }
        mainDialog = null
    }
}