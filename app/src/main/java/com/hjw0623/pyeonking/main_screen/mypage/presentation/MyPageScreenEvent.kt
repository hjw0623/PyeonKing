package com.hjw0623.pyeonking.main_screen.mypage.presentation

sealed interface MyPageScreenEvent {
    data class Error(val error: String) : MyPageScreenEvent
    data object NavigateToChangeNickname : MyPageScreenEvent
    data object NavigateToChangePassword : MyPageScreenEvent
    data object NavigateToReviewHistory : MyPageScreenEvent
    data object NavigateToLogin : MyPageScreenEvent
}
