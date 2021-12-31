package com.digital.playground.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavOptions
import com.digital.playground.BuildConfig
import com.digital.playground.R
import com.digital.playground.core.BaseActivity
import com.digital.playground.core.BaseViewModel

/**
 * The type App utils.
 */
object AppUtils {
    /**
     * To check whether network is available or not in device.
     *
     * @param context the context
     * @return boolean boolean
     */
    fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                || capabilities.hasTransport(
            NetworkCapabilities.TRANSPORT_VPN
        ))
    }

    /**
     * Show log. You can disable Logs by setting "BuildConfig.LOG" value to False
     *
     * @param tagName the tag name
     * @param message the message
     */
    fun showLog(tagName: String?, message: String) {
        if (BuildConfig.DEBUG && !TextUtils.isEmpty(message)) {
            val maxLogSize = message.length + 1
            for (i in 0..message.length / maxLogSize) {
                val start = i * maxLogSize
                var end = (i + 1) * maxLogSize
                end = if (end > message.length)
                    message.length
                else end
                Log.v(tagName, message.substring(start, end))
            }
        }
    }

    /**
     * Print Exceptions in Log.
     *
     * @param t :Throwable
     */
    fun showException(t: Throwable?) {
        if (BuildConfig.DEBUG) {
            Log.e("", Log.getStackTraceString(t))
        }
    }

    /**
     * Hide soft keyboard from view
     *
     * @param mActivity :Activity
     */
    fun hideSoftInput(context: Context) {
        val activity = context as Activity
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(
            activity.currentFocus?.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm =
                view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    fun navigateToActivity(
        callerActivity: BaseActivity<ViewDataBinding, BaseViewModel>,
        targetActivity: Class<out Activity>,
        dataBundle: Bundle? = null,
        isToFinishActivity: Boolean = false,
    ) {
        val intent = Intent(callerActivity.applicationContext, targetActivity)
        dataBundle?.let { intent.putExtras(it) }
        callerActivity.startActivity(intent)
        if (isToFinishActivity) {
            callerActivity.finish()
        }

    }

    /**
     * @method used to apply animation in fragment navigation
     */
    fun applyAnimation(navOptionBuilder: NavOptions.Builder) {
        navOptionBuilder.apply {
            setEnterAnim(R.anim.right_in)
            setExitAnim(R.anim.left_out)
            setPopExitAnim(R.anim.right_out)
            setPopEnterAnim(R.anim.left_in)
        }
    }

    /**
     * @method used to apply animation when current screen need to be popped out
     */
    fun applyReverseAnimation(navOptionBuilder: NavOptions.Builder) {
        navOptionBuilder.apply {
            setEnterAnim(R.anim.left_in)
            setExitAnim(R.anim.left_out)
            setPopExitAnim(R.anim.right_out)
            setPopEnterAnim(R.anim.left_in)
        }
    }

    fun isListNotEmpty(list: List<Any>?) = !(list?.isEmpty() ?: true)
}