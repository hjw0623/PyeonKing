package com.hjw0623.presentation.screen.home.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ProductCardTextLarge(
    string: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onBackground
) {
    Text(
        text = string,
        style = MaterialTheme.typography.titleMedium,
        color = color,
        modifier = modifier
    )
}