package com.hjw0623.presentation.screen.mypage.change_password.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hjw0623.core.domain.auth.PasswordValidationState
import com.hjw0623.core.domain.auth.UserDataValidator
import com.hjw0623.core.presentation.designsystem.components.PyeonKingButton
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
    myPageViewModel: MyPageViewModel,
    onNavigateToMyPage: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val viewModel = myPageViewModel

    val currentPassword by viewModel.currentPassword.collectAsStateWithLifecycle()
    val newPassword by viewModel.newPassword.collectAsStateWithLifecycle()
    val confirmPassword by viewModel.confirmPassword.collectAsStateWithLifecycle()
    val passwordValidationState by viewModel.passwordValidationState.collectAsStateWithLifecycle()

    val isCurrentPasswordValid by viewModel.isCurrentPasswordValid.collectAsStateWithLifecycle()
    val isConfirmPasswordValid by viewModel.isConfirmPasswordValid.collectAsStateWithLifecycle()

    val isCurrentPasswordVisible by viewModel.isCurrentPasswordVisible.collectAsStateWithLifecycle()
    val isNewPasswordVisible by viewModel.isNewPasswordVisible.collectAsStateWithLifecycle()
    val isConfirmPasswordVisible by viewModel.isConfirmPasswordVisible.collectAsStateWithLifecycle()

    val isChangePwButtonEnabled by viewModel.isChangePwButtonEnabled.collectAsStateWithLifecycle()

    val throttledChangePwClick = rememberThrottledOnClick {
        viewModel.onChangePasswordClick()
    }
    ObserveAsEvents(flow = viewModel.changePasswordEvent) { event ->
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
        currentPassword = currentPassword,
        newPassword = newPassword,
        confirmPassword = confirmPassword,
        passwordValidationState = passwordValidationState,
        onChangePasswordClick = throttledChangePwClick,
        isCurrentPasswordValid = isCurrentPasswordValid,
        isConfirmPasswordValid = isConfirmPasswordValid,
        isCurrentPasswordVisible = isCurrentPasswordVisible,
        isNewPasswordVisible = isNewPasswordVisible,
        isConfirmPasswordVisible = isConfirmPasswordVisible,
        onCurrentPasswordChange = viewModel::onCurrentPasswordChange,
        onNewPasswordChange = viewModel::onNewPasswordChange,
        onConfirmPasswordChange = viewModel::onConfirmPasswordChange,
        onToggleCurrentPasswordVisibility = viewModel::onToggleCurrentPasswordVisibility,
        onToggleNewPasswordVisibility = viewModel::onToggleNewPasswordVisibility,
        onToggleConfirmPasswordVisibility = viewModel::onToggleConfirmPasswordVisibility,
        onCurrentPasswordChangeDebounced = viewModel::onCurrentPasswordChangeDebounced,
        onNewPasswordChangeDebounced = viewModel::onNewPasswordChangeDebounced,
        isChangePwButtonEnabled = isChangePwButtonEnabled
    )
}

