package com.hjw0623.presentation.screen.mypage.change_password.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hjw0623.core.core_domain.auth.validator.PasswordValidationState
import com.hjw0623.core.core_domain.auth.validator.UserDataValidator
import com.hjw0623.core.presentation.designsystem.components.LoadingButton
import com.hjw0623.core.presentation.designsystem.components.PyeonKingPasswordTextField
import com.hjw0623.core.presentation.designsystem.components.showToast
import com.hjw0623.core.presentation.designsystem.theme.PyeonKingTheme
import com.hjw0623.core.presentation.ui.ObserveAsEvents
import com.hjw0623.core.presentation.ui.rememberThrottledOnClick
import com.hjw0623.presentation.R
import com.hjw0623.presentation.screen.auth.register.ui.component.PasswordRequirement
import com.hjw0623.presentation.screen.mypage.viewmodel.MyPageViewModel

@Composable
fun ChangePasswordScreenRoot(
    modifier: Modifier = Modifier,
    myPageViewModel: MyPageViewModel = hiltViewModel(),
    onNavigateToMyPage: () -> Unit,
) {
    val context = LocalContext.current

    val state by myPageViewModel.changePasswordState.collectAsStateWithLifecycle()

    val throttledChangePwClick = rememberThrottledOnClick {
        myPageViewModel.onChangePasswordClick()
    }
    ObserveAsEvents(flow = myPageViewModel.changePasswordEvent) { event ->
        when (event) {
            is ChangePasswordScreenEvent.Error -> {
                showToast(context, event.error)
            }

            is ChangePasswordScreenEvent.NavigateToMyPage -> {
                showToast(context, event.message)
                onNavigateToMyPage()
            }
        }
    }

    ChangePasswordScreenScreen(
        modifier = modifier,
        state = state,
        onChangePasswordClick = throttledChangePwClick,
        onCurrentPasswordChange = myPageViewModel::onCurrentPasswordChange,
        onNewPasswordChange = myPageViewModel::onNewPasswordChange,
        onConfirmPasswordChange = myPageViewModel::onConfirmPasswordChange,
        onToggleCurrentPasswordVisibility = myPageViewModel::onToggleCurrentPasswordVisibility,
        onToggleNewPasswordVisibility = myPageViewModel::onToggleNewPasswordVisibility,
        onToggleConfirmPasswordVisibility = myPageViewModel::onToggleConfirmPasswordVisibility,
        onNewPasswordChangeDebounced = myPageViewModel::onNewPasswordChangeDebounced,
    )
}

