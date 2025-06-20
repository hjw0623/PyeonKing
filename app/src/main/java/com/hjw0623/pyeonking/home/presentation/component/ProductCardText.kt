package com.hjw0623.pyeonking.home.presentation.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ProductCardTextLarge(
    string: String,
    modifier: Modifier = Modifier,
    color: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onBackground
) {
    Text(
        text = string,
        style = MaterialTheme.typography.titleMedium,
        color = color,
        modifier = modifier
    )
}