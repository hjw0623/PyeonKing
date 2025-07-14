package com.hjw0623.presentation.screen.auth.register.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun PyeonKingOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isValid: Boolean,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    supportingText: String? = null,
    supportingTextColor: Color = MaterialTheme.colorScheme.error,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    var isFocused by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            },
        label = { Text(label) },
        placeholder = {
            if (placeholder != null) {
                Text(text = placeholder)
            }
        },
        visualTransformation = if (isPassword && !passwordVisible) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = if (isPassword) KeyboardType.Password else keyboardType
        ),
        trailingIcon = {
            if (isPassword) {
                val image = if (passwordVisible)
                    Icons.Filled.Visibility
                else
                    Icons.Filled.VisibilityOff


                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = image,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            } else {
                when {
                    isFocused && value.isNotEmpty() -> {
                        IconButton(onClick = { onValueChange("") }) {
                            Icon(
                                Icons.Default.Clear,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    !isFocused && value.isNotEmpty() && isValid -> {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        },
        isError = !isFocused && value.isNotEmpty() && !isValid,
        supportingText = {
            if (supportingText != null) {
                Text(
                    text = supportingText,
                    color = supportingTextColor
                )
            }
        }
    )
}