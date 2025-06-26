package com.hjw0623.pyeonking.change_nickname

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hjw0623.pyeonking.R
import com.hjw0623.pyeonking.auth.mockTakenNicknames
import com.hjw0623.pyeonking.auth.register.presentation.NicknameValidationState
import com.hjw0623.pyeonking.auth.register.presentation.component.PyeonKingOutlinedTextField
import com.hjw0623.pyeonking.core.presentation.designsystem.util.BackBar
import com.hjw0623.pyeonking.core.presentation.designsystem.util.PyeonKingButton
import com.hjw0623.pyeonking.ui.theme.PyeonKingTheme

@Composable
fun ChangeNicknameScreenRoot(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    onNicknameChangeSuccess: () -> Unit,
) {
    var state by remember { mutableStateOf(ChangeNicknameScreenState()) }
    val context = LocalContext.current

    ChangeNicknameScreen(
        state = state,
        onAction = { action ->
            when (action) {
                ChangeNicknameScreenAction.OnBackClick -> onNavigateBack()
                ChangeNicknameScreenAction.OnChangeNicknameClick -> onNicknameChangeSuccess()
                is ChangeNicknameScreenAction.OnNicknameChanged -> {
                    state = state.copy(
                        newNickname = action.nickname,
                        nicknameValidationState = NicknameValidationState.Idle
                    )
                }

                ChangeNicknameScreenAction.OnNicknameCheckClick -> {
                    if (mockTakenNicknames.contains(state.newNickname) || state.newNickname.isBlank()) {
                        state = state.copy(
                            nicknameValidationState = NicknameValidationState.Invalid(
                                context.getString(
                                    R.string.text_already_used_nickname
                                )
                            )
                        )
                    } else {
                        state = state.copy(
                            nicknameValidationState = NicknameValidationState.Valid
                        )
                    }
                }
            }

        }
    )

}

@Composable
private fun ChangeNicknameScreen(
    state: ChangeNicknameScreenState,
    onAction: (ChangeNicknameScreenAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            BackBar(
                onBackClick = { onAction(ChangeNicknameScreenAction.OnBackClick) }
            )
        },
        bottomBar = {
            PyeonKingButton(
                text = stringResource(R.string.text_change_nickname),
                onClick = { onAction(ChangeNicknameScreenAction.OnChangeNicknameClick) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                enabled = state.isChangeButtonEnabled,
                contentPadding = PaddingValues(16.dp)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(R.string.action_change_nickname),
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(R.string.input_new_nickname),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                PyeonKingOutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = state.newNickname,
                    onValueChange = { onAction(ChangeNicknameScreenAction.OnNicknameChanged(it)) },
                    label = state.currentNickname,
                    isValid = state.nicknameValidationState is NicknameValidationState.Valid,
                    supportingText = when (val validationState = state.nicknameValidationState) {
                        is NicknameValidationState.Valid -> stringResource(R.string.text_useable_nickname)
                        is NicknameValidationState.Invalid -> validationState.message
                        else -> null
                    },
                    supportingTextColor = if (state.nicknameValidationState is NicknameValidationState.Valid) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.error
                    }
                )

                Spacer(modifier = Modifier.width(18.dp))

                Box(
                    modifier = Modifier
                        .height(70.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (state.nicknameValidationState is NicknameValidationState.Checking) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    } else {
                        PyeonKingButton(
                            text = stringResource(R.string.action_duplicate_check),
                            onClick = { onAction(ChangeNicknameScreenAction.OnNicknameCheckClick) },
                            enabled = state.newNickname.isNotBlank() && state.nicknameValidationState !is NicknameValidationState.Valid,
                            modifier = Modifier.height(56.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp)
                        )
                    }
                }

            }
        }

    }
}

@Preview
@Composable
private fun ChangeNicknameScreenPreview() {
    PyeonKingTheme {
        ChangeNicknameScreen(
            state = ChangeNicknameScreenState(),
            onAction = {}
        )
    }
}