package com.hjw0623.core.business_logic.auth

import android.util.Log
import com.hjw0623.core.business_logic.model.mypage.User
import com.hjw0623.core.data.UserPreferenceDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

object AuthManager {

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _userData = MutableStateFlow<User?>(null)
    val userData: StateFlow<User?> = _userData.asStateFlow()

    private var _accessToken: String = ""
    val accessToken: String get() = _accessToken

    private lateinit var userPrefs: UserPreferenceDataStore
    private val applicationScope = CoroutineScope(Dispatchers.IO)

    fun initialize(userPreferenceDataStore: UserPreferenceDataStore) {
        userPrefs = userPreferenceDataStore

        applicationScope.launch {
            userPrefs.isLoggedInFlow.collect {
                _isLoggedIn.emit(it)
            }
        }
        applicationScope.launch {
            userPrefs.accessTokenFlow.collect {
                _accessToken = it.orEmpty()
            }
        }

        applicationScope.launch {
            combine(
                userPrefs.nicknameFlow,
                userPrefs.emailFlow
            ) { nickname, email ->
                if (!nickname.isNullOrBlank() && !email.isNullOrBlank()) {
                    User(nickname = nickname, email = email)
                } else null
            }.collect {
                _userData.emit(it)
            }
        }
    }

    fun login(user: User) {
        applicationScope.launch {
            userPrefs.saveUserInfo(
                nickname = user.nickname,
                email = user.email,
                password = user.password,
                accessToken = user.accessToken,
                refreshToken = user.refreshToken
            )
            _userData.emit(user)
            Log.d("🔐AuthManager", "✅ login(): accessToken = ${user.accessToken}")

        }
    }

    fun logout() {
        applicationScope.launch {
            userPrefs.clearLoginInfo()
        }
    }

    fun updateUserData(newUser: User) {
        applicationScope.launch {
            userPrefs.saveUserInfo(
                nickname = newUser.nickname,
                email = newUser.email,
                password = userPrefs.passwordFlow.first() ?: "",
                accessToken = userPrefs.accessTokenFlow.first() ?: "",
                refreshToken = userPrefs.refreshTokenFlow.first() ?: ""
            )
        }
    }
}