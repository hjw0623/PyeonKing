package com.hjw0623.presentation.screen.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjw0623.core.constants.Error.UNKNOWN_ERROR
import com.hjw0623.core.business_logic.model.request.AuthRequest
import com.hjw0623.core.business_logic.model.response.AuthResponse
import com.hjw0623.core.business_logic.model.response.BaseResponse
import com.hjw0623.core.business_logic.auth.AuthManager
import com.hjw0623.core.business_logic.repository.AuthRepository
import com.hjw0623.core.business_logic.auth.validator.UserDataValidator
import com.hjw0623.core.business_logic.model.mypage.User
import com.hjw0623.core.business_logic.model.network.DataResourceResult
import com.hjw0623.presentation.screen.auth.login.ui.LoginScreenEvent
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

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val userDataValidator: UserDataValidator,
    private val authManager: AuthManager = AuthManager
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _isLoggingIn = MutableStateFlow(false)
    val isLoggingIn = _isLoggingIn.asStateFlow()

    private val _isPasswordVisible = MutableStateFlow(false)
    val isPasswordVisible = _isPasswordVisible.asStateFlow()
    private val _isEmailValid = MutableStateFlow(false)
    val isEmailValid = _isEmailValid.asStateFlow()
    val isLoginButtonEnabled = combine(isEmailValid, password) { isEmailValid, password ->
        isEmailValid && password.isNotBlank()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)
    private val _loginResult =
        MutableStateFlow<DataResourceResult<BaseResponse<AuthResponse>>>(DataResourceResult.DummyConstructor)
    val loginResult = _loginResult.asStateFlow()
    private val _event = MutableSharedFlow<LoginScreenEvent>()
    val event = _event.asSharedFlow()

    fun onEmailChange(inputEmail: String) {
        if (inputEmail != _email.value) {
            _isEmailValid.value = false
        }
        _email.value = inputEmail
    }

    fun onEmailChangeDebounced(debouncedEmail: String) {
        val isValid = userDataValidator.isEmailValid(debouncedEmail)
        _isEmailValid.value = isValid
    }

    fun onPasswordChange(inputPassword: String) {
        _password.value = inputPassword
    }

    fun onTogglePasswordVisibility() {
        _isPasswordVisible.update { !it }
    }

    fun onLoginClick() {
        viewModelScope.launch {
            _isLoggingIn.value = true
            _loginResult.value = DataResourceResult.Loading

            val authRequest = AuthRequest(
                email = email.value,
                password = password.value
            )

            authRepository.login(authRequest).collectLatest { result ->
                _loginResult.value = result
                _isLoggingIn.value = false

                when (result) {
                    is DataResourceResult.Success -> {
                        val response = result.data.data

                        val user = User(
                            email = email.value,
                            nickname = response.nickname,
                            password = password.value,
                            accessToken = response.accessToken,
                            refreshToken = response.refreshToken
                        )
                        authManager.login(user)
                        _event.emit(LoginScreenEvent.NavigateToMyPage)
                    }

                    is DataResourceResult.Failure -> {
                        val message = result.exception.message ?: UNKNOWN_ERROR
                        _event.emit(LoginScreenEvent.Error(message))
                    }

                    else -> Unit
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