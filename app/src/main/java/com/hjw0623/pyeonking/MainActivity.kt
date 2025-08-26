package com.hjw0623.pyeonking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.hjw0623.core.presentation.designsystem.theme.PyeonKingTheme
import com.hjw0623.presentation.screen.splash.SplashScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PyeonKingTheme {
                var isSplashShown by remember { mutableStateOf(true) }

                LaunchedEffect(Unit) {
                    delay(2000L)
                    isSplashShown = false
                }

                if (isSplashShown) {
                    SplashScreen()
                } else {
                    MainScreen()
                }
            }
        }
    }
}
