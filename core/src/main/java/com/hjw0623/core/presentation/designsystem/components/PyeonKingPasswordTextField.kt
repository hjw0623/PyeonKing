package com.hjw0623.core.presentation.designsystem.components

import androidx.compose.foundation.text.KeyboardOptions
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
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    onDebouncedValueChange: ((String) -> Unit)? = null,
    debounceInterval: Long = DEBOUNCE_INTERVAL,
    isPasswordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit,
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
        onDebouncedValueChange = onDebouncedValueChange,
        debounceInterval = debounceInterval,
        modifier = modifier,
        startIcon = Icons.Default.Lock,
        endIcon = icon,
        onEndIconClick = onTogglePasswordVisibility,
        hint = hint,
        title = title,
        error = error,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = visualTransformation
    )
}