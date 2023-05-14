package com.digital.playground.ui.view

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.digital.playground.R
import com.digital.playground.core.BaseActivity
import com.digital.playground.databinding.ActivityMovieBinding
import dagger.hilt.android.AndroidEntryPoint


/**
/**
 * @Details MovieActivity : Main activity where user0interface will
 * be displayed and user can interact with app on launch
 * @Author Roshan Bhagat
*/ *
 * @constructor Create Movie Activity
 */
@AndroidEntryPoint
class MovieActivity(
    override val layoutResId: Int = R.layout.activity_movie
) : BaseActivity<ActivityMovieBinding>() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addNavigateCallback()
    }

    /**
     * Adding Navigation Callback
     */
    private fun addNavigateCallback() {
        setSupportActionBar(binding.toolbar)
        navController = findNavController(R.id.nav_host_fragment)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}