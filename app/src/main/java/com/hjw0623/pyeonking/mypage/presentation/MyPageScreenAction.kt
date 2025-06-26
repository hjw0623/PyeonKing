package com.hjw0623.pyeonking.mypage.presentation

sealed interface MyPageScreenAction {
    data object OnLoginClick : MyPageScreenAction
    data object OnLogoutClick : MyPageScreenAction
    data object OnChangePasswordClick : MyPageScreenAction
    data object OnChangeNicknameClick : MyPageScreenAction
    data object OnReviewHistoryClick : MyPageScreenAction
}