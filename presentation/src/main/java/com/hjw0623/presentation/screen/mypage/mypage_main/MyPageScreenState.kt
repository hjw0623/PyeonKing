package com.hjw0623.presentation.screen.mypage.mypage_main

import com.hjw0623.core.domain.mypage.User
import com.hjw0623.core.mockdata.mockUser

data class MyPageScreenState(
    val isLoggedIn: Boolean = false,
    val isLoading: Boolean = false,
    val userData: User? = mockUser
)
