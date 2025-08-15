package com.hjw0623.presentation.screen.auth.register.ui

import com.hjw0623.core.business_logic.auth.validator.NicknameValidationState
import com.hjw0623.core.business_logic.auth.validator.PasswordValidationState

data class RegisterScreenState(
    val email: String = "",
    val password: String = "",
    val nickname: String = "",
    val isEmailValid: Boolean = false,
    val passwordValidationState: PasswordValidationState = PasswordValidationState(),
    val isPasswordVisible: Boolean = false,
    val nicknameValidationState: NicknameValidationState = NicknameValidationState.Idle,
    val isRegistering: Boolean = false,
) {
    val isRegisterButtonEnabled: Boolean
        get() = isEmailValid &&
                passwordValidationState.isValidPassword &&
                nicknameValidationState is NicknameValidationState.Valid &&
                !isRegistering
}