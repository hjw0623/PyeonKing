package com.hjw0623.presentation.screen.mypage.mypage_main.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hjw0623.core.core_ui.designsystem.theme.PyeonKingTheme
import com.hjw0623.core.core_ui.ui.ObserveAsEvents
import com.hjw0623.core.core_ui.ui.rememberThrottledOnClick
import com.hjw0623.presentation.screen.mypage.mypage_main.ui.component.LoggedInScreen
import com.hjw0623.presentation.screen.mypage.mypage_main.ui.component.LoggedOutScreen
import com.hjw0623.presentation.screen.mypage.viewmodel.MyPageViewModel

@Composable
fun MyPageScreenRoot(
    modifier: Modifier = Modifier,
    myPageViewModel: MyPageViewModel = hiltViewModel(),
    onNavigateToChangeNickname: () -> Unit,
    onNavigateToChangePassword: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToReviewHistory: () -> Unit
) {
    val context = LocalContext.current

    val state by myPageViewModel.myPageScreenState.collectAsStateWithLifecycle()

    val throttledLoginClick = rememberThrottledOnClick { myPageViewModel.onLoginClick() }
    val throttledLogoutClick = rememberThrottledOnClick { myPageViewModel.onLogoutClick() }
    val throttledChangePasswordClick =
        rememberThrottledOnClick { myPageViewModel.navigateToChangePassword() }
    val throttledChangeNicknameClick =
        rememberThrottledOnClick { myPageViewModel.navigateToChangeNickname() }
    val throttledReviewHistoryClick =
        rememberThrottledOnClick { myPageViewModel.navigateToReviewHistory() }

    ObserveAsEvents(flow = myPageViewModel.myPageScreenEvent) { event ->
        when (event) {
            is MyPageScreenEvent.Error -> {
                Toast.makeText(context, event.error, Toast.LENGTH_SHORT).show()
            }

            is MyPageScreenEvent.NavigateToChangeNickname -> {
                onNavigateToChangeNickname()
            }

            is MyPageScreenEvent.NavigateToChangePassword -> {
                onNavigateToChangePassword()
            }

            MyPageScreenEvent.NavigateToLogin -> {
                onNavigateToLogin()
            }

            is MyPageScreenEvent.NavigateToReviewHistory -> {
                onNavigateToReviewHistory()
            }
        }
    }


    MyPageScreen(
        modifier = modifier,
        state = state,
        onLoginClick = throttledLoginClick,
        onLogoutClick = throttledLogoutClick,
        onChangePasswordClick = throttledChangePasswordClick,
        onChangeNicknameClick = throttledChangeNicknameClick,
        onReviewHistoryClick = throttledReviewHistoryClick
    )
}

@Composable
private fun MyPageScreen(
    modifier: Modifier = Modifier,
    state: MyPageScreenState,
    onLoginClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onChangePasswordClick: () -> Unit,
    onChangeNicknameClick: () -> Unit,
    onReviewHistoryClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        if (state.isLoggedIn) {
            LoggedInScreen(
                nickname = state.nickname,
                onLogoutClick = onLogoutClick,
                onChangePasswordClick = onChangePasswordClick,
                onChangeNicknameClick = onChangeNicknameClick,
                onReviewHistoryClick = onReviewHistoryClick,
            )
        } else {
            LoggedOutScreen(
                onLoginClick = onLoginClick,
            )
        }

    }
}

@Preview
@Composable
private fun MyPageScreenPreviewLoggedIn() {
    PyeonKingTheme {
        MyPageScreen(
            state = MyPageScreenState(
                isLoggedIn = true,
                nickname = "nickname"
            ),
            onLoginClick = {},
            onLogoutClick = {},
            onChangePasswordClick = {},
            onChangeNicknameClick = {},
            onReviewHistoryClick = {}
        )
    }
}

@Preview
@Composable
private fun MyPageScreenPreviewLoggedOut() {
    PyeonKingTheme {
        MyPageScreen(
            state = MyPageScreenState(
                isLoggedIn = false,
                nickname = "nickname"
            ),
            onLoginClick = {},
            onLogoutClick = {},
            onChangePasswordClick = {},
            onChangeNicknameClick = {},
            onReviewHistoryClick = {}
        )
    }
}