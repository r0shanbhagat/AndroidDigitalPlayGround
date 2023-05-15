package com.digital.playground

import android.app.Application
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.digital.playground.ui.view.screens.LoginScreen
import com.digital.playground.ui.view.screens.SignUpScreen
import com.digital.playground.ui.view.screens.TermsAndConditionsScreen
import com.nativemobilebits.loginflow.navigation.PostOfficeAppRouter
import com.nativemobilebits.loginflow.navigation.Screen
import dagger.hilt.android.HiltAndroidApp

/**
 * @Details PlaygroundApp is the Application class.
 * @Author Roshan Bhagat
 */
@HiltAndroidApp
class PlaygroundApp : Application()

@Composable
fun PostOfficeApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {

        Crossfade(targetState = PostOfficeAppRouter.currentScreen) { currentState ->
            when (currentState.value) {
                is Screen.SignUpScreen -> {
                    SignUpScreen()
                }

                is Screen.TermsAndConditionsScreen -> {
                    TermsAndConditionsScreen()
                }

                is Screen.LoginScreen -> {
                    LoginScreen()
                }
            }
        }

    }
}
