package com.hjw0623.presentation.screen.mypage.change_nickname.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hjw0623.core.domain.auth.NicknameValidationState
import com.hjw0623.core.presentation.designsystem.components.PyeonKingButton
import com.hjw0623.core.presentation.designsystem.components.PyeonKingTextField
import com.hjw0623.core.presentation.designsystem.components.showToast
import com.hjw0623.core.presentation.designsystem.theme.PyeonKingTheme
import com.hjw0623.core.presentation.ui.ObserveAsEvents
import com.hjw0623.core.presentation.ui.rememberThrottledOnClick
import com.hjw0623.presentation.R
import com.hjw0623.presentation.screen.factory.MyPageViewModelFactory
import com.hjw0623.presentation.screen.mypage.change_nickname.ui.ChangeNicknameScreenEvent
import com.hjw0623.presentation.screen.mypage.viewmodel.MyPageViewModel

@Composable
fun ChangeNicknameScreenRoot(
    modifier: Modifier = Modifier,
    onNavigateToMyPage: () -> Unit,
) {
    val context = LocalContext.current
    val myPageViewModel = MyPageViewModelFactory()
    val viewModel: MyPageViewModel = viewModel(factory = myPageViewModel)

    val newNickname by viewModel.newNickname.collectAsStateWithLifecycle()
    val nicknameValidationState by viewModel.nicknameValidationState.collectAsStateWithLifecycle()
    val isChangeButtonEnabled by viewModel.isChangeButtonEnabled.collectAsStateWithLifecycle()

    val throttledNicknameCheckClick = rememberThrottledOnClick {
        viewModel.onNicknameCheckClick()
    }
    val throttledChangeNicknameClick = rememberThrottledOnClick {
        viewModel.onChangeNicknameClick()
    }

    ObserveAsEvents(flow = viewModel.event) { event ->
        when (event) {
            is ChangeNicknameScreenEvent.Error -> {
                showToast(context, event.error)
            }

            ChangeNicknameScreenEvent.NavigateToMyPage -> {
                onNavigateToMyPage()
            }
        }
    }

    ChangeNicknameScreen(
        modifier = modifier,
        newNickname = newNickname,
        nicknameValidationState = nicknameValidationState,
        isChangeButtonEnabled = isChangeButtonEnabled,
        onNicknameCheckClick = throttledNicknameCheckClick,
        onNicknameChange = viewModel::onNicknameChange,
        onChangeNicknameClick = throttledChangeNicknameClick,
    )
}

@Composable
private fun ChangeNicknameScreen(
    modifier: Modifier = Modifier,
    newNickname: String,
    nicknameValidationState: NicknameValidationState,
    onNicknameChange: (String) -> Unit,
    isChangeButtonEnabled: Boolean,
    onNicknameCheckClick: () -> Unit,
    onChangeNicknameClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(R.string.action_change_nickname),
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(R.string.input_new_nickname),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom
        ) {
            PyeonKingTextField(
                value = newNickname,
                onValueChange = onNicknameChange,
                startIcon = Icons.Default.Badge,
                endIcon = if (nicknameValidationState is NicknameValidationState.Valid) Icons.Default.Check else null,
                title = stringResource(R.string.label_nickname),
                error = if (nicknameValidationState is NicknameValidationState.Invalid) {
                    nicknameValidationState.message
                } else {
                    null
                },
                keyboardType = KeyboardType.Text,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(16.dp))

            if (nicknameValidationState is NicknameValidationState.Checking) {
                Box(
                    modifier = Modifier.size(56.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                }
            } else {
                PyeonKingButton(
                    text = stringResource(R.string.action_duplicate_check),
                    onClick = onNicknameCheckClick,
                    enabled = newNickname.isNotBlank() && nicknameValidationState !is NicknameValidationState.Valid,
                    modifier = Modifier.height(56.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        PyeonKingButton(
            text = stringResource(R.string.text_change_nickname),
            onClick = onChangeNicknameClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            enabled = isChangeButtonEnabled,
            contentPadding = PaddingValues(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ChangeNicknameScreenPreview() {
    PyeonKingTheme {
        ChangeNicknameScreen(
            newNickname = "편킹왕",
            nicknameValidationState = NicknameValidationState.Valid,
            isChangeButtonEnabled = true,
            onNicknameCheckClick = {},
            onChangeNicknameClick = {},
            onNicknameChange = {},
        )
    }
}