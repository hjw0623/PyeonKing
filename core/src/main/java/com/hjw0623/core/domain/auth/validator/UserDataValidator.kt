package com.hjw0623.core.domain.auth.validator

import com.hjw0623.core.domain.auth.model.PasswordValidationState

class UserDataValidator(
    private val patternValidator: PatternValidator
) {
    fun isEmailValid(email: String): Boolean {
        return patternValidator.matches(email.trim())
    }

    fun isPasswordValid(password: String): PasswordValidationState {
        val specialChars = "@#$%^&+=!"
        val hasMinLength = password.length >= MIN_PASSWORD_LENGTH
        val hasMaxLength = password.length <= MAX_PASSWORD_LENGTH
        val hasNumber = password.any { it.isDigit() }
        val hasLowerCase = password.any { it.isLowerCase() }
        val hasUpperCase = password.any { it.isUpperCase() }
        val hasSpecialCharacter = password.any { it in specialChars }

        return PasswordValidationState(
            hasMinLength = hasMinLength,
            hasMaxLength = hasMaxLength,
            hasNumber = hasNumber,
            hasLowerCase = hasLowerCase,
            hasUpperCase = hasUpperCase,
            hasSpecialCharacter = hasSpecialCharacter
        )
    }

    companion object {
        const val MIN_PASSWORD_LENGTH = 6
        const val MAX_PASSWORD_LENGTH = 20
    }
}