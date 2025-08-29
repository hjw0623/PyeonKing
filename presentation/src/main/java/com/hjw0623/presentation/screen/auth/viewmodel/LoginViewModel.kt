package com.hjw0623.presentation.screen.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjw0623.core.domain.auth.validator.UserDataValidator
import com.hjw0623.core.core_network.network.DataResourceResult
import com.hjw0623.core.core_network.request.AuthRequest
import com.hjw0623.core.domain.repository.AuthRepository
import com.hjw0623.core.domain.repository.UserDataStoreRepository
import com.hjw0623.core.android.constants.Error.UNKNOWN_ERROR
import com.hjw0623.presentation.screen.auth.login.ui.LoginScreenEvent
import com.hjw0623.presentation.screen.auth.login.ui.LoginScreenState
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
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userDataValidator: UserDataValidator,
    private val userDataStoreRepository: UserDataStoreRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(LoginScreenState())
    val state = _state.asStateFlow()
    private val _event = MutableSharedFlow<LoginScreenEvent>()
    val event = _event.asSharedFlow()

    fun onEmailChange(inputEmail: String) {
        _state.update {
            it.copy(
                email = inputEmail,
                isEmailValid = false
            )
        }
    }

    fun onEmailChangeDebounced(debouncedEmail: String) {
        _state.update {
            it.copy(isEmailValid = userDataValidator.isEmailValid(debouncedEmail))
        }
    }

    fun onPasswordChange(inputPassword: String) {
        _state.update { it.copy(password = inputPassword) }
    }

    fun onTogglePasswordVisibility() {
        _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun onLoginClick() {
        val currentState = state.value
        if (!currentState.isEmailValid) return

        viewModelScope.launch {
            authRepository.login(
                AuthRequest(
                    email = currentState.email.trim(),
                    password = currentState.password
                )
            ).collectLatest { result ->
                _state.update {
                    when (result) {
                        is DataResourceResult.Loading -> it.copy(isLoggingIn = true)
                        is DataResourceResult.Success -> {
                            val response = result.data.data
                            userDataStoreRepository.saveUserInfo(
                                nickname = response.nickname,
                                email = currentState.email.trim(),
                                accessToken = response.accessToken,
                                refreshToken = response.refreshToken
                            )
                            _event.emit(LoginScreenEvent.NavigateToMyPage)
                            it.copy(
                                isLoggingIn = false,
                                email = "",
                                password = ""
                            )
                        }

                        is DataResourceResult.Failure -> {
                            _event.emit(
                                LoginScreenEvent.Error(
                                    result.exception.message ?: UNKNOWN_ERROR
                                )
                            )
                            it.copy(isLoggingIn = false)
                        }

                        is DataResourceResult.DummyConstructor -> it
                    }
                }
            }
        }
    }

    fun onRegisterClick() {
        viewModelScope.launch {
            _event.emit(LoginScreenEvent.NavigateToRegister)
        }
    }
}