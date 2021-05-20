package com.demo.assignment.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * SwipeViewPager :Custom ViewPager class to handle the Left and Right Swipe
 */
public class SwipeViewPager extends ViewPager {

    private static final int min_distance = 20;
    private boolean eventSent = false;
    private SwipeListener mSwipeListener;
    private boolean isTouchCaptured;
    private float upX1;
    private float upY1;

    /**
     * @param context :context of View
     */
    public SwipeViewPager(Context context) {
        super(context);
    }

    /**
     * @param context :Context
     * @param attrs   :Attrs
     */
    public SwipeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE: {
                if (!isTouchCaptured) {
                    upX1 = event.getX();
                    upY1 = event.getY();
                    isTouchCaptured = true;
                } else {
                    float upX2 = event.getX();
                    float upY2 = event.getY();
                    float deltaX = upX1 - upX2;
                    float deltaY = upY1 - upY2;
                    //HORIZONTAL SCROLL
                    if (Math.abs(deltaX) > Math.abs(deltaY)) {
                        if (Math.abs(deltaX) > min_distance) {
                            // left or right
                            if (deltaX < 0) {
                                if (!eventSent && mSwipeListener != null) {
                                    mSwipeListener.onLeftSwipe();
                                    eventSent = true;
                                }
                            }
                            if (deltaX > 0) {
                                if (!eventSent && mSwipeListener != null) {
                                    if (mSwipeListener.onRightSwipe()) {
                                        eventSent = true;
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                isTouchCaptured = false;
                eventSent = false;
            }

        }
        return super.onTouchEvent(event);
    }

    /**
     * @param mSwipeListener :Callback for Swipe action
     */
    public void setSwipeListener(SwipeListener mSwipeListener) {
        this.mSwipeListener = mSwipeListener;
    }

    /**
     * Callback Interface
     */
    public interface SwipeListener {
        /**
         * On User Swipe to Left direction
         */
        void onLeftSwipe();

        /**
         * On User Swipe to Right direction
         */
        boolean onRightSwipe();
    }

}