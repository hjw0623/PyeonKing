package com.hjw0623.core.business_logic.model.mypage

private const val EMPTY = ""

data class User(
    val email: String = EMPTY,
    val nickname: String = EMPTY,
    val password: String = EMPTY,
    val accessToken: String = EMPTY,
    val refreshToken: String = EMPTY
)