package com.hjw0623.pyeonking.mypage.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.hjw0623.pyeonking.core.util.ObserveAsEvents
import com.hjw0623.pyeonking.mypage.presentation.component.LoggedInScreen
import com.hjw0623.pyeonking.mypage.presentation.component.LoggedOutScreen
import com.hjw0623.pyeonking.ui.theme.PyeonKingTheme
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

@Composable
fun MyPageScreenRoot(
    modifier: Modifier = Modifier,
    onNavigateToChangeNickname: () -> Unit,
    onNavigateToChangePassword: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToReviewHistory: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var state by remember { mutableStateOf(MyPageScreenState()) }
    val eventFlow = remember { MutableSharedFlow<MyPageScreenEvent>() }

    ObserveAsEvents(flow = eventFlow) { event ->
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
        state = state,
        onAction = { action ->
            when (action) {
                MyPageScreenAction.OnChangeNicknameClick -> {
                    coroutineScope.launch {
                        eventFlow.emit(MyPageScreenEvent.NavigateToChangeNickname)
                    }
                }

                MyPageScreenAction.OnChangePasswordClick -> {
                    coroutineScope.launch {
                        eventFlow.emit(MyPageScreenEvent.NavigateToChangePassword)
                    }
                }

                MyPageScreenAction.OnLoginClick -> {
                    coroutineScope.launch {
                        eventFlow.emit(MyPageScreenEvent.NavigateToLogin)
                    }
                }

                MyPageScreenAction.OnLogoutClick -> {
                    state = state.copy(isLoggedIn = false)
                }

                MyPageScreenAction.OnReviewHistoryClick -> {
                    coroutineScope.launch {
                        eventFlow.emit(MyPageScreenEvent.NavigateToReviewHistory)
                    }
                }
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