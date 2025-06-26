package com.hjw0623.pyeonking.auth.register.presentation


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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hjw0623.pyeonking.R
import com.hjw0623.pyeonking.auth.isConfirmPasswordValid
import com.hjw0623.pyeonking.auth.isEmailValid
import com.hjw0623.pyeonking.auth.isPasswordValid
import com.hjw0623.pyeonking.auth.mockTakenNicknames
import com.hjw0623.pyeonking.auth.register.presentation.component.PyeonKingOutlinedTextField
import com.hjw0623.pyeonking.core.presentation.designsystem.util.BackBar
import com.hjw0623.pyeonking.core.presentation.designsystem.util.PyeonKingButton
import com.hjw0623.pyeonking.ui.theme.PyeonKingTheme

@Composable
fun RegisterScreenRoot(
    onRegisterSuccess: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var state by remember { mutableStateOf(RegisterScreenState()) }
    val context = LocalContext.current

    RegisterScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is RegisterScreenAction.OnIdChanged -> {
                    state = state.copy(
                        email = action.id,
                        isEmailValid = isEmailValid(action.id)
                    )
                }

                is RegisterScreenAction.OnNicknameChanged -> {
                    state = state.copy(
                        nickname = action.nickname,
                        nicknameValidationState = NicknameValidationState.Idle
                    )
                }

                is RegisterScreenAction.OnNicknameCheckClick -> {
                    if (mockTakenNicknames.contains(state.nickname) || state.nickname.isBlank()) {
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

                is RegisterScreenAction.OnPasswordChanged -> {
                    val newPassword = action.password
                    state = state.copy(
                        password = newPassword,
                        isPasswordValid = isPasswordValid(newPassword),
                        isConfirmPasswordValid = isConfirmPasswordValid(
                            password = newPassword,
                            confirm = state.confirmPassword
                        )
                    )
                }

                is RegisterScreenAction.OnConfirmPasswordChanged -> {
                    val newConfirmPassword = action.confirmPassword
                    state = state.copy(
                        confirmPassword = newConfirmPassword,
                        isConfirmPasswordValid = isConfirmPasswordValid(
                            password = state.password,
                            confirm = newConfirmPassword
                        )
                    )
                }

                RegisterScreenAction.OnBackClick -> onNavigateBack()
                RegisterScreenAction.OnRegisterClick -> onRegisterSuccess()
            }
        },
        modifier = modifier
    )
}

@Composable
fun RegisterScreen(
    state: RegisterScreenState,
    onAction: (RegisterScreenAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            BackBar(
                onBackClick = { onAction(RegisterScreenAction.OnBackClick) },

                )
        },
        bottomBar = {
            PyeonKingButton(
                text = stringResource(R.string.label_register),
                onClick = { onAction(RegisterScreenAction.OnRegisterClick) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                enabled = state.isRegisterButtonEnabled,
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
                text = stringResource(R.string.label_register),
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(R.string.input_email_password),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                PyeonKingOutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = state.nickname,
                    onValueChange = { onAction(RegisterScreenAction.OnNicknameChanged(it)) },
                    label = stringResource(R.string.label_nickname),
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
                            onClick = { onAction(RegisterScreenAction.OnNicknameCheckClick) },
                            enabled = state.nickname.isNotBlank() && state.nicknameValidationState !is NicknameValidationState.Valid,
                            modifier = Modifier.height(56.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp)
                        )
                    }
                }
            }

            PyeonKingOutlinedTextField(
                value = state.email,
                onValueChange = { onAction(RegisterScreenAction.OnIdChanged(it)) },
                label = stringResource(R.string.label_email),
                placeholder = stringResource(R.string.login_hint_email),
                isValid = state.isEmailValid,
                supportingText = if (state.email.isNotBlank() && !state.isEmailValid) stringResource(
                    R.string.email_input_error
                ) else null,
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(32.dp))

            PyeonKingOutlinedTextField(
                value = state.password,
                onValueChange = { onAction(RegisterScreenAction.OnPasswordChanged(it)) },
                label = stringResource(R.string.label_password),
                isValid = state.isPasswordValid,
                supportingText = if (state.password.isNotBlank() && !state.isPasswordValid) stringResource(
                    R.string.signup_password_validation_rule
                ) else null,
                isPassword = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            PyeonKingOutlinedTextField(
                value = state.confirmPassword,
                onValueChange = { onAction(RegisterScreenAction.OnConfirmPasswordChanged(it)) },
                label = stringResource(R.string.label_confirm),
                isValid = state.isConfirmPasswordValid,
                supportingText = if (state.confirmPassword.isNotBlank() && !state.isConfirmPasswordValid) stringResource(
                    R.string.signup_error_password_mismatch
                ) else null,
                isPassword = true
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun RegisterScreenWithPyeonKingFieldPreview() {
    PyeonKingTheme {
        RegisterScreen(
            state = RegisterScreenState(
                email = "test@test.com",
                password = "Password123!",
                confirmPassword = "Password123!",
                isEmailValid = true,
                isPasswordValid = true,
                isConfirmPasswordValid = true,
                nicknameValidationState = NicknameValidationState.Valid
            ),
            onAction = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterScreenWithPyeonKingFieldErrorPreview() {
    RegisterScreen(
        state = RegisterScreenState(
            email = "test@",
            password = "pw",
            confirmPassword = "pW",
            isEmailValid = false,
            isPasswordValid = false,
            isConfirmPasswordValid = false
        ),
        onAction = {}
    )
}
