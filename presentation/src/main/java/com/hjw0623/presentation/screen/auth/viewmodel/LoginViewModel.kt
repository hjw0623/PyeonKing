package com.hjw0623.presentation.screen.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjw0623.core.domain.auth.UserDataValidator
import com.hjw0623.presentation.screen.auth.login.ui.LoginScreenEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    //private val authRepository: AuthRepository,
    private val userDataValidator: UserDataValidator
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _isLoggingIn = MutableStateFlow(false)
    val isLoggingIn = _isLoggingIn.asStateFlow()

    private val _isPasswordVisible = MutableStateFlow(false)
    val isPasswordVisible = _isPasswordVisible.asStateFlow()
    val isEmailValid = email.map {
        userDataValidator.isEmailValid(it)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val isLoginButtonEnabled = combine(isEmailValid, password) { isEmailValid, password ->
        isEmailValid && password.isNotBlank()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    private val _event = MutableSharedFlow<LoginScreenEvent>()
    val event = _event.asSharedFlow()

    fun onEmailChange(inputEmail: String) {
        _email.value = inputEmail
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
            // TODO: 실제 로그인 로직 구현
            // 성공 시
            _event.emit(LoginScreenEvent.NavigateToMyPage)
            // 실패 시
            // _event.emit(LoginEvent.Error("이메일 또는 비밀번호가 일치하지 않습니다."))
            _isLoggingIn.value = false
        }
    }

    fun onRegisterClick() {
        viewModelScope.launch {
            _event.emit(LoginScreenEvent.NavigateToRegister)
        }
    }
}