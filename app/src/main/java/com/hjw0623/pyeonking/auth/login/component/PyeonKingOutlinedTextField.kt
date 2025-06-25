package com.hjw0623.pyeonking.auth.login.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.hjw0623.pyeonking.R
import com.hjw0623.pyeonking.ui.theme.PyeonKingTheme

@Composable
fun PyeonKingOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isValid: Boolean?,
    modifier: Modifier = Modifier,
) {
    var isFocused by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            placeholder = {
                Text(
                    text = stringResource(R.string.email_input_hint)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },
            trailingIcon = {
                when {
                    isFocused && value.isNotEmpty() -> {
                        IconButton(onClick = { onValueChange("") }) {
                            Icon(
                                Icons.Default.Clear,
                                contentDescription = null,
                                tint = Color.Gray
                            )
                        }
                    }

                    !isFocused && value.isNotEmpty() && isValid == true -> {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.Green
                        )
                    }
                }
            },
            isError = (!isFocused && value.isNotEmpty() && isValid == false)
        )
    }
}


@Preview
@Composable
private fun PyeonKingOutlinedTextFieldPreview() {
    PyeonKingTheme {
        PyeonKingOutlinedTextField(
            value = "",
            onValueChange = {},
            label = "이메일",
            isValid = true,
        )
    }
}