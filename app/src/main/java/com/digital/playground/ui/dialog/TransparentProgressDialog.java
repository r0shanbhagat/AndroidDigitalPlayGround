package com.digital.playground.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Window;

import com.digital.playground.R;

public class TransparentProgressDialog extends Dialog {
    private static AnimationDrawable animation;

    public TransparentProgressDialog(Context context, boolean cancelable) {
        super(context, R.style.NewDialog);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.progress_dialog);
        setCancelable(cancelable);
        setCanceledOnTouchOutside(cancelable);
    }

    @Override
    public void show() {
        animation = (AnimationDrawable)
                findViewById(R.id.imageview_progress).getBackground();
        animation.start();
        super.show();
    }

    @Override
    public void dismiss() {
        if (isShowing()) {
            animation.stop();
            super.dismiss();
        }

    }
}

