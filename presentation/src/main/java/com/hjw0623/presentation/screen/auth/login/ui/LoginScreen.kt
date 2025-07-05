package com.hjw0623.presentation.screen.auth.login.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hjw0623.core.domain.auth.EmailPatternValidator
import com.hjw0623.core.domain.auth.UserDataValidator
import com.hjw0623.core.presentation.designsystem.components.PyeonKingButton
import com.hjw0623.core.presentation.designsystem.components.PyeonKingPasswordTextField
import com.hjw0623.core.presentation.designsystem.components.PyeonKingTextField
import com.hjw0623.core.presentation.designsystem.components.showToast
import com.hjw0623.core.presentation.designsystem.theme.PyeonKingTheme
import com.hjw0623.core.presentation.ui.ObserveAsEvents
import com.hjw0623.core.presentation.ui.rememberThrottledOnClick
import com.hjw0623.presentation.R
import com.hjw0623.presentation.screen.auth.login.ui.component.LoginGreetingSection
import com.hjw0623.presentation.screen.auth.viewmodel.LoginViewModel
import com.hjw0623.presentation.screen.factory.LoginViewModelFactory

@Composable
fun LoginScreenRoot(
    onNavigateToRegister: () -> Unit,
    onNavigateToMyPage: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val userDataValidator = UserDataValidator(EmailPatternValidator)
    val loginViewModelFactory = LoginViewModelFactory(userDataValidator)
    val viewModel: LoginViewModel = viewModel(factory = loginViewModelFactory)

    val context = LocalContext.current

    val email by viewModel.email.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    val isEmailValid by viewModel.isEmailValid.collectAsStateWithLifecycle()
    val isPasswordVisible by viewModel.isPasswordVisible.collectAsStateWithLifecycle()
    val isLoginButtonEnabled by viewModel.isLoginButtonEnabled.collectAsStateWithLifecycle()

    val throttledLoginClick = rememberThrottledOnClick {
        viewModel.onLoginClick()
    }

    val throttledRegisterClick = rememberThrottledOnClick {
        viewModel.onRegisterClick()
    }

    ObserveAsEvents(flow = viewModel.event) { event ->
        when (event) {
            is LoginScreenEvent.Error -> {
                showToast(context, event.error)
            }

            LoginScreenEvent.NavigateToRegister -> {
                onNavigateToRegister()
            }

            LoginScreenEvent.NavigateToMyPage -> {
                onNavigateToMyPage()
            }
        }
    }

    LoginScreen(
        modifier = modifier,
        email = email,
        password = password,
        isEmailValid = isEmailValid,
        isPasswordVisible = isPasswordVisible,
        isLoginButtonEnabled = isLoginButtonEnabled,
        onEmailChange = viewModel::onEmailChange,
        onEmailChangeDebounced = viewModel::onEmailChangeDebounced,
        onPasswordChange = viewModel::onPasswordChange,
        onLoginClick = throttledLoginClick,
        onRegisterClick = throttledRegisterClick,
        onTogglePasswordVisibility = viewModel::onTogglePasswordVisibility
    )
}

@Composable
private fun LoginScreen(
    modifier: Modifier = Modifier,
    email: String,
    password: String,
    isEmailValid: Boolean,
    isPasswordVisible: Boolean,
    isLoginButtonEnabled: Boolean,
    onEmailChange: (String) -> Unit,
    onEmailChangeDebounced: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onTogglePasswordVisibility: () -> Unit,
) {
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
                value = email,
                onValueChange = onEmailChange,
                onDebouncedValueChange = onEmailChangeDebounced,
                startIcon = Icons.Default.Email,
                endIcon = if (isEmailValid) Icons.Default.Check else null,
                title = stringResource(R.string.label_email),
                hint = stringResource(R.string.login_hint_email),
                error = if (email.isNotBlank() && !isEmailValid) stringResource(
                    R.string.email_input_error
                ) else null,
                keyboardType = KeyboardType.Email,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            PyeonKingPasswordTextField(
                value = password,
                onValueChange = onPasswordChange,
                isPasswordVisible = isPasswordVisible,
                onTogglePasswordVisibility = onTogglePasswordVisibility,
                title = stringResource(R.string.label_password),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            Column {
                PyeonKingButton(
                    text = stringResource(R.string.label_login),
                    onClick = onLoginClick,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isLoginButtonEnabled,
                    contentPadding = PaddingValues(16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                PyeonKingButton(
                    text = stringResource(R.string.label_register),
                    onClick = onRegisterClick,
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp)
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
            email = "test@email.com",
            password = "password",
            isEmailValid = true,
            isPasswordVisible = false,
            isLoginButtonEnabled = true,
            onEmailChange = {},
            onPasswordChange = {},
            onLoginClick = {},
            onRegisterClick = {},
            onTogglePasswordVisibility = {},
            onEmailChangeDebounced = {}
        )
    }
}