package com.hjw0623.core.presentation.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hjw0623.core.presentation.designsystem.theme.PyeonKingTheme

@Composable
fun LoadingButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    loading: Boolean,
    enabled: Boolean = true,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    fullWidth: Boolean = true,
) {
    val widthModifier = if (fullWidth) Modifier.fillMaxWidth() else Modifier
    Box(modifier) {
        PyeonKingButton(
            text = if (loading) "" else text,
            onClick = onClick,
            enabled = enabled && !loading,
            modifier = widthModifier,
            contentPadding = contentPadding
        )
        if (loading) {
            Box(Modifier.matchParentSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(Modifier.height(20.dp))
            }
        }
    }
}

@Preview
@Composable
private fun LoadingButtonPreview() {
    PyeonKingTheme {
        LoadingButton(
            text = "로그인",
            onClick = {},
            loading = true
        )
    }
}