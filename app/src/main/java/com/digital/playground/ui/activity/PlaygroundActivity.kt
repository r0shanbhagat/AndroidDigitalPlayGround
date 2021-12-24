package com.digital.playground.ui.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import com.digital.playground.R
import com.digital.playground.core.BaseActivity
import com.digital.playground.databinding.ActivityPlaygroundBinding
import com.digital.playground.ui.viewmodel.PlaygroundViewModel
import com.digital.playground.util.AppUtils.showLog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaygroundActivity : BaseActivity<ActivityPlaygroundBinding, PlaygroundViewModel>() {

    override fun createViewModel(): PlaygroundViewModel {
        return ViewModelProvider(this).get(PlaygroundViewModel::class.java)
    }

    override val layoutResId: Int
        get() = R.layout.activity_playground

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSplash()
        /*
         * Adding Navigation Callback for Analytics
         */
        addNavigateCallback()
    }

    /**
     * Adding Navigation Callback
     */
    private fun addNavigateCallback() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        var navController: NavController? = null
        if (navHostFragment != null) {
            navController = navHostFragment.navController
        }
        //Adding OnDestinationChangedListener
        navController?.addOnDestinationChangedListener { _: NavController?, destination: NavDestination, _: Bundle? ->
            showLog(
                PlaygroundActivity::class.java.simpleName,
                destination.label.toString()
            )
        }
    }

    /**
     * After android sdk 30 onwards there's no seperate activity for Splash we need merge splash
     * activity code into this
     */
    private fun initSplash() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val content: View = findViewById(android.R.id.content)
            content.viewTreeObserver.addOnPreDrawListener(object :
                ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean =
                    when {
                        viewModel.isSplashTimeout() -> {
                            content.viewTreeObserver.removeOnPreDrawListener(this)
                            true
                        }
                        else -> false
                    }
            })
        }
    }
}