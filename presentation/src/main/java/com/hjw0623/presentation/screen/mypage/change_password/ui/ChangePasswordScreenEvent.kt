package com.hjw0623.presentation.screen.mypage.change_password.ui

sealed interface ChangePasswordScreenEvent {
    data class Error(val error: String) : ChangePasswordScreenEvent
    data class NavigateToMyPage(val message: String) : ChangePasswordScreenEvent
}