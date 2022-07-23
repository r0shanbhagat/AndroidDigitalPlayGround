package com.playground.movieapp.ui.view

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.playground.movieapp.R
import com.playground.movieapp.core.BaseActivity
import com.playground.movieapp.core.BaseViewModel
import com.playground.movieapp.databinding.ActivityMovieBinding
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
) : BaseActivity<ActivityMovieBinding, BaseViewModel>() {

    private lateinit var navController: NavController


    override fun createViewModel(): BaseViewModel? = null

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