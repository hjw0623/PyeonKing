package com.hjw0623.presentation.screen.auth.register

sealed interface RegisterScreenEvent {
    data class Error(val error: String) : RegisterScreenEvent
    data object NavigateToRegisterSuccess : RegisterScreenEvent
}