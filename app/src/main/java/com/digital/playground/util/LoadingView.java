package com.digital.playground.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.digital.playground.R;

public class LoadingView extends LinearLayout {

    private AppCompatImageView mLoadingAnimation;
    private String mMessage;
    private int mBackgroundColor = -1;
    private TypedArray typedArray;

    public LoadingView(Context context) {
        super(context);
        init();
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingView);
        init();
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingView, defStyle, 0);

        init();
    }


    private void init() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.loading_anim, this);
        mLoadingAnimation = rootView.findViewById(R.id.ivLoadingAnim);
        AppCompatTextView mLoadingMessage = rootView.findViewById(R.id.tvLoadingMsg);

        /*
         * Getting the Loading Message and Background Color from attrs
         */
        if (typedArray != null &&
                typedArray.getIndexCount() > 0) {
            mMessage = typedArray.getString(R.styleable.LoadingView_loadingMessage);
            mBackgroundColor = typedArray.getResourceId(R.styleable.LoadingView_backgroundColor, -1);
            /*
             * Recycle the typed typedArray
             */
            typedArray.recycle();
        }


        if (!TextUtils.isEmpty(mMessage)) {
            mLoadingMessage.setText(mMessage);
            mLoadingMessage.setVisibility(VISIBLE);
        } else {
            mLoadingMessage.setVisibility(GONE);
        }
        /*
         * Introduced the new attrs for mBackgroundColor in custom view.
         */
        rootView.setBackgroundColor(ContextCompat.getColor(getContext(), mBackgroundColor != -1 ? mBackgroundColor : R.color.transparent));
        startAnimation();
    }

    public void startAnimation() {
        mLoadingAnimation.post(() -> {
            AnimationDrawable animationDrawable = (AnimationDrawable) mLoadingAnimation.getDrawable();
            animationDrawable.start();
        });
    }

    @SuppressWarnings({"unused", "RedundantSuppression"})
    public void stopAnimation() {
        mLoadingAnimation.post(() -> {
            AnimationDrawable animationDrawable = (AnimationDrawable) mLoadingAnimation.getDrawable();
            animationDrawable.stop();
        });
    }
}
