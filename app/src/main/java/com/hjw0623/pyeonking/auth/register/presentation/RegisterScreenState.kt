package com.hjw0623.pyeonking.auth.register.presentation

data class RegisterScreenState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val nickname: String = "",
    val isEmailValid: Boolean = false,
    val isPasswordValid: Boolean = false,
    val isConfirmPasswordValid: Boolean = false,
    val nicknameValidationState: NicknameValidationState = NicknameValidationState.Idle,
) {
    val isRegisterButtonEnabled: Boolean
        get() = isEmailValid && isPasswordValid && isConfirmPasswordValid &&
                nicknameValidationState is NicknameValidationState.Valid

}