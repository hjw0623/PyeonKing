package com.hjw0623.presentation.screen.search.text_search.component

import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.hjw0623.core.presentation.designsystem.theme.PyeonKingTheme
import com.hjw0623.presentation.R


@Composable
fun SearchTextField(
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    onClearClick: () -> Unit,
    onBackClick: () -> Unit,
    onSearchClick: () -> Unit,
    onFocusChange: (Boolean) -> Unit,
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    var isFocused by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is FocusInteraction.Focus -> {
                    isFocused = true
                    onFocusChange(true)
                }

                is FocusInteraction.Unfocus -> {
                    isFocused = false
                    onFocusChange(false)
                }
            }
        }
    }

    TextField(
        value = searchQuery,
        onValueChange = onQueryChange,
        placeholder = { Text(stringResource(R.string.search_hint)) },
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        interactionSource = interactionSource,
        singleLine = true,
        leadingIcon = {
            if (isFocused) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        },
        trailingIcon = {
            if (isFocused) {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = onClearClick) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            } else {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchClick()
                keyboardController?.hide()
            }
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}

@Preview(
    showBackground = true
)
@Composable
private fun SearchTextFieldPreview() {
    PyeonKingTheme {
        SearchTextField(
            searchQuery = "",
            onQueryChange = { },
            onClearClick = { },
            onBackClick = { },
            onSearchClick = { },
            onFocusChange = { },
            focusRequester = remember { FocusRequester() }
        )
    }
}