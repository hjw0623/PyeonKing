package com.hjw0623.presentation.screen.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjw0623.core.domain.auth.NicknameValidationState
import com.hjw0623.core.domain.auth.PasswordValidationState
import com.hjw0623.core.domain.auth.UserDataValidator
import com.hjw0623.core.util.mockdata.mockTakenNicknames
import com.hjw0623.presentation.screen.auth.register.ui.RegisterScreenEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    // TODO: private val authRepository: AuthRepository,
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

    fun onRegisterClick() {
        viewModelScope.launch {
            _isRegistering.value = true
            try {
                delay(1500)
                // TODO: 실제 회원가입 로직 호출
                _event.emit(RegisterScreenEvent.NavigateToRegisterSuccess)

            } catch (e: Exception) {
                _event.emit(RegisterScreenEvent.Error("회원가입에 실패했습니다. 잠시 후 다시 시도해주세요."))
            } finally {
                _isRegistering.value = false
            }
        }
    }

    fun onNicknameCheckClick() {
        viewModelScope.launch {
            _nicknameValidationState.value = NicknameValidationState.Checking
            try {
                delay(1000)
                // TODO: 실제 닉네임 중복 확인 로직 호출

                if (mockTakenNicknames.contains(_nickname.value) || _nickname.value.isBlank()) {
                    _nicknameValidationState.value = NicknameValidationState.Invalid("이미 사용 중인 닉네임입니다.")
                } else {
                    _nicknameValidationState.value = NicknameValidationState.Valid
                }
            } catch (e: Exception) {
                _nicknameValidationState.value = NicknameValidationState.Idle
                _event.emit(RegisterScreenEvent.Error("닉네임 확인 중 오류가 발생했습니다."))
            }
        }
    }

    fun onEmailChange(inputEmail: String) {
        _email.value = inputEmail
    }

    fun onEmailChangeDebounced(debouncedEmail: String) {
        val isValid = userDataValidator.isEmailValid(debouncedEmail)
        _isEmailValid.value = isValid
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun onPasswordChangeDebounced(password: String) {
        val validationState = userDataValidator.isPasswordValid(password)
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