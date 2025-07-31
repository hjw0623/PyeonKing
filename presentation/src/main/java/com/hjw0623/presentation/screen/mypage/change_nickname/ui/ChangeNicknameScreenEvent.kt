package com.hjw0623.presentation.screen.mypage.change_nickname.ui

sealed interface ChangeNicknameScreenEvent {
    data class Error(val error: String) : ChangeNicknameScreenEvent
    data class NavigateToMyPage(val message: String) : ChangeNicknameScreenEvent
}