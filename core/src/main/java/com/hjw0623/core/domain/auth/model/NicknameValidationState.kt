package com.hjw0623.core.domain.auth.model

sealed interface NicknameValidationState {
    data object Idle : NicknameValidationState
    data object Checking : NicknameValidationState
    data object Valid : NicknameValidationState
    data class Invalid(val message: String) : NicknameValidationState
}