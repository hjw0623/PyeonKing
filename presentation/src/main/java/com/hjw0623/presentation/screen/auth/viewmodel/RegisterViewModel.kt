package com.hjw0623.presentation.screen.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjw0623.core.core_domain.auth.validator.NicknameValidationState
import com.hjw0623.core.core_domain.auth.validator.UserDataValidator
import com.hjw0623.core.core_network.network.DataResourceResult
import com.hjw0623.core.core_network.request.AuthRequest
import com.hjw0623.core.core_domain.repository.AuthRepository
import com.hjw0623.core.android.constants.Error.DUPLICATED_NICKNAME
import com.hjw0623.core.android.constants.Error.UNKNOWN_ERROR
import com.hjw0623.presentation.screen.auth.register.ui.RegisterScreenEvent
import com.hjw0623.presentation.screen.auth.register.ui.RegisterScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userDataValidator: UserDataValidator
) : ViewModel() {
    private val _state = MutableStateFlow(RegisterScreenState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<RegisterScreenEvent>()
    val event = _event.asSharedFlow()

    fun onRegisterClick() {
        val currentState = state.value
        if (currentState.isRegistering) return

        viewModelScope.launch {
            authRepository.register(
                AuthRequest(
                    email = currentState.email.trim(),
                    password = currentState.password,
                    nickname = currentState.nickname
                )
            ).collectLatest { result ->
                _state.update {
                    when (result) {
                        is DataResourceResult.Loading -> it.copy(isRegistering = true)
                        is DataResourceResult.Success -> {
                            _event.emit(RegisterScreenEvent.NavigateToRegisterSuccess)
                            it.copy(
                                isRegistering = false,
                                email = "",
                                password = "",
                                nickname = ""
                            )
                        }

                        is DataResourceResult.Failure -> {
                            _event.emit(
                                RegisterScreenEvent.Error(
                                    result.exception.message ?: UNKNOWN_ERROR
                                )
                            )
                            it.copy(isRegistering = false)
                        }

                        is DataResourceResult.DummyConstructor -> it
                    }
                }
            }
        }
    }

    fun onNicknameCheckClick() {
        val currentState = state.value
        if (currentState.nicknameValidationState is NicknameValidationState.Checking) return

        viewModelScope.launch {
            authRepository.checkNickname(currentState.nickname).collectLatest { result ->
                _state.update {
                    when (result) {
                        is DataResourceResult.Loading -> it.copy(
                            nicknameValidationState = NicknameValidationState.Checking
                        )

                        is DataResourceResult.Success -> {
                            val isAvailable = result.data.data
                            it.copy(
                                nicknameValidationState = if (isAvailable) {
                                    NicknameValidationState.Valid
                                } else {
                                    NicknameValidationState.Invalid(DUPLICATED_NICKNAME)
                                }
                            )
                        }

                        is DataResourceResult.Failure -> {
                            it.copy(
                                nicknameValidationState = NicknameValidationState.Invalid(
                                    result.exception.message ?: UNKNOWN_ERROR
                                )
                            )
                        }

                        is DataResourceResult.DummyConstructor -> it
                    }
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