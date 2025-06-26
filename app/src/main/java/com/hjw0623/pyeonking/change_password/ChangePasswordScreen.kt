package com.hjw0623.pyeonking.change_password

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hjw0623.pyeonking.R
import com.hjw0623.pyeonking.auth.register.presentation.component.PyeonKingOutlinedTextField
import com.hjw0623.pyeonking.core.presentation.designsystem.util.BackBar
import com.hjw0623.pyeonking.core.presentation.designsystem.util.PyeonKingButton
import com.hjw0623.pyeonking.ui.theme.PyeonKingTheme

@Composable
fun ChangePasswordScreenScreenRoot(
    onChangePasswordSuccess: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var state by remember { mutableStateOf(ChangePasswordScreenState()) }

    ChangePasswordScreenScreen(
        state = state,
        onAction = { action ->
            when (action) {
                ChangePasswordScreenAction.OnBackClick -> onNavigateBack()
                ChangePasswordScreenAction.OnChangePasswordClick -> onChangePasswordSuccess()
                is ChangePasswordScreenAction.OnConfirmPasswordChanged -> {
                    state = state.copy(
                        confirmPassword = action.confirmPassword
                    )
                }

                is ChangePasswordScreenAction.OnCurrentPasswordChanged -> {
                    state = state.copy(
                        currentPassword = action.password
                    )
                }

                is ChangePasswordScreenAction.OnNewPasswordChanged -> {
                    state = state.copy(
                        newPassword = action.password
                    )
                }
            }
        },
        modifier = modifier
    )
}

@Composable
private fun ChangePasswordScreenScreen(
    state: ChangePasswordScreenState,
    onAction: (ChangePasswordScreenAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            BackBar(
                onBackClick = { onAction(ChangePasswordScreenAction.OnBackClick) }
            )
        },
        bottomBar = {
            PyeonKingButton(
                text = stringResource(R.string.text_change_password),
                onClick = { onAction(ChangePasswordScreenAction.OnChangePasswordClick) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                enabled = state.isChangePasswordButtonEnabled,
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
                text = stringResource(R.string.action_change_password),
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(R.string.input_current_password_new_password),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(40.dp))

            PyeonKingOutlinedTextField(
                value = state.currentPassword,
                onValueChange = { onAction(ChangePasswordScreenAction.OnCurrentPasswordChanged(it)) },
                label = stringResource(R.string.lable_current_password),
                isValid = state.isCurrentPasswordValid,
                supportingText =
                    if (state.currentPassword.isNotBlank() && !state.isCurrentPasswordValid) "현재 비밀버호와 일치하지 않습니다."
                    else null,
                isPassword = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            PyeonKingOutlinedTextField(
                value = state.newPassword,
                onValueChange = { onAction(ChangePasswordScreenAction.OnNewPasswordChanged(it)) },
                label = stringResource(R.string.label_new_password),
                isValid = state.isNewPasswordValid,
                supportingText = if (state.newPassword.isNotBlank() && !state.isNewPasswordValid) stringResource(
                    R.string.signup_password_validation_rule
                ) else null,
                isPassword = true
            )
            Spacer(modifier = Modifier.height(16.dp))

            PyeonKingOutlinedTextField(
                value = state.confirmPassword,
                onValueChange = { onAction(ChangePasswordScreenAction.OnConfirmPasswordChanged(it)) },
                label = stringResource(R.string.label_new_password_confirm),
                isValid = state.isConfirmPasswordValid,
                supportingText = if (state.confirmPassword.isNotBlank() && !state.isConfirmPasswordValid) stringResource(
                    R.string.signup_error_password_mismatch
                ) else null,
                isPassword = true
            )
        }
    }
}


@Preview
@Composable
private fun ChangePasswordScreenScreenPreview() {
    PyeonKingTheme {
        ChangePasswordScreenScreen(
            state = ChangePasswordScreenState(),
            onAction = {}
        )
    }
}