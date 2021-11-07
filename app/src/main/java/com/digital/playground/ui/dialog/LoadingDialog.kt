package com.digital.playground.ui.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

import com.digital.playground.util.AppUtils;


/**
 * LoadingDialog class is responsible for showing the Loader in User screen.
 */
public final class LoadingDialog {
    private static final boolean IS_ANIMATED = true;
    private static Dialog mainDialog;

    /*
     * Show Loading Dialog
     * @param context the context
     */
    public static void show(Context context) {
        try {
            dismissDialog();
            Dialog dialog;
            if (IS_ANIMATED) {
                dialog = new TransparentProgressDialog(context, false);
            } else {
                ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Loading Please wait...");
                progressDialog.setIndeterminate(true);
                dialog = progressDialog;
            }
            mainDialog = dialog;
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

