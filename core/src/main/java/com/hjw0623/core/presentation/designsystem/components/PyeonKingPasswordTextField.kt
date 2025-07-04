package com.hjw0623.core.presentation.designsystem.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun PyeonKingPasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "",
    title: String? = null,
    error: String? = null
) {
    val icon = if (isPasswordVisible) {
        Icons.Default.Visibility
    } else {
        Icons.Default.VisibilityOff
    }

    val visualTransformation = if (isPasswordVisible) {
        VisualTransformation.None
    } else {
        PasswordVisualTransformation()
    }

    PyeonKingTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        startIcon = Icons.Default.Lock,
        endIcon = icon,
        onEndIconClick = onTogglePasswordVisibility,
        hint = hint,
        title = title,
        error = error,
        keyboardType = KeyboardType.Password,
        visualTransformation = visualTransformation
    )
}