package com.hjw0623.presentation.screen.mypage.change_nickname


sealed interface ChangeNicknameScreenAction {
    data class OnNicknameChanged(val nickname: String) : ChangeNicknameScreenAction
    data object OnNicknameCheckClick : ChangeNicknameScreenAction
    data object OnChangeNicknameClick : ChangeNicknameScreenAction
}