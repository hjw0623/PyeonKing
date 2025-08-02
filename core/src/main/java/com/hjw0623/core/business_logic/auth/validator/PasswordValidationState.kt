package com.hjw0623.core.business_logic.auth.validator

data class PasswordValidationState(
    val hasMinLength: Boolean = false,
    val hasMaxLength: Boolean = false,
    val hasNumber: Boolean = false,
    val hasUpperCase: Boolean = false,
    val hasLowerCase: Boolean = false,
    val hasSpecialCharacter: Boolean = false
) {
    val isValidPassword: Boolean
        get() = hasMinLength && hasNumber && hasUpperCase && hasLowerCase && hasSpecialCharacter
}