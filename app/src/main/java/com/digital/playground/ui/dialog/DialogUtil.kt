package com.digital.playground.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.digital.playground.util.AppUtils;

public final class DialogUtil {
    private static AlertDialog mainDialog;

    /*
     * Show Loading Dialog
     * @param context the context
     */
    public static void show(Context context, String msg, @Nullable DialogInterface.OnClickListener onClickListener) {
        try {
            dismissDialog();
            mainDialog = new AlertDialog
                    .Builder(context)
                    .setMessage(msg)
                    .setTitle("Alert")
                    .setPositiveButton("OK", onClickListener)
                    .create();
            mainDialog.show();
        } catch (Exception e) {
            AppUtils.showException(e);
        }
    }


    /**
     * Dismiss existing Loading dialog.
     */
    public static void dismissDialog() {
        try {
            if (null != mainDialog && mainDialog.isShowing()) {
                mainDialog.dismiss();
            }
        } catch (Exception e) {
            AppUtils.showException(e);
        }
        mainDialog = null;

    }


}
