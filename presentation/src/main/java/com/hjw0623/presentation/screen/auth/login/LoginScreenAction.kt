package com.hjw0623.presentation.screen.auth.login

sealed interface LoginScreenAction {
    data class OnEmailChanged(val email: String) : LoginScreenAction
    data class OnPasswordChanged(val password: String) : LoginScreenAction
    data object OnLoginClick : LoginScreenAction
    data object OnRegisterClick : LoginScreenAction
}