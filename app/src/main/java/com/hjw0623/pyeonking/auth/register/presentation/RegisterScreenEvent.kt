package com.hjw0623.pyeonking.auth.register.presentation

sealed interface RegisterScreenEvent {
    data class Error(val error: String) : RegisterScreenEvent
    data object NavigateToRegisterSuccess : RegisterScreenEvent
}