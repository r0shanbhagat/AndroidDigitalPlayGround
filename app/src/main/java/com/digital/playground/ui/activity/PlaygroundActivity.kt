package com.digital.playground.ui.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.digital.playground.R
import com.digital.playground.core.BaseActivity
import com.digital.playground.databinding.ActivityPlaygroundBinding
import com.digital.playground.ui.viewmodel.PlaygroundViewModel
import com.digital.playground.util.AppUtils.showLog
import dagger.hilt.android.AndroidEntryPoint
/**
 * @Details PlaygroundActivity
 * @Author Roshan Bhagat
 */
@AndroidEntryPoint
class PlaygroundActivity : BaseActivity<ActivityPlaygroundBinding, PlaygroundViewModel>() {
    private val playgroundViewModel: PlaygroundViewModel by viewModels()
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun createViewModel(): PlaygroundViewModel = playgroundViewModel

    override val layoutResId: Int = R.layout.activity_playground

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
        setSupportActionBar(binding.toolbar)
        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        //Adding OnDestinationChangedListener
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            showLog(
                PlaygroundActivity::class.java.simpleName,
                destination.label.toString()
            )
        }

    }


    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    /**
     * After android sdk 30 onwards there's no separate activity for Splash we need merge splash
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