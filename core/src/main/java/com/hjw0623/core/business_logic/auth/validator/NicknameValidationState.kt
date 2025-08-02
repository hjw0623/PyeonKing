package com.hjw0623.core.business_logic.auth.validator

sealed interface NicknameValidationState {
    object Idle : NicknameValidationState
    object Checking : NicknameValidationState
    object Valid : NicknameValidationState
    data class Invalid(val message: String) : NicknameValidationState
}