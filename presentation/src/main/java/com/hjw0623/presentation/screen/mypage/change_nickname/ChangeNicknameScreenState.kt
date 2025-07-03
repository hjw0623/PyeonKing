package com.hjw0623.presentation.screen.mypage.change_nickname

import com.hjw0623.core.domain.auth.NicknameValidationState

data class ChangeNicknameScreenState(
    val currentNickname: String = "",
    val newNickname: String = "",
    val nicknameValidationState: NicknameValidationState = NicknameValidationState.Idle,
) {
    val isChangeButtonEnabled: Boolean
        get() = newNickname.isNotBlank() && nicknameValidationState is NicknameValidationState.Valid
}