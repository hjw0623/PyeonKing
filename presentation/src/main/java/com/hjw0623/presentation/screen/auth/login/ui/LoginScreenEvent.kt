package com.hjw0623.presentation.screen.auth.login.ui

sealed interface LoginScreenEvent {
    data class Error(val error: String) : LoginScreenEvent
    data object NavigateToMyPage : LoginScreenEvent
    data object NavigateToRegister : LoginScreenEvent
}