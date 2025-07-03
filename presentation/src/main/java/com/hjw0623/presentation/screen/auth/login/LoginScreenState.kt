package com.hjw0623.presentation.screen.auth.login

data class LoginScreenState(
    val email: String = "",
    val isEmailValid: Boolean = false,
    val password: String = "",
    val isPasswordNotBlank: Boolean = false,
) {
    val isLoginButtonEnabled: Boolean
        get() = isEmailValid && isPasswordNotBlank
}