@Composable
private fun ChangePasswordScreenScreen(
    modifier: Modifier = Modifier,
    currentPassword: String,
    newPassword: String,
    confirmPassword: String,
    passwordValidationState: PasswordValidationState,
    onChangePasswordClick: () -> Unit,
    isCurrentPasswordValid: Boolean,
    isConfirmPasswordValid: Boolean,
    isCurrentPasswordVisible: Boolean,
    isNewPasswordVisible: Boolean,
    isConfirmPasswordVisible: Boolean,
    onCurrentPasswordChange: (String) -> Unit,
    onNewPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onCurrentPasswordChangeDebounced: (String) -> Unit,
    onNewPasswordChangeDebounced: (String) -> Unit,
    onToggleCurrentPasswordVisibility: () -> Unit,
    onToggleNewPasswordVisibility: () -> Unit,
    onToggleConfirmPasswordVisibility: () -> Unit,
    isChangePwButtonEnabled: Boolean
) {
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
            value = currentPassword,
            onValueChange = onCurrentPasswordChange,
            onDebouncedValueChange = onCurrentPasswordChangeDebounced,
            isPasswordVisible = isCurrentPasswordVisible,
            onTogglePasswordVisibility = onToggleCurrentPasswordVisibility,
            title = stringResource(R.string.label_current_password),
        )
        PasswordRequirement(
            text = "현재 비밀번호 일치",
            isValid = isCurrentPasswordValid
        )
        Spacer(modifier = Modifier.height(16.dp))

        PyeonKingPasswordTextField(
            modifier = Modifier.fillMaxWidth(),
            value = newPassword,
            onValueChange = onNewPasswordChange,
            onDebouncedValueChange = onNewPasswordChangeDebounced,
            isPasswordVisible = isNewPasswordVisible,
            onTogglePasswordVisibility = onToggleNewPasswordVisibility,
            title = stringResource(R.string.label_new_password),
        )
        Spacer(modifier = Modifier.height(16.dp))

        PasswordRequirement(
            text = stringResource(
                id = R.string.at_least_x_characters,
                UserDataValidator.MIN_PASSWORD_LENGTH
            ),
            isValid = passwordValidationState.hasMinLength
        )

        Spacer(modifier = Modifier.height(4.dp))

        PasswordRequirement(
            text = stringResource(
                id = R.string.at_max_x_characters,
                UserDataValidator.MAX_PASSWORD_LENGTH
            ),
            isValid = passwordValidationState.hasMaxLength
        )

        Spacer(modifier = Modifier.height(4.dp))

        PasswordRequirement(
            text = stringResource(
                id = R.string.at_least_one_number,
            ),
            isValid = passwordValidationState.hasNumber
        )

        Spacer(modifier = Modifier.height(4.dp))

        PasswordRequirement(
            text = stringResource(
                id = R.string.contains_lowercase_char,
            ),
            isValid = passwordValidationState.hasLowerCase
        )

        Spacer(modifier = Modifier.height(4.dp))

        PasswordRequirement(
            text = stringResource(
                id = R.string.contains_uppercase_char,
            ),
            isValid = passwordValidationState.hasUpperCase
        )

        Spacer(modifier = Modifier.height(4.dp))

        PasswordRequirement(
            text = stringResource(
                id = R.string.contains_special_char,
            ),
            isValid = passwordValidationState.hasSpecialCharacter
        )

        Spacer(modifier = Modifier.height(16.dp))

        PyeonKingPasswordTextField(
            modifier = Modifier.fillMaxWidth(),
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            isPasswordVisible = isConfirmPasswordVisible,
            onTogglePasswordVisibility = onToggleConfirmPasswordVisibility,
            title = stringResource(R.string.label_confirm),
        )
        PasswordRequirement(
            text = "비밀번호 일치",
            isValid = isConfirmPasswordValid
        )

        Spacer(modifier = Modifier.weight(1f))

        PyeonKingButton(
            text = stringResource(R.string.text_change_password),
            onClick = onChangePasswordClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            enabled = isChangePwButtonEnabled,
            contentPadding = PaddingValues(16.dp)
        )
    }
}


@Preview(showBackground = true, name = "Valid State")
@Composable
private fun ChangePasswordScreenValidPreview() {
    PyeonKingTheme {
        ChangePasswordScreenScreen(
            currentPassword = "currentPassword1!",
            newPassword = "newPassword123@",
            confirmPassword = "newPassword1223@",
            passwordValidationState = PasswordValidationState(
                hasMinLength = true,
                hasMaxLength = true,
                hasNumber = true,
                hasLowerCase = true,
                hasUpperCase = true,
                hasSpecialCharacter = true
            ),
            isCurrentPasswordValid = true,
            isConfirmPasswordValid = true,
            isCurrentPasswordVisible = false,
            isNewPasswordVisible = false,
            isConfirmPasswordVisible = true,
            isChangePwButtonEnabled = true,
            onChangePasswordClick = {},
            onCurrentPasswordChange = {},
            onNewPasswordChange = {},
            onConfirmPasswordChange = {},
            onCurrentPasswordChangeDebounced = {},
            onNewPasswordChangeDebounced = {},
            onToggleCurrentPasswordVisibility = {},
            onToggleNewPasswordVisibility = {},
            onToggleConfirmPasswordVisibility = {}
        )
    }
}

@Preview(showBackground = true, name = "Error State")
@Composable
private fun ChangePasswordScreenErrorPreview() {
    PyeonKingTheme {
        ChangePasswordScreenScreen(
            currentPassword = "wrongPassword",
            newPassword = "short",
            confirmPassword = "mismatch",
            passwordValidationState = PasswordValidationState(hasMinLength = false),
            isCurrentPasswordValid = false,
            isConfirmPasswordValid = false,
            isCurrentPasswordVisible = true,
            isNewPasswordVisible = false,
            isConfirmPasswordVisible = false,
            isChangePwButtonEnabled = false,
            onChangePasswordClick = {},
            onCurrentPasswordChange = {},
            onNewPasswordChange = {},
            onConfirmPasswordChange = {},
            onCurrentPasswordChangeDebounced = {},
            onNewPasswordChangeDebounced = {},
            onToggleCurrentPasswordVisibility = {},
            onToggleNewPasswordVisibility = {},
            onToggleConfirmPasswordVisibility = {}
        )
    }
}