package com.hjw0623.presentation.screen.auth.register.ui

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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hjw0623.core.domain.auth.validator.EmailPatternValidator
import com.hjw0623.core.domain.auth.validator.NicknameValidationState
import com.hjw0623.core.domain.auth.validator.UserDataValidator
import com.hjw0623.core.ui.designsystem.components.LoadingButton
import com.hjw0623.core.ui.designsystem.components.PyeonKingButton
import com.hjw0623.core.ui.designsystem.components.PyeonKingPasswordTextField
import com.hjw0623.core.ui.designsystem.components.PyeonKingTextField
import com.hjw0623.core.ui.designsystem.components.showToast
import com.hjw0623.core.ui.designsystem.theme.PyeonKingTheme
import com.hjw0623.core.ui.util.ObserveAsEvents
import com.hjw0623.core.ui.util.rememberThrottledOnClick
import com.hjw0623.presentation.R
import com.hjw0623.presentation.screen.auth.register.ui.component.PasswordRequirement
import com.hjw0623.presentation.screen.auth.viewmodel.RegisterViewModel

@Composable
fun RegisterScreenRoot(
    modifier: Modifier = Modifier,
    onNavigateToRegisterSuccess: () -> Unit,
    registerViewModel: RegisterViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by registerViewModel.state.collectAsStateWithLifecycle()

    val throttledRegisterClick = rememberThrottledOnClick {
        registerViewModel.onRegisterClick()
    }
    val throttledNicknameCheckClick = rememberThrottledOnClick {
        registerViewModel.onNicknameCheckClick()
    }

    ObserveAsEvents(flow = registerViewModel.event) { event ->
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
        state = state,
        onEmailChange = registerViewModel::onEmailChange,
        onEmailChangeDebounced = registerViewModel::onEmailChangeDebounced,
        onPasswordChange = registerViewModel::onPasswordChange,
        onPasswordChangeDebounced = registerViewModel::onPasswordChangeDebounced,
        onNicknameChange = registerViewModel::onNicknameChange,
        onNicknameCheckClick = throttledNicknameCheckClick,
        onRegisterClick = throttledRegisterClick,
        onTogglePasswordVisibility = registerViewModel::onTogglePasswordVisibility,
    )
}

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    state: RegisterScreenState,
    onEmailChange: (String) -> Unit,
    onEmailChangeDebounced: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordChangeDebounced: (String) -> Unit,
    onNicknameChange: (String) -> Unit,
    onNicknameCheckClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onTogglePasswordVisibility: () -> Unit,
) {
    val isChecking = state.nicknameValidationState == NicknameValidationState.Checking
    val focusManager = LocalFocusManager.current
    val keyboard = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 32.dp)
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
                value = state.nickname,
                onValueChange = onNicknameChange,
                startIcon = Icons.Default.Badge,
                endIcon = if (state.nicknameValidationState is NicknameValidationState.Valid) Icons.Default.Check else null,
                title = stringResource(R.string.label_nickname),
                error = if (state.nicknameValidationState is NicknameValidationState.Invalid) {
                    state.nicknameValidationState.message
                } else null,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(16.dp))


            LoadingButton(
                text = stringResource(R.string.action_duplicate_check),
                onClick = onNicknameCheckClick,
                loading = isChecking,
                enabled = state.nickname.isNotBlank()
                        && state.nicknameValidationState !is NicknameValidationState.Valid,
                modifier = Modifier.height(56.dp),
                contentPadding = PaddingValues(horizontal = 12.dp),
                fullWidth = false
            )

        }


        Spacer(modifier = Modifier.height(16.dp))

        PyeonKingTextField(
            value = state.email,
            onValueChange = onEmailChange,
            onDebouncedValueChange = onEmailChangeDebounced,
            startIcon = Icons.Default.Email,
            endIcon = if (state.isEmailValid) Icons.Default.Check else null,
            title = stringResource(R.string.label_email),
            hint = stringResource(R.string.login_hint_email),
            error = if (state.email.isNotBlank() && !state.isEmailValid) stringResource(
                R.string.email_input_error
            ) else null,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        PyeonKingPasswordTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.password,
            onValueChange = onPasswordChange,
            onDebouncedValueChange = onPasswordChangeDebounced,
            isPasswordVisible = state.isPasswordVisible,
            onTogglePasswordVisibility = onTogglePasswordVisibility,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                if (state.isRegisterButtonEnabled && !state.isRegistering) {
                    keyboard?.hide()
                    onRegisterClick()
                }
            }),
            title = stringResource(R.string.label_password),
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

        Spacer(modifier = Modifier.weight(1f))

        PyeonKingButton(
            text = stringResource(R.string.label_register),
            onClick = onRegisterClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            enabled = state.isRegisterButtonEnabled,
            contentPadding = PaddingValues(16.dp)
        )
    }
}

@Preview(showBackground = true, name = "기본 상태")
@Composable
private fun RegisterScreenDefaultPreview() {
    PyeonKingTheme {
        RegisterScreen(
            state = RegisterScreenState(),
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

@Preview(showBackground = true, name = "모든 유효성 검사 통과 상태")
@Composable
private fun RegisterScreenValidPreview() {
    PyeonKingTheme {
        RegisterScreen(
            state = RegisterScreenState(
                email = "test@pking.com",
                isEmailValid = true,
                password = "Passw0rd!",
                passwordValidationState = UserDataValidator(EmailPatternValidator).isPasswordValid("Password!123"),
                nickname = "편킹왕",
                nicknameValidationState = NicknameValidationState.Valid,
                isPasswordVisible = false
            ),
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

@Preview(showBackground = true, name = "유효성 검사 실패 상태")
@Composable
private fun RegisterScreenInvalidPreview() {
    PyeonKingTheme {
        RegisterScreen(
            state = RegisterScreenState(
                email = "wrongemail",
                isEmailValid = false,
                password = "123",
                passwordValidationState = UserDataValidator(EmailPatternValidator).isPasswordValid("123"),
                nickname = "편킹",
                nicknameValidationState = NicknameValidationState.Invalid("중복된 닉네임입니다."),
                isPasswordVisible = true
            ),
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