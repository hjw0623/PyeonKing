package com.hjw0623.presentation.screen.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjw0623.core.constants.Error.UNCHANGED_NICKNAME
import com.hjw0623.core.data.model.ChangePasswordRequest
import com.hjw0623.core.domain.AuthManager
import com.hjw0623.core.domain.auth.NicknameValidationState
import com.hjw0623.core.domain.auth.PasswordValidationState
import com.hjw0623.core.domain.auth.UserDataValidator
import com.hjw0623.core.domain.mypage.MyPageRepository
import com.hjw0623.core.network.DataResourceResult
import com.hjw0623.presentation.screen.mypage.change_nickname.ui.ChangeNicknameScreenEvent
import com.hjw0623.presentation.screen.mypage.change_password.ui.ChangePasswordScreenEvent
import com.hjw0623.presentation.screen.mypage.mypage_main.ui.MyPageScreenEvent
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

class MyPageViewModel(
    private val myPageRepository: MyPageRepository,
    private val userDataValidator: UserDataValidator,
    private val authManager: AuthManager = AuthManager
) : ViewModel() {

    // --------------------------------------------------
    // 닉네임 변경 관련
    // --------------------------------------------------

    private val _currentNickname = MutableStateFlow(authManager.userData.value?.nickname ?: "")
    private val _newNickname = MutableStateFlow(authManager.userData.value?.nickname ?: "")
    val newNickname = _newNickname.asStateFlow()

    private val _nicknameValidationState =
        MutableStateFlow<NicknameValidationState>(NicknameValidationState.Idle)
    val nicknameValidationState = _nicknameValidationState.asStateFlow()

    private val _isChangingNickname = MutableStateFlow(false)

    val isChangeButtonEnabled = combine(
        _currentNickname, newNickname, nicknameValidationState, _isChangingNickname
    ) { current, new, validation, changing ->
        validation is NicknameValidationState.Valid && current != new && !changing
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), false)

    private val _changeNicknameEvent = MutableSharedFlow<ChangeNicknameScreenEvent>()
    val changeNicknameEvent = _changeNicknameEvent.asSharedFlow()

    fun onNicknameChange(nickname: String) {
        _newNickname.value = nickname
        if (_nicknameValidationState.value !is NicknameValidationState.Idle) {
            _nicknameValidationState.value = NicknameValidationState.Idle
        }
    }


    fun onNicknameCheckClick() {
        if (newNickname.value == _currentNickname.value) {
            _nicknameValidationState.value = NicknameValidationState.Invalid(UNCHANGED_NICKNAME)
            return
        }

        viewModelScope.launch {
            _nicknameValidationState.value = NicknameValidationState.Checking
            myPageRepository.checkNickname(newNickname.value).collectLatest { result ->
                when (result) {
                    is DataResourceResult.Success -> {
                        if (result.data.data) {
                            _nicknameValidationState.value = NicknameValidationState.Valid
                        } else {
                            val message = result.data.message
                            _nicknameValidationState.value =
                                NicknameValidationState.Invalid(message)
                        }
                    }

                    is DataResourceResult.Failure -> {
                        val errorMsg = result.exception.message.toString()
                        _nicknameValidationState.value = NicknameValidationState.Idle
                        _changeNicknameEvent.emit(ChangeNicknameScreenEvent.Error(errorMsg))
                    }

                    DataResourceResult.Loading -> {
                        _nicknameValidationState.value = NicknameValidationState.Checking
                    }

                    else -> Unit
                }
            }
        }
    }


    fun onChangeNicknameClick() {
        viewModelScope.launch {
            _isChangingNickname.value = true
            myPageRepository.changeNickname(_newNickname.value).collectLatest { result ->
                when (result) {
                    is DataResourceResult.Success -> {
                        if (result.data.data) {
                            authManager.userData.value?.let { user ->
                                authManager.updateUserData(user.copy(nickname = _newNickname.value))
                            }
                            _changeNicknameEvent.emit(
                                ChangeNicknameScreenEvent.NavigateToMyPage(result.data.message)
                            )
                        } else {
                            _changeNicknameEvent.emit(
                                ChangeNicknameScreenEvent.Error(result.data.message)
                            )
                        }
                    }

                    is DataResourceResult.Failure -> {
                        val errorMsg = result.exception.message.toString()
                        _changeNicknameEvent.emit(ChangeNicknameScreenEvent.Error(errorMsg))
                    }

                    else -> Unit
                }
                _isChangingNickname.value = false
            }
        }
    }

    // --------------------------------------------------
    // 비밀번호 변경 관련
    // --------------------------------------------------

    private val _userEmail = MutableStateFlow(authManager.userData.value?.email ?: "")
    private val _currentPassword = MutableStateFlow("")
    val currentPassword = _currentPassword.asStateFlow()

    private val _newPassword = MutableStateFlow("")
    val newPassword = _newPassword.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword = _confirmPassword.asStateFlow()

    private val _isCurrentPasswordValid = MutableStateFlow(false)
    val isCurrentPasswordValid = _isCurrentPasswordValid.asStateFlow()

    private val _isCurrentPasswordVisible = MutableStateFlow(false)
    val isCurrentPasswordVisible = _isCurrentPasswordVisible.asStateFlow()

    private val _isNewPasswordVisible = MutableStateFlow(false)
    val isNewPasswordVisible = _isNewPasswordVisible.asStateFlow()

    private val _isConfirmPasswordVisible = MutableStateFlow(false)
    val isConfirmPasswordVisible = _isConfirmPasswordVisible.asStateFlow()

    private val _passwordValidationState = MutableStateFlow(PasswordValidationState())
    val passwordValidationState = _passwordValidationState.asStateFlow()

    private val _isChangingPassword = MutableStateFlow(false)
    val isChangingPassword = _isChangingPassword.asStateFlow()

    val isConfirmPasswordValid = combine(newPassword, confirmPassword) { new, confirm ->
        new.isNotEmpty() && new == confirm
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), false)

    val isChangePwButtonEnabled = combine(
        isCurrentPasswordValid,
        passwordValidationState,
        isConfirmPasswordValid,
        isChangingPassword
    ) { isValidCurrent, pwState, isValidConfirm, isChanging ->
        isValidCurrent && pwState.isValidPassword && isValidConfirm && !isChanging
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), false)

    private val _changePasswordEvent = MutableSharedFlow<ChangePasswordScreenEvent>()
    val changePasswordEvent = _changePasswordEvent.asSharedFlow()

    fun onChangePasswordClick() {
        viewModelScope.launch {
            _isChangingPassword.value = true
            val request = ChangePasswordRequest(
                email = _userEmail.value,
                password = _currentPassword.value,
                newPassword = _newPassword.value,
            )

            myPageRepository.changePassword(request).collectLatest { result ->
                when (result) {
                    is DataResourceResult.Success -> {
                        val message = result.data.message
                        _changePasswordEvent.emit(ChangePasswordScreenEvent.NavigateToMyPage(message))
                    }

                    is DataResourceResult.Failure -> {
                        val errorMsg = result.exception.message.toString()
                        _changePasswordEvent.emit(ChangePasswordScreenEvent.Error(errorMsg))
                    }

                    else -> Unit
                }
                _isChangingPassword.value = false
            }
        }
    }

    fun onCurrentPasswordChange(password: String) {
        _currentPassword.value = password
    }

    fun onCurrentPasswordChangeDebounced(password: String) {
        val stored = AuthManager.userData.value?.password ?: ""
        _isCurrentPasswordValid.value = password == stored
    }

    fun onNewPasswordChange(password: String) {
        _newPassword.value = password
    }

    fun onNewPasswordChangeDebounced(password: String) {
        _passwordValidationState.value = userDataValidator.isPasswordValid(password)
    }

    fun onConfirmPasswordChange(password: String) {
        _confirmPassword.value = password
    }

    fun onToggleCurrentPasswordVisibility() {
        _isCurrentPasswordVisible.update { !it }
    }

    fun onToggleNewPasswordVisibility() {
        _isNewPasswordVisible.update { !it }
    }

    fun onToggleConfirmPasswordVisibility() {
        _isConfirmPasswordVisible.update { !it }
    }

    // --------------------------------------------------
    // 마이페이지 이동 관련
    // --------------------------------------------------

    private val _myPageScreenEvent = MutableSharedFlow<MyPageScreenEvent>()
    val myPageScreenEvent = _myPageScreenEvent.asSharedFlow()

    fun onLoginClick() = emitEvent(_myPageScreenEvent, MyPageScreenEvent.NavigateToLogin)
    fun onLogoutClick() = AuthManager.logout()
    fun navigateToChangePassword() =
        emitEvent(_myPageScreenEvent, MyPageScreenEvent.NavigateToChangePassword)

    fun navigateToChangeNickname() =
        emitEvent(_myPageScreenEvent, MyPageScreenEvent.NavigateToChangeNickname)

    fun navigateToReviewHistory() =
        emitEvent(_myPageScreenEvent, MyPageScreenEvent.NavigateToReviewHistory)

    // --------------------------------------------------
    // Util
    // --------------------------------------------------

    private fun <T> emitEvent(flow: MutableSharedFlow<T>, event: T) {
        viewModelScope.launch { flow.emit(event) }
    }
}
