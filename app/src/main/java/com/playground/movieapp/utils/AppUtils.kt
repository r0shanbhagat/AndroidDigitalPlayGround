package com.playground.movieapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.text.TextUtils
import android.util.Log
import androidx.navigation.NavOptions
import com.playground.movieapp.BuildConfig
import com.playground.movieapp.R

/**
 * @Details AppUtils: Common Utility Class to handle the utils functions
 * @Author Roshan Bhagat
 */

/**
 * Is network connected check and return the n/w availability of user's device
 *
 * @param context
 * @return boolean true isNetworkConnected else false
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
 * Show log
 *
 * @param tagName
 * @param message
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
 * Log exception
 *
 * @param t
 */
fun logException(t: Throwable?) {
    if (BuildConfig.DEBUG) {
        Log.e("", Log.getStackTraceString(t))
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
 * Is list not empty
 *
 * @param list
 */
fun isListNotEmpty(list: List<Any>?) = !(list?.isEmpty() ?: true)

/**
 * Is valid string
 *
 * @param value
 */
fun isValidString(value: String?) = !TextUtils.isEmpty(value)