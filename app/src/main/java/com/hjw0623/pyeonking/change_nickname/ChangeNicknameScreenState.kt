package com.hjw0623.pyeonking.change_nickname

import com.hjw0623.pyeonking.auth.register.presentation.NicknameValidationState

data class ChangeNicknameScreenState(
    val currentNickname: String = "",
    val newNickname: String = "",
    val nicknameValidationState: NicknameValidationState = NicknameValidationState.Idle,
) {
    val isChangeButtonEnabled: Boolean
        get() = newNickname.isNotBlank() && nicknameValidationState is NicknameValidationState.Valid
}