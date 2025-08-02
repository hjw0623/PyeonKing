package com.hjw0623.presentation.screen.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjw0623.core.constants.Error.DUPLICATED_NICKNAME
import com.hjw0623.core.constants.Error.UNKNOWN_ERROR
import com.hjw0623.core.business_logic.model.request.AuthRequest
import com.hjw0623.core.business_logic.model.response.AuthResponse
import com.hjw0623.core.business_logic.model.response.BaseResponse
import com.hjw0623.core.business_logic.repository.AuthRepository
import com.hjw0623.core.business_logic.auth.validator.NicknameValidationState
import com.hjw0623.core.business_logic.auth.validator.PasswordValidationState
import com.hjw0623.core.business_logic.auth.validator.UserDataValidator
import com.hjw0623.core.business_logic.model.network.DataResourceResult
import com.hjw0623.presentation.screen.auth.register.ui.RegisterScreenEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepository: AuthRepository,
    private val userDataValidator: UserDataValidator
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _nickname = MutableStateFlow("")
    val nickname = _nickname.asStateFlow()

    private val _isEmailValid = MutableStateFlow(false)
    val isEmailValid = _isEmailValid.asStateFlow()

    private val _passwordValidationState = MutableStateFlow(PasswordValidationState())
    val passwordValidationState = _passwordValidationState.asStateFlow()

    private val _isPasswordVisible = MutableStateFlow(false)
    val isPasswordVisible = _isPasswordVisible.asStateFlow()

    private val _nicknameValidationState =
        MutableStateFlow<NicknameValidationState>(NicknameValidationState.Idle)
    val nicknameValidationState = _nicknameValidationState.asStateFlow()

    private val _isRegistering = MutableStateFlow(false)
    val isRegistering = _isRegistering.asStateFlow()

    val isRegisterButtonEnabled = combine(
        _isEmailValid,
        _passwordValidationState,
        _nicknameValidationState,
        _isRegistering
    ) { isEmailValid, passwordValidationState, nicknameValidationState, isRegistering ->
        isEmailValid &&
                passwordValidationState.isValidPassword
        nicknameValidationState is NicknameValidationState.Valid &&
                !isRegistering
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    private val _event = MutableSharedFlow<RegisterScreenEvent>()
    val event = _event.asSharedFlow()

    private val _registerResult = MutableStateFlow<DataResourceResult<BaseResponse<AuthResponse>>>(
        DataResourceResult.DummyConstructor
    )
    val registerResult = _registerResult.asStateFlow()

    fun onRegisterClick() {
        viewModelScope.launch {
            _isRegistering.value = true
            _registerResult.value = DataResourceResult.Loading

            val authRequest = AuthRequest(
                email = _email.value,
                password = _password.value,
                nickname = _nickname.value
            )

            authRepository.register(authRequest).collectLatest { result ->
                _registerResult.value = result
                _isRegistering.value = false

                when (result) {
                    is DataResourceResult.Success -> {
                        _event.emit(RegisterScreenEvent.NavigateToRegisterSuccess)
                    }

                    is DataResourceResult.Failure -> {
                        val message = result.exception.message ?: UNKNOWN_ERROR
                        _event.emit(RegisterScreenEvent.Error(message))
                    }

                    else -> Unit
                }
            }
        }
    }

    fun onNicknameCheckClick() {
        viewModelScope.launch {
            _nicknameValidationState.value = NicknameValidationState.Checking

            authRepository.checkNickname(_nickname.value).collectLatest { result ->
                when (result) {
                    is DataResourceResult.Success -> {
                        _nicknameValidationState.value = if (result.data.data) {
                            NicknameValidationState.Valid
                        } else {
                            NicknameValidationState.Invalid(DUPLICATED_NICKNAME)
                        }
                    }

                    is DataResourceResult.Failure -> {
                        val message = result.exception.message ?: UNKNOWN_ERROR
                        _nicknameValidationState.value = NicknameValidationState.Invalid(message)
                    }

                    else -> Unit
                }
            }
        }
    }

    fun onEmailChange(inputEmail: String) {
        _email.value = inputEmail.trim()
    }

    fun onEmailChangeDebounced(debouncedEmail: String) {
        val isValid = userDataValidator.isEmailValid(debouncedEmail.trim())
        _isEmailValid.value = isValid
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun onPasswordChangeDebounced(password: String) {
        val validationState = userDataValidator.isPasswordValid(password.trim())
        _passwordValidationState.value = validationState
    }

    fun onTogglePasswordVisibility() {
        _isPasswordVisible.update { !it }
    }

    fun onNicknameChange(nickname: String) {
        _nickname.value = nickname
        if (_nicknameValidationState.value !is NicknameValidationState.Idle) {
            _nicknameValidationState.value = NicknameValidationState.Idle
        }
    }
}