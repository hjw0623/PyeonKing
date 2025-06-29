package com.hjw0623.pyeonking.change_password

sealed interface ChangePasswordScreenEvent {
    data class Error(val error: String) : ChangePasswordScreenEvent
    data object NavigateToMyPage : ChangePasswordScreenEvent
}