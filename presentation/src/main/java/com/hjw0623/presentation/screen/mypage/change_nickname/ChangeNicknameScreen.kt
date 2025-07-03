package com.hjw0623.presentation.screen.mypage.change_nickname

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hjw0623.core.domain.auth.NicknameValidationState
import com.hjw0623.core.mockdata.mockTakenNicknames
import com.hjw0623.core.presentation.designsystem.components.PyeonKingButton
import com.hjw0623.core.presentation.designsystem.components.showToast
import com.hjw0623.core.presentation.designsystem.theme.PyeonKingTheme
import com.hjw0623.core.presentation.ui.ObserveAsEvents
import com.hjw0623.presentation.R
import com.hjw0623.presentation.screen.auth.register.component.PyeonKingOutlinedTextField
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

@Composable
fun ChangeNicknameScreenRoot(
    modifier: Modifier = Modifier,
    onNavigateToMyPage: () -> Unit,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var state by remember { mutableStateOf(ChangeNicknameScreenState()) }
    val eventFlow = remember { MutableSharedFlow<ChangeNicknameScreenEvent>() }
    val keyboardController = LocalSoftwareKeyboardController.current

    ObserveAsEvents(flow = eventFlow) { event ->
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
        state = state,
        onAction = { action ->
            when (action) {

                ChangeNicknameScreenAction.OnChangeNicknameClick -> {
                    scope.launch {
                        eventFlow.emit(ChangeNicknameScreenEvent.NavigateToMyPage)
                    }
                }

                is ChangeNicknameScreenAction.OnNicknameChanged -> {
                    state = state.copy(
                        newNickname = action.nickname,
                        nicknameValidationState = NicknameValidationState.Idle
                    )
                }

                ChangeNicknameScreenAction.OnNicknameCheckClick -> {
                    if (mockTakenNicknames.contains(state.newNickname) || state.newNickname.isBlank()) {
                        state = state.copy(
                            nicknameValidationState = NicknameValidationState.Invalid(
                                context.getString(
                                    R.string.text_already_used_nickname
                                )
                            )
                        )
                    } else {
                        state = state.copy(
                            nicknameValidationState = NicknameValidationState.Valid
                        )
                        keyboardController?.hide()
                    }
                }
            }

        },
        modifier = modifier
    )

}

@Composable
private fun ChangeNicknameScreen(
    state: ChangeNicknameScreenState,
    onAction: (ChangeNicknameScreenAction) -> Unit,
    modifier: Modifier = Modifier,
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
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            PyeonKingOutlinedTextField(
                modifier = Modifier.weight(1f),
                value = state.newNickname,
                onValueChange = { onAction(ChangeNicknameScreenAction.OnNicknameChanged(it)) },
                label = state.currentNickname,
                isValid = state.nicknameValidationState is NicknameValidationState.Valid,
                supportingText = when (val validationState = state.nicknameValidationState) {
                    is NicknameValidationState.Valid -> stringResource(R.string.text_useable_nickname)
                    is NicknameValidationState.Invalid -> validationState.message
                    else -> null
                },
                supportingTextColor = if (state.nicknameValidationState is NicknameValidationState.Valid) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.error
                }
            )

            Spacer(modifier = Modifier.width(18.dp))

            Box(
                modifier = Modifier
                    .height(70.dp),
                contentAlignment = Alignment.Center
            ) {
                if (state.nicknameValidationState is NicknameValidationState.Checking) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                } else {
                    PyeonKingButton(
                        text = stringResource(R.string.action_duplicate_check),
                        onClick = { onAction(ChangeNicknameScreenAction.OnNicknameCheckClick) },
                        enabled = state.newNickname.isNotBlank() && state.nicknameValidationState !is NicknameValidationState.Valid,
                        modifier = Modifier.height(56.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        PyeonKingButton(
            text = stringResource(R.string.text_change_nickname),
            onClick = { onAction(ChangeNicknameScreenAction.OnChangeNicknameClick) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            enabled = state.isChangeButtonEnabled,
            contentPadding = PaddingValues(16.dp)
        )
    }
}


@Preview
@Composable
private fun ChangeNicknameScreenPreview() {
    PyeonKingTheme {
        ChangeNicknameScreen(
            state = ChangeNicknameScreenState(),
            onAction = {}
        )
    }
}