package com.hjw0623.core.domain.auth

sealed interface NicknameValidationState {
    object Idle : NicknameValidationState
    object Checking : NicknameValidationState
    object Valid : NicknameValidationState
    data class Invalid(val message: String) : NicknameValidationState
}