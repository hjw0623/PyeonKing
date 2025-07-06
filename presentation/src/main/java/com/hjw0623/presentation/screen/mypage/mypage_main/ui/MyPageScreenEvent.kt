package com.hjw0623.presentation.screen.mypage.mypage_main.ui

sealed interface MyPageScreenEvent {
    data class Error(val error: String) : MyPageScreenEvent
    data object NavigateToChangeNickname : MyPageScreenEvent
    data object NavigateToChangePassword : MyPageScreenEvent
    data object NavigateToReviewHistory : MyPageScreenEvent
    data object NavigateToLogin : MyPageScreenEvent
}
