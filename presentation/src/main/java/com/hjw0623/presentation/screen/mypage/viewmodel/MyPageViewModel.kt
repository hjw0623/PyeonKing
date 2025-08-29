package com.hjw0623.presentation.screen.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjw0623.core.domain.auth.validator.NicknameValidationState
import com.hjw0623.core.domain.auth.validator.UserDataValidator
import com.hjw0623.core.core_network.network.DataResourceResult
import com.hjw0623.core.core_network.request.ChangePasswordRequest
import com.hjw0623.core.domain.repository.MyPageRepository
import com.hjw0623.core.domain.repository.UserDataStoreRepository
import com.hjw0623.core.android.constants.Error.UNCHANGED_NICKNAME
import com.hjw0623.core.android.constants.Error.UNKNOWN_ERROR
import com.hjw0623.presentation.screen.mypage.change_nickname.ui.ChangeNicknameScreenEvent
import com.hjw0623.presentation.screen.mypage.change_nickname.ui.ChangeNicknameScreenState
import com.hjw0623.presentation.screen.mypage.change_password.ui.ChangePasswordScreenEvent
import com.hjw0623.presentation.screen.mypage.change_password.ui.ChangePasswordScreenState
import com.hjw0623.presentation.screen.mypage.mypage_main.ui.MyPageScreenEvent
import com.hjw0623.presentation.screen.mypage.mypage_main.ui.MyPageScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val myPageRepository: MyPageRepository,
    private val userDataValidator: UserDataValidator,
    private val userDataStoreRepository: UserDataStoreRepository
) : ViewModel() {
    private val _changeNicknameState = MutableStateFlow(ChangeNicknameScreenState())
    val changeNicknameState = _changeNicknameState.asStateFlow()

    private val _changeNicknameEvent = MutableSharedFlow<ChangeNicknameScreenEvent>()
    val changeNicknameEvent = _changeNicknameEvent.asSharedFlow()

    private val _changePasswordState = MutableStateFlow(ChangePasswordScreenState())
    val changePasswordState = _changePasswordState.asStateFlow()

    private val _changePasswordEvent = MutableSharedFlow<ChangePasswordScreenEvent>()
    val changePasswordEvent = _changePasswordEvent.asSharedFlow()

    private val _myPageScreenState = MutableStateFlow(MyPageScreenState())
    val myPageScreenState = _myPageScreenState.asStateFlow()

    private val _myPageScreenEvent = MutableSharedFlow<MyPageScreenEvent>()
    val myPageScreenEvent = _myPageScreenEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            userDataStoreRepository.nicknameFlow.collectLatest { nickname ->
                _myPageScreenState.update {
                    it.copy(nickname = nickname.orEmpty())
                }
            }
        }

        viewModelScope.launch {
            userDataStoreRepository.isLoggedInFlow.collectLatest { isLoggedIn ->
                _myPageScreenState.update {
                    it.copy(isLoggedIn = isLoggedIn)
                }
            }
        }

        viewModelScope.launch {
            userDataStoreRepository.nicknameFlow.collectLatest { nickname ->
                val saved = nickname.orEmpty()
                _changeNicknameState.update {
                    it.copy(
                        currentNickname = saved,
                        newNickname = if (it.newNickname == it.currentNickname) saved else it.newNickname
                    )
                }
            }
        }

        viewModelScope.launch {
            userDataStoreRepository.emailFlow.collectLatest { email ->
                _changePasswordState.update {
                    it.copy(email = email.orEmpty())
                }
            }
        }
    }

    // --------------------------------------------------
    // 닉네임 변경 관련
    // --------------------------------------------------

    fun onNicknameChange(nickname: String) {
        _changeNicknameState.update {
            it.copy(
                newNickname = nickname,
                nicknameValidationState = NicknameValidationState.Idle
            )
        }
    }

    fun onNicknameCheckClick() {
        val currentState = _changeNicknameState.value

        if (currentState.newNickname == currentState.currentNickname) {
            _changeNicknameState.update {
                it.copy(
                    nicknameValidationState = NicknameValidationState.Invalid(
                        UNCHANGED_NICKNAME
                    )
                )
            }
            return
        }

        viewModelScope.launch {
            _changeNicknameState.update {
                it.copy(nicknameValidationState = NicknameValidationState.Checking)
            }

            myPageRepository.checkNickname(currentState.newNickname).collectLatest { result ->
                _changeNicknameState.update {
                    when (result) {
                        is DataResourceResult.Loading -> it.copy(nicknameValidationState = NicknameValidationState.Checking)

                        is DataResourceResult.Success -> {
                            if (result.data.data) {
                                it.copy(nicknameValidationState = NicknameValidationState.Valid)
                            } else {
                                it.copy(
                                    nicknameValidationState = NicknameValidationState.Invalid(
                                        result.data.message
                                    )
                                )
                            }
                        }

                        is DataResourceResult.Failure -> {
                            _changeNicknameEvent.emit(
                                ChangeNicknameScreenEvent.Error(
                                    result.exception.message.toString()
                                )
                            )
                            it.copy(nicknameValidationState = NicknameValidationState.Idle)
                        }

                        is DataResourceResult.DummyConstructor -> it
                    }
                }
            }
        }
    }


    fun onChangeNicknameClick() {
        val currentState = _changeNicknameState.value

        viewModelScope.launch {
            myPageRepository.checkNickname(currentState.newNickname).collectLatest { result ->
                when (result) {
                    is DataResourceResult.Loading -> {
                        _changeNicknameState.update { it.copy(isChangingNickname = true) }
                    }

                    is DataResourceResult.Success -> {
                        _changeNicknameState.update { it.copy(isChangingNickname = false) }
                        if (result.data.data) {
                            userDataStoreRepository.saveUserInfo(
                                nickname = currentState.newNickname,
                                email = userDataStoreRepository.emailFlow.first() ?: "",
                                accessToken = userDataStoreRepository.accessTokenFlow.first() ?: "",
                                refreshToken = userDataStoreRepository.refreshTokenFlow.first()
                                    ?: ""
                            )

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
                        _changeNicknameState.update { it.copy(isChangingNickname = false) }
                        val message = result.exception.message ?: UNKNOWN_ERROR
                        _changeNicknameEvent.emit(
                            ChangeNicknameScreenEvent.Error(message)
                        )
                    }

                    is DataResourceResult.DummyConstructor -> Unit
                }
            }
        }
    }

    // --------------------------------------------------
    // 비밀번호 변경 관련
    // --------------------------------------------------

    fun onChangePasswordClick() {
        val currentState = _changePasswordState.value
        viewModelScope.launch {

            val request = ChangePasswordRequest(
                email = currentState.email,
                password = currentState.currentPassword,
                newPassword = currentState.newPassword
            )

            myPageRepository.changePassword(request).collectLatest { result ->
                when (result) {
                    is DataResourceResult.Loading -> {
                        _changePasswordState.update { it.copy(isChangingPassword = true) }
                    }

                    is DataResourceResult.Success -> {
                        _changePasswordState.update { it.copy(isChangingPassword = false) }
                        val message = result.data.message
                        _changePasswordEvent.emit(ChangePasswordScreenEvent.NavigateToMyPage(message))
                    }

                    is DataResourceResult.Failure -> {
                        _changePasswordState.update { it.copy(isChangingPassword = false) }
                        val message = result.exception.message ?: UNKNOWN_ERROR
                        _changePasswordEvent.emit(ChangePasswordScreenEvent.Error(message))
                    }

                    is DataResourceResult.DummyConstructor -> Unit
                }
            }
        }
    }

    fun onCurrentPasswordChange(password: String) {
        _changePasswordState.update { it.copy(currentPassword = password) }
    }

    fun onNewPasswordChange(password: String) {
        _changePasswordState.update { it.copy(newPassword = password) }
    }

    fun onNewPasswordChangeDebounced(password: String) {
        _changePasswordState.update {
            it.copy(
                passwordValidationState = userDataValidator.isPasswordValid(password)
            )
        }
    }

    fun onConfirmPasswordChange(password: String) {
        _changePasswordState.update { it.copy(confirmPassword = password) }
    }

    fun onToggleCurrentPasswordVisibility() {
        _changePasswordState.update {
            it.copy(isCurrentPasswordVisible = !it.isCurrentPasswordVisible)
        }
    }

    fun onToggleNewPasswordVisibility() {
        _changePasswordState.update {
            it.copy(isNewPasswordVisible = !it.isNewPasswordVisible)
        }
    }

    fun onToggleConfirmPasswordVisibility() {
        _changePasswordState.update {
            it.copy(isConfirmPasswordVisible = !it.isConfirmPasswordVisible)
        }
    }

    // --------------------------------------------------
    // 마이페이지 이동 관련
    // --------------------------------------------------

    fun onLoginClick() {
        viewModelScope.launch {
            _myPageScreenEvent.emit(MyPageScreenEvent.NavigateToLogin)
        }
    }

    fun onLogoutClick() {
        viewModelScope.launch {
            userDataStoreRepository.clearLoginInfo()
        }
    }

    fun navigateToChangePassword() {
        viewModelScope.launch {
            _myPageScreenEvent.emit(MyPageScreenEvent.NavigateToChangePassword)
        }
    }

    fun navigateToChangeNickname() {
        viewModelScope.launch {
            _myPageScreenEvent.emit(MyPageScreenEvent.NavigateToChangeNickname)
        }
    }

    fun navigateToReviewHistory() {
        viewModelScope.launch {
            _myPageScreenEvent.emit(MyPageScreenEvent.NavigateToReviewHistory)
        }
    }
}
