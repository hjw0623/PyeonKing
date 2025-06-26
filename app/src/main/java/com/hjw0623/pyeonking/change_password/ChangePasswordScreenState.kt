package com.hjw0623.pyeonking.change_password

data class ChangePasswordScreenState(
    val currentPassword: String = "",
    val newPassword: String = "",
    val confirmPassword: String = "",
    val isCurrentPasswordValid: Boolean = false,
    val isNewPasswordValid: Boolean = false,
    val isConfirmPasswordValid: Boolean = false,
) {
    val isChangePasswordButtonEnabled: Boolean
        get() = isCurrentPasswordValid && isNewPasswordValid && isConfirmPasswordValid
}
