package com.hjw0623.core.domain.auth

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
