package com.hjw0623.presentation.screen.auth.register.ui

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
import androidx.compose.material.icons.filled.Email
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
import com.hjw0623.core.domain.auth.EmailPatternValidator
import com.hjw0623.core.domain.auth.NicknameValidationState
import com.hjw0623.core.domain.auth.PasswordValidationState
import com.hjw0623.core.domain.auth.UserDataValidator
import com.hjw0623.core.presentation.designsystem.components.PyeonKingButton
import com.hjw0623.core.presentation.designsystem.components.PyeonKingPasswordTextField
import com.hjw0623.core.presentation.designsystem.components.PyeonKingTextField
import com.hjw0623.core.presentation.designsystem.components.showToast
import com.hjw0623.core.presentation.designsystem.theme.PyeonKingTheme
import com.hjw0623.core.presentation.ui.ObserveAsEvents
import com.hjw0623.core.presentation.ui.rememberThrottledOnClick
import com.hjw0623.presentation.R
import com.hjw0623.presentation.screen.auth.register.ui.component.PasswordRequirement
import com.hjw0623.presentation.screen.auth.viewmodel.RegisterViewModel
import com.hjw0623.presentation.screen.factory.RegisterViewModelFactory

@Composable
fun RegisterScreenRoot(
    onNavigateToRegisterSuccess: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val userDataValidator = UserDataValidator(EmailPatternValidator)
    val registerViewModel = RegisterViewModelFactory(userDataValidator)
    val viewModel: RegisterViewModel = viewModel(factory = registerViewModel)

    val context = LocalContext.current

    val email by viewModel.email.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    val nickname by viewModel.nickname.collectAsStateWithLifecycle()

    val isEmailValid by viewModel.isEmailValid.collectAsStateWithLifecycle()
    val passwordValidationState by viewModel.passwordValidationState.collectAsStateWithLifecycle()
    val nicknameValidationState by viewModel.nicknameValidationState.collectAsStateWithLifecycle()
    val isRegisterButtonEnabled by viewModel.isRegisterButtonEnabled.collectAsStateWithLifecycle()
    val isPasswordVisible by viewModel.isPasswordVisible.collectAsStateWithLifecycle()

    val throttledRegisterClick = rememberThrottledOnClick {
        viewModel.onRegisterClick()
    }
    val throttledNicknameCheckClick = rememberThrottledOnClick {
        viewModel.onNicknameCheckClick()
    }

    ObserveAsEvents(flow = viewModel.event) { event ->
        when (event) {
            is RegisterScreenEvent.Error -> {
                showToast(context, event.error)
            }

            RegisterScreenEvent.NavigateToRegisterSuccess -> {
                onNavigateToRegisterSuccess()
            }
        }
    }


    RegisterScreen(
        modifier = modifier,
        email = email,
        password = password,
        nickname = nickname,
        isEmailValid = isEmailValid,
        passwordValidationState = passwordValidationState,
        isPasswordVisible = isPasswordVisible,
        nicknameValidationState = nicknameValidationState,
        isRegisterButtonEnabled = isRegisterButtonEnabled,
        onEmailChange = viewModel::onEmailChange,
        onEmailChangeDebounced = viewModel::onEmailChangeDebounced,
        onPasswordChange = viewModel::onPasswordChange,
        onPasswordChangeDebounced = viewModel::onPasswordChangeDebounced,
        onNicknameChange = viewModel::onNicknameChange,
        onNicknameCheckClick = throttledNicknameCheckClick,
        onRegisterClick = throttledRegisterClick,
        onTogglePasswordVisibility = viewModel::onTogglePasswordVisibility,
    )
}

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    email: String,
    password: String,
    nickname: String,
    isEmailValid: Boolean,
    passwordValidationState: PasswordValidationState,
    isPasswordVisible: Boolean,
    nicknameValidationState: NicknameValidationState,
    isRegisterButtonEnabled: Boolean,
    onEmailChange: (String) -> Unit,
    onEmailChangeDebounced: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordChangeDebounced: (String) -> Unit,
    onNicknameChange: (String) -> Unit,
    onNicknameCheckClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onTogglePasswordVisibility: () -> Unit,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(vertical = 32.dp)
            .padding(top = 16.dp)
    ) {

        Text(
            text = stringResource(R.string.label_register),
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(R.string.input_email_password),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom
        ) {
            PyeonKingTextField(
                value = nickname,
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
                    enabled = nickname.isNotBlank() && nicknameValidationState !is NicknameValidationState.Valid,
                    modifier = Modifier.height(56.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp)
                )
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        PyeonKingTextField(
            value = email,
            onValueChange = onEmailChange,
            onDebouncedValueChange = onEmailChangeDebounced,
            startIcon = Icons.Default.Email,
            endIcon = if (isEmailValid) Icons.Default.Check else null,
            title = stringResource(R.string.label_email),
            hint = stringResource(R.string.login_hint_email),
            error = if (email.isNotBlank() && !isEmailValid) stringResource(
                R.string.email_input_error
            ) else null,
            keyboardType = KeyboardType.Email,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        PyeonKingPasswordTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = onPasswordChange,
            onDebouncedValueChange = onPasswordChangeDebounced,
            isPasswordVisible = isPasswordVisible,
            onTogglePasswordVisibility = onTogglePasswordVisibility,
            title = stringResource(R.string.label_password),
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

        Spacer(modifier = Modifier.weight(1f))

        PyeonKingButton(
            text = stringResource(R.string.label_register),
            onClick = onRegisterClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            enabled = isRegisterButtonEnabled,
            contentPadding = PaddingValues(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterScreenWithPyeonKingFieldPreview() {
    PyeonKingTheme {
        RegisterScreen(
            modifier = Modifier,
            email = "test@pking.com",
            password = "password123!",
            nickname = "편킹왕",
            isEmailValid = true,
            passwordValidationState = PasswordValidationState(
                hasMinLength = true,
                hasMaxLength = true,
                hasNumber = true,
                hasLowerCase = true,
                hasUpperCase = true,
                hasSpecialCharacter = true
            ),
            isPasswordVisible = false,
            nicknameValidationState = NicknameValidationState.Valid,
            isRegisterButtonEnabled = true,
            onEmailChange = {},
            onEmailChangeDebounced = {},
            onPasswordChange = {},
            onPasswordChangeDebounced = {},
            onNicknameChange = {},
            onNicknameCheckClick = {},
            onRegisterClick = {},
            onTogglePasswordVisibility = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterScreenWithPyeonKingFieldErrorPreview() {
    PyeonKingTheme {
        RegisterScreen(
            modifier = Modifier,
            email = "test@",
            password = "123",
            nickname = "킹",
            isEmailValid = false,
            passwordValidationState = PasswordValidationState(
                hasMinLength = false,
                hasMaxLength = false,
                hasNumber = false,
                hasLowerCase = false,
                hasUpperCase = false,
                hasSpecialCharacter = false
            ),
            isPasswordVisible = true,
            nicknameValidationState = NicknameValidationState.Invalid("중복된 닉네임 입니다."),
            isRegisterButtonEnabled = false,
            onEmailChange = {},
            onEmailChangeDebounced = {},
            onPasswordChange = {},
            onPasswordChangeDebounced = {},
            onNicknameChange = {},
            onNicknameCheckClick = {},
            onRegisterClick = {},
            onTogglePasswordVisibility = {},
        )
    }
}
