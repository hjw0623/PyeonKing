package com.hjw0623.pyeonking.mypage.presentation

import com.hjw0623.pyeonking.mypage.data.User

data class MyPageScreenState(
    val isLoggedIn: Boolean = true,
    val isLoading: Boolean = true,
    val userData: User? = User()
)
