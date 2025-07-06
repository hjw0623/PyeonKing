package com.hjw0623.core.domain

import com.hjw0623.core.domain.mypage.User
import com.hjw0623.core.mockdata.mockUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object AuthManager {

    private val _isLoggedIn = MutableStateFlow(true)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    private val _userData = MutableStateFlow<User?>(mockUser)
    val userData = _userData.asStateFlow()

    fun login(user: User) {
        _userData.value = user
        _isLoggedIn.value = true
    }

    fun logout() {
        _userData.value = null
        _isLoggedIn.value = false
    }

    fun updateUserData(user: User) {
        _userData.value = user
    }
}