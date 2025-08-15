package com.hjw0623.presentation.screen.auth.login.ui

data class LoginScreenState(
    val email: String = "",
    val password: String = "",
    val isEmailValid: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val isLoggingIn: Boolean = false,
) {
    val isLoginButtonEnabled: Boolean
        get() = isEmailValid && password.isNotBlank()
}