package com.hjw0623.pyeonking.mypage.presentation

import com.hjw0623.pyeonking.mypage.data.User

data class MyPageScreenState(
    val isLoggedIn: Boolean = false,
    val isLoading: Boolean = false,
    val userData: User? = User()
)
