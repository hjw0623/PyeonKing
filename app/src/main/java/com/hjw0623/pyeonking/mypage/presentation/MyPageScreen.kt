package com.hjw0623.pyeonking.mypage.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hjw0623.pyeonking.mypage.presentation.component.LoggedInScreen
import com.hjw0623.pyeonking.mypage.presentation.component.LoggedOutScreen
import com.hjw0623.pyeonking.ui.theme.PyeonKingTheme

@Composable
fun MyPageScreenRoot(
    modifier: Modifier = Modifier,
) {
    var state by remember { mutableStateOf(MyPageScreenState()) }

    MyPageScreen(
        state = state,
        onAction = { action ->
            when (action) {
                MyPageScreenAction.OnChangeNicknameClick -> {}
                MyPageScreenAction.OnChangePasswordClick -> {}
                MyPageScreenAction.OnLoginClick -> {}
                MyPageScreenAction.OnLogoutClick -> {}
                MyPageScreenAction.OnReviewHistoryClick -> {}
            }
        },
        modifier = modifier
    )
}

@Composable
private fun MyPageScreen(
    modifier: Modifier = Modifier,
    state: MyPageScreenState,
    onAction: (MyPageScreenAction) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        if (state.isLoggedIn) {
            LoggedInScreen(
                userData = state.userData!!,
                onLogoutClick = { onAction(MyPageScreenAction.OnLogoutClick) },
                onChangePasswordClick = { onAction(MyPageScreenAction.OnChangePasswordClick) },
                onChangeNicknameClick = { onAction(MyPageScreenAction.OnChangeNicknameClick) },
                onReviewHistoryClick = { onAction(MyPageScreenAction.OnReviewHistoryClick) }
            )
        } else {
            LoggedOutScreen(
                onLoginClick = { onAction(MyPageScreenAction.OnLoginClick) }
            )
        }

    }
}

@Preview
@Composable
private fun MyPageScreenPreview() {
    PyeonKingTheme {
        MyPageScreen(
            state = MyPageScreenState(),
            onAction = {}
        )
    }
}