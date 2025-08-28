package com.hjw0623.presentation.screen.mypage.change_password.ui

import com.hjw0623.core.core_domain.auth.validator.PasswordValidationState

data class ChangePasswordScreenState(
    val email: String = "",
    val currentPassword: String = "",
    val newPassword: String = "",
    val confirmPassword: String = "",
    val isCurrentPasswordVisible: Boolean = false,
    val isNewPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,
    val passwordValidationState: PasswordValidationState = PasswordValidationState(),
    val isChangingPassword: Boolean = false
) {
    val isConfirmPasswordValid: Boolean
        get() = newPassword.isNotEmpty() && newPassword == confirmPassword

    val isChangePwButtonEnabled: Boolean
        get() = newPassword.isNotBlank() && confirmPassword.isNotBlank() && newPassword == confirmPassword
}