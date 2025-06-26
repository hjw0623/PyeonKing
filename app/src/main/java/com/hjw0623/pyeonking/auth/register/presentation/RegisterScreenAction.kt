package com.hjw0623.pyeonking.auth.register.presentation

sealed interface RegisterScreenAction {
    data class OnIdChanged(val id: String) : RegisterScreenAction
    data class OnPasswordChanged(val password: String) : RegisterScreenAction
    data class OnConfirmPasswordChanged(val confirmPassword: String) : RegisterScreenAction
    data class OnNicknameChanged(val nickname: String) : RegisterScreenAction
    data object OnNicknameCheckClick : RegisterScreenAction
    data object OnRegisterClick : RegisterScreenAction
    data object OnBackClick : RegisterScreenAction
}