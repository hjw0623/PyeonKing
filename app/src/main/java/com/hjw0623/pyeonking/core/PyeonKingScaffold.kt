package com.hjw0623.pyeonking.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PyeonKingScaffold(
    modifier: Modifier = Modifier,
    topAppBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = topAppBar,
        bottomBar = bottomBar,
        floatingActionButton = floatingActionButton,
        contentWindowInsets = WindowInsets(0,0,0,0)
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .padding(WindowInsets.systemBars.asPaddingValues())
        ) {
            content(padding)
        }
    }
}