package com.digital.playground.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.digital.playground.PostOfficeApp

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
class MovieActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PostOfficeApp()
        }
    }

    @Preview
    @Composable
    fun DefaultPreview() {
        PostOfficeApp()
    }
}