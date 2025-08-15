package com.hjw0623.presentation.screen.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjw0623.core.business_logic.auth.validator.NicknameValidationState
import com.hjw0623.core.business_logic.auth.validator.UserDataValidator
import com.hjw0623.core.business_logic.model.network.DataResourceResult
import com.hjw0623.core.business_logic.model.request.AuthRequest
import com.hjw0623.core.business_logic.repository.AuthRepository
import com.hjw0623.core.constants.Error.DUPLICATED_NICKNAME
import com.hjw0623.core.constants.Error.UNKNOWN_ERROR
import com.hjw0623.presentation.screen.auth.register.ui.RegisterScreenEvent
import com.hjw0623.presentation.screen.auth.register.ui.RegisterScreenState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepository: AuthRepository,
    private val userDataValidator: UserDataValidator
) : ViewModel() {
    private val _state = MutableStateFlow(RegisterScreenState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<RegisterScreenEvent>()
    val event = _event.asSharedFlow()

    fun onRegisterClick() {
        if (state.value.isRegistering) return
        viewModelScope.launch {
            _state.update { it.copy(isRegistering = true) }

            val authRequest = AuthRequest(
                email = state.value.email,
                password = state.value.password,
                nickname = state.value.nickname
            )

            authRepository.register(authRequest).collectLatest { result ->
                _state.update { it.copy(isRegistering = false) }

                when (result) {
                    is DataResourceResult.Success -> {
                        val response = result.data.data
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
        if (state.value.nicknameValidationState is NicknameValidationState.Checking) return

        _state.update { it.copy(nicknameValidationState = NicknameValidationState.Checking) }

        viewModelScope.launch {
            authRepository.checkNickname(state.value.nickname).collectLatest { result ->
                when (result) {
                    is DataResourceResult.Success -> {
                        val isRegisterAvailable = result.data.data
                        _state.update {
                            it.copy(
                                nicknameValidationState = if (isRegisterAvailable) {
                                    NicknameValidationState.Valid
                                } else {
                                    NicknameValidationState.Invalid(DUPLICATED_NICKNAME)
                                }
                            )
                        }
                    }

                    is DataResourceResult.Failure -> {
                        _state.update {
                            it.copy(
                                nicknameValidationState = NicknameValidationState.Invalid(
                                    result.exception.message ?: UNKNOWN_ERROR
                                )
                            )
                        }
                    }

                    else -> Unit
                }
            }
        }
    }

    fun onEmailChange(inputEmail: String) {
        _state.update { it.copy(email = inputEmail.trim()) }
    }

    fun onEmailChangeDebounced(debouncedEmail: String) {
        val isValid = userDataValidator.isEmailValid(debouncedEmail.trim())
        _state.update { it.copy(isEmailValid = isValid) }
    }

    fun onPasswordChange(password: String) {
        _state.update { it.copy(password = password) }
    }

    fun onPasswordChangeDebounced(password: String) {
        val validationState = userDataValidator.isPasswordValid(password.trim())
        _state.update { it.copy(passwordValidationState = validationState) }
    }

    fun onTogglePasswordVisibility() {
        _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun onNicknameChange(nickname: String) {
        _state.update {
            it.copy(
                nickname = nickname.trim(),
                nicknameValidationState = NicknameValidationState.Idle
            )
        }
    }
}