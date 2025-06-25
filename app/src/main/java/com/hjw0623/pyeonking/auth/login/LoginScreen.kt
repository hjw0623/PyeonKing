package com.hjw0623.pyeonking.auth.login

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
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
import com.hjw0623.pyeonking.auth.component.LoginGreetingSection
import com.hjw0623.pyeonking.auth.component.LoginInputFields
import com.hjw0623.pyeonking.core.presentation.designsystem.util.BackBar
import com.hjw0623.pyeonking.core.presentation.designsystem.util.PyeonKingButton
import com.hjw0623.pyeonking.ui.theme.PyeonKingTheme

@Composable
fun LoginScreenRoot() {
    var state by remember { mutableStateOf(LoginScreenState()) }

    LoginScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is LoginScreenAction.OnEmailChanged -> {
                    state = state.copy(
                        email = action.email,
                        isEmailValid = Patterns.EMAIL_ADDRESS.matcher(action.email).matches()
                    )
                }

                is LoginScreenAction.OnPasswordChanged -> {
                    state = state.copy(password = action.password)
                }

                LoginScreenAction.OnTogglePasswordVisibility -> {
                    state = state.copy(isPasswordVisible = !state.isPasswordVisible)
                }

                LoginScreenAction.OnLoginClick -> {}
                LoginScreenAction.OnSignUpClick -> {}
                LoginScreenAction.OnBackClick -> {}
            }
        }
    )
}

@Composable
fun LoginScreen(
    state: LoginScreenState,
    onAction: (LoginScreenAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        BackBar(
            onBackClick = { onAction(LoginScreenAction.OnBackClick) },
            title = stringResource(R.string.label_login),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        LoginGreetingSection()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            LoginInputFields(
                email = state.email,
                onEmailChange = { onAction(LoginScreenAction.OnEmailChanged(it)) },
                isEmailValid = state.isEmailValid,
                password = state.password,
                onPasswordChange = { onAction(LoginScreenAction.OnPasswordChanged(it)) },
                isPasswordVisible = state.isPasswordVisible,
                onTogglePasswordVisibility = { onAction(LoginScreenAction.OnTogglePasswordVisibility) }
            )

            if (state.isEmailValid == false && !state.email.isEmpty()) {
                Text(
                    text = stringResource(R.string.email_input_error),
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            PyeonKingButton(
                text = stringResource(R.string.label_login),
                onClick = { onAction(LoginScreenAction.OnLoginClick) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            PyeonKingButton(
                text = stringResource(R.string.label_register),
                onClick = { onAction(LoginScreenAction.OnSignUpClick) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    PyeonKingTheme {
        LoginScreen(
            state = LoginScreenState(),
            onAction = {}
        )
    }
}