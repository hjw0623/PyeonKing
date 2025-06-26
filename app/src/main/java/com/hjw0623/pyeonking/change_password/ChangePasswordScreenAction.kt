package com.hjw0623.pyeonking.change_password

sealed interface ChangePasswordScreenAction {
    data class OnCurrentPasswordChanged(val password: String) : ChangePasswordScreenAction
    data class OnNewPasswordChanged(val password: String) : ChangePasswordScreenAction
    data class OnConfirmPasswordChanged(val confirmPassword: String) : ChangePasswordScreenAction
    data object OnChangePasswordClick : ChangePasswordScreenAction
    data object OnBackClick : ChangePasswordScreenAction
}