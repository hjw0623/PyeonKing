package com.hjw0623.presentation.screen.mypage.change_nickname.ui

import com.hjw0623.core.domain.auth.model.NicknameValidationState

data class ChangeNicknameScreenState(
    val currentNickname: String = "",
    val newNickname: String = "",
    val nicknameValidationState: NicknameValidationState = NicknameValidationState.Idle,
    val isChangingNickname: Boolean = false
) {
    val isChangeNicknameButtonEnabled
        get() = nicknameValidationState is
                NicknameValidationState.Valid &&
                newNickname !=
                currentNickname &&
                !isChangingNickname
}
