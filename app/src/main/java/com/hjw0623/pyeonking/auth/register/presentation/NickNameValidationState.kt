package com.hjw0623.pyeonking.auth.register.presentation

sealed interface NicknameValidationState {
    object Idle : NicknameValidationState
    object Checking : NicknameValidationState
    object Valid : NicknameValidationState
    data class Invalid(val message: String) : NicknameValidationState
}