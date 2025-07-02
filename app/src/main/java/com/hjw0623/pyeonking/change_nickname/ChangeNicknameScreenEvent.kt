package com.hjw0623.pyeonking.change_nickname

sealed interface ChangeNicknameScreenEvent {
    data class Error(val error: String) : ChangeNicknameScreenEvent
    data object NavigateToMyPage : ChangeNicknameScreenEvent
}