@Composable
private fun ChangePasswordScreenScreen(
    modifier: Modifier = Modifier,
    state: ChangePasswordScreenState,
    onChangePasswordClick: () -> Unit,
    onCurrentPasswordChange: (String) -> Unit,
    onNewPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onNewPasswordChangeDebounced: (String) -> Unit,
    onToggleCurrentPasswordVisibility: () -> Unit,
    onToggleNewPasswordVisibility: () -> Unit,
    onToggleConfirmPasswordVisibility: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(R.string.action_change_password),
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(R.string.input_current_password_new_password),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        PyeonKingPasswordTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.currentPassword,
            onValueChange = onCurrentPasswordChange,
            isPasswordVisible = state.isCurrentPasswordVisible,
            onTogglePasswordVisibility = onToggleCurrentPasswordVisibility,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            title = stringResource(R.string.label_current_password),
        )

        Spacer(modifier = Modifier.height(16.dp))

        PyeonKingPasswordTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.newPassword,
            onValueChange = onNewPasswordChange,
            onDebouncedValueChange = onNewPasswordChangeDebounced,
            isPasswordVisible = state.isNewPasswordVisible,
            onTogglePasswordVisibility = onToggleNewPasswordVisibility,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            title = stringResource(R.string.label_new_password),
        )
        Spacer(modifier = Modifier.height(16.dp))

        PasswordRequirement(
            text = stringResource(
                id = R.string.at_least_x_characters,
                UserDataValidator.MIN_PASSWORD_LENGTH
            ),
            isValid = state.passwordValidationState.hasMinLength
        )

        Spacer(modifier = Modifier.height(4.dp))

        PasswordRequirement(
            text = stringResource(
                id = R.string.at_max_x_characters,
                UserDataValidator.MAX_PASSWORD_LENGTH
            ),
            isValid = state.passwordValidationState.hasMaxLength
        )

        Spacer(modifier = Modifier.height(4.dp))

        PasswordRequirement(
            text = stringResource(
                id = R.string.at_least_one_number,
            ),
            isValid = state.passwordValidationState.hasNumber
        )

        Spacer(modifier = Modifier.height(4.dp))

        PasswordRequirement(
            text = stringResource(
                id = R.string.contains_lowercase_char,
            ),
            isValid = state.passwordValidationState.hasLowerCase
        )

        Spacer(modifier = Modifier.height(4.dp))

        PasswordRequirement(
            text = stringResource(
                id = R.string.contains_uppercase_char,
            ),
            isValid = state.passwordValidationState.hasUpperCase
        )

        Spacer(modifier = Modifier.height(4.dp))

        PasswordRequirement(
            text = stringResource(
                id = R.string.contains_special_char,
            ),
            isValid = state.passwordValidationState.hasSpecialCharacter
        )

        Spacer(modifier = Modifier.height(16.dp))

        PyeonKingPasswordTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.confirmPassword,
            onValueChange = onConfirmPasswordChange,
            isPasswordVisible = state.isConfirmPasswordVisible,
            onTogglePasswordVisibility = onToggleConfirmPasswordVisibility,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                keyboard?.hide()
                onChangePasswordClick()
            }),
            title = stringResource(R.string.label_confirm),
        )
        PasswordRequirement(
            text = stringResource(R.string.text_same_password),
            isValid = state.isConfirmPasswordValid
        )

        Spacer(modifier = Modifier.weight(1f))

        LoadingButton(
            text = stringResource(R.string.text_change_password),
            onClick = onChangePasswordClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            enabled = state.isChangePwButtonEnabled,
            contentPadding = PaddingValues(16.dp),
            loading = state.isChangingPassword
        )
    }
}


@Preview(showBackground = true, name = "Valid State")
@Composable
private fun ChangePasswordScreenValidPreview() {
    PyeonKingTheme {
        ChangePasswordScreenScreen(
            state = ChangePasswordScreenState(
                currentPassword = "password",
                newPassword = "password",
                confirmPassword = "password",
                passwordValidationState = PasswordValidationState(
                    hasUpperCase = true,
                    hasLowerCase = true,
                    hasNumber = true,
                    hasSpecialCharacter = true,
                    hasMinLength = true
                ),
                isCurrentPasswordVisible = true,
                isNewPasswordVisible = false,
                isConfirmPasswordVisible = false
            ),
            onChangePasswordClick = {},
            onCurrentPasswordChange = {},
            onNewPasswordChange = {},
            onConfirmPasswordChange = {},
            onNewPasswordChangeDebounced = {},
            onToggleCurrentPasswordVisibility = {},
            onToggleNewPasswordVisibility = {},
            onToggleConfirmPasswordVisibility = {},
        )
    }
}

@Preview(showBackground = true, name = "Error State")
@Composable
private fun ChangePasswordScreenErrorPreview() {
    PyeonKingTheme {
        ChangePasswordScreenScreen(
            state = ChangePasswordScreenState(
                currentPassword = "wrongPassword",
                newPassword = "short",
                confirmPassword = "mismatch",
                passwordValidationState = PasswordValidationState(
                    hasUpperCase = false,
                    hasLowerCase = false,
                    hasNumber = false,
                    hasSpecialCharacter = false,
                    hasMinLength = false
                ),
                isCurrentPasswordVisible = true,
                isNewPasswordVisible = false,
                isConfirmPasswordVisible = false,
            ),
            onChangePasswordClick = {},
            onCurrentPasswordChange = {},
            onNewPasswordChange = {},
            onConfirmPasswordChange = {},
            onNewPasswordChangeDebounced = {},
            onToggleCurrentPasswordVisibility = {},
            onToggleNewPasswordVisibility = {},
            onToggleConfirmPasswordVisibility = {},
        )
    }
}