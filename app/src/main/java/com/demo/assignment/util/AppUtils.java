package com.demo.assignment.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.navigation.NavOptions;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.demo.assignment.BuildConfig;
import com.demo.assignment.R;
import com.demo.assignment.repository.ApiConstants;
import com.demo.assignment.repository.model.MoviesModel;
import com.demo.assignment.ui.callback.ImageLoadingStatus;

import java.util.List;

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
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) isConnected = true;
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


    /**
     * Sets recycler basic properties.
     *
     * @param context      the context
     * @param recyclerView the recycler view
     */
    public static void setRecyclerViewProp(Context context, RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
    }

    /**
     * @param list:List
     * @return :boolean
     */
    public static boolean isListNotEmpty(List<?> list) {
        return null != list && !list.isEmpty();
    }

    /**
     * @param moviesModel :MoviesModel
     * @return :String
     */
    public static String formatYearLabel(MoviesModel moviesModel) {
        return moviesModel.getReleaseDate().substring(0, 4)  // we want the year only
                + " | "
                + moviesModel.getOriginalLanguage().toUpperCase();
    }

    /**
     * @param ivImage                   : ImageView
     * @param itemData:String
     * @param status:ImageLoadingStatus
     */
    public static void loadImageWithCallback(@NonNull ImageView ivImage, String itemData, ImageLoadingStatus status) {
        Glide.with(ivImage.getContext())
                .load(ApiConstants.BASE_URL_IMG + itemData)
                .diskCacheStrategy(DiskCacheStrategy.ALL)   // cache both original & resized image
                .centerCrop()
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        status.onComplete();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        status.onComplete();
                        return false;
                    }
                })
                .into(ivImage);
    }
}
