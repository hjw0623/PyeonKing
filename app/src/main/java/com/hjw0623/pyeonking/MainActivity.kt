package com.hjw0623.pyeonking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.hjw0623.pyeonking.core.presentation.ui.theme.PyeonKingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PyeonKingTheme {
                MainScreen()
            }
        }
    }
}
