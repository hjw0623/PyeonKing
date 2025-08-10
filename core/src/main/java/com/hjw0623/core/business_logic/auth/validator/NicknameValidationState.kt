package com.hjw0623.core.business_logic.auth.validator

sealed interface NicknameValidationState {
    data object Idle : NicknameValidationState
    data object Checking : NicknameValidationState
    data object Valid : NicknameValidationState
    data class Invalid(val message: String) : NicknameValidationState
}