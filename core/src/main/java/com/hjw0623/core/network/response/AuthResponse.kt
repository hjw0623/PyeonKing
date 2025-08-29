package com.hjw0623.core.network.response

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String,
    val nickname: String,
)