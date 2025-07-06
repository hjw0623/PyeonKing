package com.hjw0623.presentation.screen.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjw0623.core.domain.auth.NicknameValidationState
import com.hjw0623.core.domain.auth.PasswordValidationState
import com.hjw0623.core.domain.auth.UserDataValidator
import com.hjw0623.core.mockdata.mockTakenNicknames
import com.hjw0623.core.mockdata.mockUser
import com.hjw0623.presentation.screen.mypage.change_nickname.ui.ChangeNicknameScreenEvent
import com.hjw0623.presentation.screen.mypage.change_password.ui.ChangePasswordScreenEvent
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

class MyPageViewModel(
    //Todo: private val myPageRepository: MyPageRepository
    private val userDataValidator: UserDataValidator
) : ViewModel() {
    /*
    닉네임 변경 관련 상태
     */
    private val _currentNickname = MutableStateFlow(mockUser.nickname)

    private val _newNickname = MutableStateFlow(mockUser.nickname)
    val newNickname = _newNickname.asStateFlow()

    private val _nicknameValidationState =
        MutableStateFlow<NicknameValidationState>(NicknameValidationState.Idle)
    val nicknameValidationState = _nicknameValidationState.asStateFlow()

    private val _isChangingNickname = MutableStateFlow(false)

    val isChangeButtonEnabled = combine(
        _currentNickname,
        newNickname,
        nicknameValidationState,
        _isChangingNickname
    ) { current, new, validationState, isChanging ->
        validationState is NicknameValidationState.Valid &&
                current != new &&
                !isChanging
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = false
    )

    private val _changeNicknameEvent = MutableSharedFlow<ChangeNicknameScreenEvent>()
    val changeNicknameEvent = _changeNicknameEvent.asSharedFlow()


    fun onNicknameCheckClick() {
        val newNicknameValue = newNickname.value
        if (newNicknameValue == _currentNickname.value) {
            _nicknameValidationState.value =
                NicknameValidationState.Invalid("현재 닉네임과 동일합니다.")
            return
        }

        viewModelScope.launch {
            _nicknameValidationState.value = NicknameValidationState.Checking
            try {
                delay(1000)
                if (mockTakenNicknames.contains(newNicknameValue)) {
                    _nicknameValidationState.value =
                        NicknameValidationState.Invalid("이미 사용 중인 닉네임입니다.")
                } else {
                    _nicknameValidationState.value = NicknameValidationState.Valid
                }
            } catch (e: Exception) {
                _nicknameValidationState.value = NicknameValidationState.Idle
                _changeNicknameEvent.emit(ChangeNicknameScreenEvent.Error("확인 중 오류가 발생했습니다."))
            }
        }
    }

    fun onChangeNicknameClick() {
        viewModelScope.launch {
            _isChangingNickname.value = true
            try {
                delay(1500)
                // TODO: 실제 닉네임 변경 API 호출
                _changeNicknameEvent.emit(ChangeNicknameScreenEvent.NavigateToMyPage)
            } catch (e: Exception) {
                _changeNicknameEvent.emit(ChangeNicknameScreenEvent.Error("닉네임 변경에 실패했습니다."))
            } finally {
                _isChangingNickname.value = false
            }
        }
    }

    fun onNicknameChange(nickname: String) {
        _newNickname.value = nickname
        if (_nicknameValidationState.value !is NicknameValidationState.Idle) {
            _nicknameValidationState.value = NicknameValidationState.Idle
        }
    }


    /*
    ============================================================
    비밀번호 변경 관련 상태 및 로직
    ============================================================
     */
    private val _currentPassword = MutableStateFlow("")
    val currentPassword = _currentPassword.asStateFlow()

    private val _newPassword = MutableStateFlow("")
    val newPassword = _newPassword.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword = _confirmPassword.asStateFlow()

    private val _isCurrentPasswordValid = MutableStateFlow(false)
    val isCurrentPasswordValid = _isCurrentPasswordValid.asStateFlow()

    val isConfirmPasswordValid = combine(newPassword, confirmPassword) { newPw, confirmPw ->
        newPw.isNotEmpty() && newPw == confirmPw
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = false
    )

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

    val isChangePwButtonEnabled = combine(
        isCurrentPasswordValid,
        passwordValidationState,
        isConfirmPasswordValid,
        isChangingPassword
    ) { isCurrentPwValid, newPwValidation, isConfirmPwValid, isChanging ->
        isCurrentPwValid &&
                newPwValidation.isValidPassword && // `PasswordValidationState`의 모든 규칙이 참인지 확인
                isConfirmPwValid &&
                !isChanging
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = false
    )

    private val _changePasswordEvent = MutableSharedFlow<ChangePasswordScreenEvent>()
    val changePasswordEvent = _changePasswordEvent.asSharedFlow()


    /**
     * 최종 '비밀번호 변경' 버튼 클릭 로직
     */
    fun onChangePasswordClick() {
        viewModelScope.launch {
            _isChangingPassword.value = true
            try {
                delay(1500)
                // TODO: 실제 비밀번호 변경 API 호출
                _changePasswordEvent.emit(ChangePasswordScreenEvent.NavigateToMyPage)
            } catch (e: Exception) {
                _changePasswordEvent.emit(ChangePasswordScreenEvent.Error("비밀번호 변경에 실패했습니다."))
            } finally {
                _isChangingPassword.value = false
            }
        }
    }

    /**
     * 현재 비밀번호 입력값 변경
     */
    fun onCurrentPasswordChange(password: String) {
        _currentPassword.value = password
    }

    /**
     * 현재 비밀번호 유효성 검사 (입력 멈췄을 때)
     */
    fun onCurrentPasswordChangeDebounced(debouncedPassword: String) {
        _isCurrentPasswordValid.value = (debouncedPassword == mockUser.password)
    }

    /**
     * 새 비밀번호 입력값 변경
     */
    fun onNewPasswordChange(password: String) {
        _newPassword.value = password
    }

    /**
     * 새 비밀번호 유효성 검사 (입력 멈췄을 때)
     */
    fun onNewPasswordChangeDebounced(debouncedPassword: String) {
        _passwordValidationState.value = userDataValidator.isPasswordValid(debouncedPassword)
    }

    /**
     * 확인 비밀번호 입력값 변경
     */
    fun onConfirmPasswordChange(password: String) {
        _confirmPassword.value = password
    }

    /**
     * 각 비밀번호 필드의 보이기/숨기기 상태 토글
     */
    fun onToggleCurrentPasswordVisibility() {
        _isCurrentPasswordVisible.update { !it }
    }

    fun onToggleNewPasswordVisibility() {
        _isNewPasswordVisible.update { !it }
    }

    fun onToggleConfirmPasswordVisibility() {
        _isConfirmPasswordVisible.update { !it }
    }
}