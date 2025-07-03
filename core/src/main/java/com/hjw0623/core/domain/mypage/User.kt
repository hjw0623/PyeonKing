package com.hjw0623.core.domain.mypage

data class User(
    val email: String = "",
    val nickname: String = "",
    val password: String = "",
    val accessToken: String = "",
    val refreshToken: String = "",
)