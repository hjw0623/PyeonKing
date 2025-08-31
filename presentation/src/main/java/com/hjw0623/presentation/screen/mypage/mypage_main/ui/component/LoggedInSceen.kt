package com.hjw0623.presentation.screen.mypage.mypage_main.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hjw0623.core.ui.designsystem.components.TopRoundedBackground
import com.hjw0623.core.ui.designsystem.theme.PyeonKingTheme
import com.hjw0623.presentation.R

@Composable
fun LoggedInScreen(
    modifier: Modifier = Modifier,
    nickname: String,
    onLogoutClick: () -> Unit,
    onChangePasswordClick: () -> Unit,
    onChangeNicknameClick: () -> Unit,
    onReviewHistoryClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopRoundedBackground {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .background(MaterialTheme.colorScheme.primary),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Outlined.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier.size(60.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
                MyPageGreetingText(stringResource(R.string.text_greeting))
                MyPageGreetingText(stringResource(R.string.text_user_name, nickname))
                MyPageGreetingText(stringResource(R.string.text_welcom_to_pyeonking))
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MyPageMenuListItem(
                icon = Icons.Default.AccountCircle,
                menuName = stringResource(R.string.action_change_nickname),
                onClick = onChangeNicknameClick
            )
            MyPageMenuListItem(
                icon = Icons.Default.Key,
                menuName = stringResource(R.string.action_change_password),
                onClick = onChangePasswordClick
            )
            MyPageMenuListItem(
                icon = Icons.Default.Edit,
                menuName = stringResource(R.string.action_view_review_history),
                onClick = onReviewHistoryClick
            )
            MyPageMenuListItem(
                icon = Icons.AutoMirrored.Default.Logout,
                menuName = stringResource(R.string.action_logout),
                onClick = onLogoutClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoggedInScreenPreview() {
    PyeonKingTheme {
        LoggedInScreen(
            nickname = "nickname",
            onLogoutClick = {},
            onChangePasswordClick = {},
            onChangeNicknameClick = {},
            onReviewHistoryClick = {},
        )
    }
}