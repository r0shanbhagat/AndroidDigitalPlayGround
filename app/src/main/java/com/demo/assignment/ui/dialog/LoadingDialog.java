package com.demo.assignment.ui.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

import com.demo.assignment.util.AppUtils;


/**
 * LoadingDialog class is responsible for showing the Loader in User screen.
 */
public final class LoadingDialog {
    private static Dialog mainDialog;

    /*
     * Show Loading Dialog
     * @param context the context
     */
    public static void show(Context context) {
        try {
            dismissDialog();
            ProgressDialog dialog = new ProgressDialog(context);
            dialog.setMessage("Loading Please wait...");
            dialog.setIndeterminate(true);
            dialog.show();
            mainDialog = dialog;
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

