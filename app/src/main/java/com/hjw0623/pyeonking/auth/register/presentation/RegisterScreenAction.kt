package com.hjw0623.pyeonking.auth.register.presentation

sealed interface RegisterScreenAction {
    data class OnIdChanged(val id: String) : RegisterScreenAction
    data class OnPasswordChanged(val password: String) : RegisterScreenAction
    data class OnConfirmPasswordChanged(val confirmPassword: String) : RegisterScreenAction
    data class OnNicknameChanged(val nickname: String) : RegisterScreenAction
    object OnNicknameCheckClick : RegisterScreenAction
    object OnRegisterClick : RegisterScreenAction
    object OnBackClick : RegisterScreenAction
}