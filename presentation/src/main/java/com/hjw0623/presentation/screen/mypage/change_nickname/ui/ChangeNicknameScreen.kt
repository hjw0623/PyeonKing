package com.hjw0623.presentation.screen.mypage.change_nickname.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hjw0623.core.core_domain.auth.validator.NicknameValidationState
import com.hjw0623.core.core_ui.designsystem.components.LoadingButton
import com.hjw0623.core.core_ui.designsystem.components.PyeonKingButton
import com.hjw0623.core.core_ui.designsystem.components.PyeonKingTextField
import com.hjw0623.core.core_ui.designsystem.components.showToast
import com.hjw0623.core.core_ui.designsystem.theme.PyeonKingTheme
import com.hjw0623.core.core_ui.util.ObserveAsEvents
import com.hjw0623.core.core_ui.util.rememberThrottledOnClick
import com.hjw0623.presentation.R
import com.hjw0623.presentation.screen.mypage.viewmodel.MyPageViewModel

@Composable
fun ChangeNicknameScreenRoot(
    modifier: Modifier = Modifier,
    myPageViewModel: MyPageViewModel = hiltViewModel(),
    onNavigateToMyPage: () -> Unit,
) {
    val context = LocalContext.current

    val state by myPageViewModel.changeNicknameState.collectAsStateWithLifecycle()

    val throttledNicknameCheckClick = rememberThrottledOnClick {
        myPageViewModel.onNicknameCheckClick()
    }
    val throttledChangeNicknameClick = rememberThrottledOnClick {
        myPageViewModel.onChangeNicknameClick()
    }

    ObserveAsEvents(flow = myPageViewModel.changeNicknameEvent) { event ->
        when (event) {
            is ChangeNicknameScreenEvent.Error -> {
                showToast(context, event.error)
            }

            is ChangeNicknameScreenEvent.NavigateToMyPage -> {
                showToast(context, event.message)
                onNavigateToMyPage()
            }
        }
    }

    ChangeNicknameScreen(
        modifier = modifier,
        state = state,
        onNicknameChange = myPageViewModel::onNicknameChange,
        onNicknameCheckClick = throttledNicknameCheckClick,
        onChangeNicknameClick = throttledChangeNicknameClick,
    )
}

@Composable
private fun ChangeNicknameScreen(
    modifier: Modifier = Modifier,
    state: ChangeNicknameScreenState,
    onNicknameChange: (String) -> Unit,
    onNicknameCheckClick: () -> Unit,
    onChangeNicknameClick: () -> Unit,
) {
    val isChecking = state.nicknameValidationState == NicknameValidationState.Checking
    val keyboard = LocalSoftwareKeyboardController.current
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
                value = state.newNickname,
                onValueChange = onNicknameChange,
                startIcon = Icons.Default.Badge,
                endIcon = if (state.nicknameValidationState is NicknameValidationState.Valid) Icons.Default.Check else null,
                title = stringResource(R.string.label_nickname),
                error = if (state.nicknameValidationState is NicknameValidationState.Invalid) {
                    state.nicknameValidationState.message
                } else {
                    null
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    keyboard?.hide()
                    onNicknameCheckClick()
                }),
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(16.dp))

            LoadingButton(
                text = stringResource(R.string.action_duplicate_check),
                onClick = onNicknameCheckClick,
                loading = isChecking,
                enabled = state.newNickname.isNotBlank() && state.nicknameValidationState !is NicknameValidationState.Valid,
                modifier = Modifier.height(56.dp),
                contentPadding = PaddingValues(horizontal = 12.dp),
                fullWidth = false
            )

        }

        Spacer(modifier = Modifier.weight(1f))

        PyeonKingButton(
            text = stringResource(R.string.text_change_nickname),
            onClick = onChangeNicknameClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            enabled = state.isChangeNicknameButtonEnabled,
            contentPadding = PaddingValues(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ChangeNicknameScreenPreview() {
    PyeonKingTheme {
        ChangeNicknameScreen(
            state = ChangeNicknameScreenState(
                newNickname = "편킹왕",
                nicknameValidationState = NicknameValidationState.Valid,
            ),
            onNicknameCheckClick = {},
            onChangeNicknameClick = {},
            onNicknameChange = {},
        )
    }
}