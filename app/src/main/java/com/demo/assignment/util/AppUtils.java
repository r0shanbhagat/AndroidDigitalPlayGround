package com.demo.assignment.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.navigation.NavOptions;

import com.demo.assignment.BuildConfig;
import com.demo.assignment.R;

/**
 * The type App utils.
 */
public final class AppUtils {

    /**
     * Is mobile network available boolean.
     *
     * @param context the context
     * @return boolean boolean
     */
    public static boolean isNetworkConnected(@NonNull Context context) {
        boolean isConnected = false;
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = connMgr.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if ((ni.getTypeName().equalsIgnoreCase("MOBILE") && ni.isConnected())
                    || (ni.getTypeName().equalsIgnoreCase("WIFI") && ni.isConnected())) {
                isConnected = true;
                break;
            }
        }
        return isConnected;
    }

    /**
     * Show log. You can disable Logs by setting "BuildConfig.LOG" value to False
     *
     * @param tagName the tag name
     * @param message the message
     */
    public static void showLog(String tagName, String message) {
        if (BuildConfig.DEBUG && !TextUtils.isEmpty(message)) {
            int maxLogSize = 1000;
            for (int i = 0; i <= message.length() / maxLogSize; i++) {
                int start = i * maxLogSize;
                int end = (i + 1) * maxLogSize;
                end = Math.min(end, message.length());
                Log.v(tagName, message.substring(start, end));
            }
        }
    }

    /**
     * Print Exceptions in Log.
     *
     * @param t :Throwable
     */
    public static void showException(Throwable t) {
        if (BuildConfig.DEBUG) {
            Log.e("", Log.getStackTraceString(t));
        }
    }

    /**
     * @param navOptionBuilder navOptionBuilder
     *                         Used to apply animation in fragment navigation
     */
    public static void applyAnimation(NavOptions.Builder navOptionBuilder) {
        navOptionBuilder.setEnterAnim(R.anim.right_in);
        navOptionBuilder.setExitAnim(R.anim.left_out);
        navOptionBuilder.setPopExitAnim(R.anim.right_out);
        navOptionBuilder.setPopEnterAnim(R.anim.left_in);
    }

    /**
     * Hide soft keyboard from view
     *
     * @param mActivity :Activity
     */
    public static void hideSoftInput(Activity mActivity) {
        View view = mActivity.getCurrentFocus();
        //If no view currently has focus, onCreate a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(mActivity);
        }
        InputMethodManager im = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }


}
