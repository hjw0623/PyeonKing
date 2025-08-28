package com.hjw0623.presentation.screen.auth.login.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hjw0623.core.core_ui.designsystem.components.LoadingButton
import com.hjw0623.core.core_ui.designsystem.components.PyeonKingButton
import com.hjw0623.core.core_ui.designsystem.components.PyeonKingPasswordTextField
import com.hjw0623.core.core_ui.designsystem.components.PyeonKingTextField
import com.hjw0623.core.core_ui.designsystem.components.showToast
import com.hjw0623.core.core_ui.designsystem.theme.PyeonKingTheme
import com.hjw0623.core.core_ui.ui.ObserveAsEvents
import com.hjw0623.core.core_ui.ui.rememberThrottledOnClick
import com.hjw0623.presentation.R
import com.hjw0623.presentation.screen.auth.login.ui.component.LoginGreetingSection
import com.hjw0623.presentation.screen.auth.viewmodel.LoginViewModel

@Composable
fun LoginScreenRoot(
    modifier: Modifier = Modifier,
    onNavigateToRegister: () -> Unit,
    onNavigateToMyPage: () -> Unit,
    loginViewModel: LoginViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val state by loginViewModel.state.collectAsStateWithLifecycle()

    val throttledLoginClick = rememberThrottledOnClick {
        loginViewModel.onLoginClick()
    }

    val throttledRegisterClick = rememberThrottledOnClick {
        loginViewModel.onRegisterClick()
    }

    ObserveAsEvents(flow = loginViewModel.event) { event ->
        when (event) {
            is LoginScreenEvent.Error -> {
                showToast(context, event.error)
            }

            LoginScreenEvent.NavigateToRegister -> {
                onNavigateToRegister()
            }

            LoginScreenEvent.NavigateToMyPage -> {
                showToast(context, context.getString(R.string.toast_login_success))
                onNavigateToMyPage()
            }
        }
    }

    LoginScreen(
        modifier = modifier,
        state = state,
        onEmailChange = loginViewModel::onEmailChange,
        onEmailChangeDebounced = loginViewModel::onEmailChangeDebounced,
        onPasswordChange = loginViewModel::onPasswordChange,
        onLoginClick = throttledLoginClick,
        onRegisterClick = throttledRegisterClick,
        onTogglePasswordVisibility = loginViewModel::onTogglePasswordVisibility
    )
}

@Composable
private fun LoginScreen(
    modifier: Modifier = Modifier,
    state: LoginScreenState,
    onEmailChange: (String) -> Unit,
    onEmailChangeDebounced: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onTogglePasswordVisibility: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current
    Column(
        modifier = modifier
            .padding(32.dp)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            LoginGreetingSection()

            PyeonKingTextField(
                value = state.email,
                onValueChange = onEmailChange,
                onDebouncedValueChange = onEmailChangeDebounced,
                startIcon = Icons.Default.Email,
                endIcon = if (state.isEmailValid) Icons.Default.Check else null,
                title = stringResource(R.string.label_email),
                hint = stringResource(R.string.login_hint_email),
                error = if (state.email.isNotBlank() && !state.isEmailValid) stringResource(
                    R.string.email_input_error
                ) else null,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            PyeonKingPasswordTextField(
                value = state.password,
                onValueChange = onPasswordChange,
                isPasswordVisible = state.isPasswordVisible,
                onTogglePasswordVisibility = onTogglePasswordVisibility,
                title = stringResource(R.string.label_password),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (!state.isLoggingIn) {
                            keyboard?.hide()
                            onLoginClick()
                        }
                    }
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            Column {
                LoadingButton(
                    text = stringResource(R.string.label_login),
                    onClick = onLoginClick,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = state.isLoginButtonEnabled,
                    loading = state.isLoggingIn,
                    contentPadding = PaddingValues(16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                PyeonKingButton(
                    text = stringResource(R.string.label_register),
                    onClick = onRegisterClick,
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp),
                    enabled = !state.isLoggingIn
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    PyeonKingTheme {
        LoginScreen(
            state = LoginScreenState(
                email = "test@email.com",
                password = "password",
                isEmailValid = true,
                isPasswordVisible = false,
                isLoggingIn = false
            ),
            onEmailChange = {},
            onEmailChangeDebounced = {},
            onPasswordChange = {},
            onLoginClick = {},
            onRegisterClick = {},
            onTogglePasswordVisibility = {}
        )
    }
}