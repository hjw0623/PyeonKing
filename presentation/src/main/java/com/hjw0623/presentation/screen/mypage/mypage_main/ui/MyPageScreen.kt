package com.hjw0623.presentation.screen.mypage.mypage_main.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hjw0623.core.domain.AuthManager
import com.hjw0623.core.domain.mypage.User
import com.hjw0623.core.presentation.designsystem.theme.PyeonKingTheme
import com.hjw0623.core.presentation.ui.ObserveAsEvents
import com.hjw0623.core.presentation.ui.rememberThrottledOnClick
import com.hjw0623.core.util.mockdata.mockUser
import com.hjw0623.presentation.screen.mypage.mypage_main.ui.component.LoggedInScreen
import com.hjw0623.presentation.screen.mypage.mypage_main.ui.component.LoggedOutScreen
import com.hjw0623.presentation.screen.mypage.viewmodel.MyPageViewModel

@Composable
fun MyPageScreenRoot(
    modifier: Modifier = Modifier,
    myPageViewModel: MyPageViewModel,
    onNavigateToChangeNickname: () -> Unit,
    onNavigateToChangePassword: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToReviewHistory: () -> Unit
) {
    val context = LocalContext.current
    val viewModel = myPageViewModel

    val isLoggedIn by AuthManager.isLoggedIn.collectAsStateWithLifecycle()
    val userData by AuthManager.userData.collectAsStateWithLifecycle()
    val throttledLoginClick = rememberThrottledOnClick { viewModel.onLoginClick() }
    val throttledLogoutClick = rememberThrottledOnClick { viewModel.onLogoutClick() }
    val throttledChangePasswordClick =
        rememberThrottledOnClick { viewModel.navigateToChangePassword() }
    val throttledChangeNicknameClick =
        rememberThrottledOnClick { viewModel.navigateToChangeNickname() }
    val throttledReviewHistoryClick =
        rememberThrottledOnClick { viewModel.navigateToReviewHistory() }

    ObserveAsEvents(flow = viewModel.myPageScreenEvent) { event ->
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
        isLoggedIn = isLoggedIn,
        userData = userData,
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
    isLoggedIn: Boolean,
    userData: User?,
    onLoginClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onChangePasswordClick: () -> Unit,
    onChangeNicknameClick: () -> Unit,
    onReviewHistoryClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        if (isLoggedIn) {
            LoggedInScreen(
                userData = userData!!,
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
private fun MyPageScreenPreview() {
    PyeonKingTheme {
        MyPageScreen(
            isLoggedIn = true,
            userData = mockUser,
            onLoginClick = {},
            onLogoutClick = {},
            onChangePasswordClick = {},
            onChangeNicknameClick = {},
            onReviewHistoryClick = {}
        )
    }
}