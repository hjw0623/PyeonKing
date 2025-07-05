package com.hjw0623.presentation.screen.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjw0623.core.domain.auth.NicknameValidationState
import com.hjw0623.core.mockdata.mockTakenNicknames
import com.hjw0623.core.mockdata.mockUser
import com.hjw0623.presentation.screen.mypage.change_nickname.ui.ChangeNicknameScreenEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MyPageViewModel(
    //Todo: private val myPageRepository: MyPageRepository
) : ViewModel() {

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

    private val _event = MutableSharedFlow<ChangeNicknameScreenEvent>()
    val event = _event.asSharedFlow()

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
                _event.emit(ChangeNicknameScreenEvent.Error("확인 중 오류가 발생했습니다."))
            }
        }
    }

    fun onChangeNicknameClick() {
        viewModelScope.launch {
            _isChangingNickname.value = true
            try {
                delay(1500)
                // TODO: 실제 닉네임 변경 API 호출
                _event.emit(ChangeNicknameScreenEvent.NavigateToMyPage)
            } catch (e: Exception) {
                _event.emit(ChangeNicknameScreenEvent.Error("닉네임 변경에 실패했습니다."))
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
}