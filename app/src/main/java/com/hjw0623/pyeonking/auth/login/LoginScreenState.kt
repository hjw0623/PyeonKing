package com.hjw0623.pyeonking.auth.login

data class LoginScreenState(
    val email: String = "",
    val isEmailValid: Boolean = false,
    val password: String = "",
    val isPasswordVisible: Boolean = false,
)