@file:OptIn(ExperimentalMaterial3Api::class)

package com.hjw0623.pyeonking.auth.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hjw0623.pyeonking.R
import com.hjw0623.pyeonking.auth.isEmailValid
import com.hjw0623.pyeonking.auth.login.component.LoginGreetingSection
import com.hjw0623.pyeonking.auth.register.presentation.component.PyeonKingOutlinedTextField
import com.hjw0623.pyeonking.core.presentation.designsystem.util.BackBar
import com.hjw0623.pyeonking.core.presentation.designsystem.util.PyeonKingButton
import com.hjw0623.pyeonking.ui.theme.PyeonKingTheme

@Composable
fun LoginScreenRoot(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var state by remember { mutableStateOf(LoginScreenState()) }

    LoginScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is LoginScreenAction.OnEmailChanged -> {
                    state = state.copy(
                        email = action.email,
                        isEmailValid = isEmailValid(action.email)
                    )
                }

                is LoginScreenAction.OnPasswordChanged -> {
                    state = state.copy(
                        password = action.password,
                        isPasswordNotBlank = action.password.isNotBlank()
                    )
                }

                LoginScreenAction.OnLoginClick -> onLoginSuccess()
                LoginScreenAction.OnRegisterClick -> onNavigateToRegister()
                LoginScreenAction.OnBackClick -> onNavigateBack()
            }
        },
        modifier = modifier
    )
}

@Composable
fun LoginScreen(
    state: LoginScreenState,
    onAction: (LoginScreenAction) -> Unit,
    modifier: Modifier = Modifier,
) {

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            BackBar(
                onBackClick = { onAction(LoginScreenAction.OnBackClick) },
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(32.dp)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                LoginGreetingSection()


                PyeonKingOutlinedTextField(
                    value = state.email,
                    onValueChange = { onAction(LoginScreenAction.OnEmailChanged(it)) },
                    label = stringResource(R.string.label_email),
                    placeholder = stringResource(R.string.login_hint_email),
                    isValid = state.isEmailValid,
                    supportingText = if (state.email.isNotBlank() && !state.isEmailValid) stringResource(
                        R.string.email_input_error
                    ) else null,
                    keyboardType = KeyboardType.Email
                )

                Spacer(modifier = Modifier.height(16.dp))

                PyeonKingOutlinedTextField(
                    value = state.password,
                    onValueChange = { onAction(LoginScreenAction.OnPasswordChanged(it)) },
                    label = stringResource(R.string.label_password),
                    isValid = true,
                    isPassword = true,
                )

                Spacer(modifier = Modifier.height(16.dp))

                Column {
                    if (state.isLoginButtonEnabled) {
                        PyeonKingButton(
                            text = stringResource(R.string.label_login),
                            onClick = { onAction(LoginScreenAction.OnLoginClick) },
                            modifier = Modifier
                                .fillMaxWidth(),
                            enabled = state.isLoginButtonEnabled,
                            contentPadding = PaddingValues(16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    PyeonKingButton(
                        text = stringResource(R.string.label_register),
                        onClick = { onAction(LoginScreenAction.OnRegisterClick) },
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentPadding = PaddingValues(16.dp)
                    )
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview_Enabled() {
    MaterialTheme {
        PyeonKingTheme {
            LoginScreen(
                state = LoginScreenState(
                    email = "test@email.com",
                    isEmailValid = true,
                    password = "password",
                    isPasswordNotBlank = true
                ),
                onAction = {}
            )

        }
    }
}