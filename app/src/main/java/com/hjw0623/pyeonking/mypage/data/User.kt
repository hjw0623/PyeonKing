package com.hjw0623.pyeonking.mypage.data

data class User(
    val email: String = "test1234@test.com",
    val nickname: String = "testNickName",
    val password: String = "test1234",
    val accessToken: String = "accessToken",
    val refreshToken: String = "refreshToken",
)