package com.hjw0623.core.core_domain.auth.validator

sealed interface NicknameValidationState {
    data object Idle : NicknameValidationState
    data object Checking : NicknameValidationState
    data object Valid : NicknameValidationState
    data class Invalid(val message: String) : NicknameValidationState
}