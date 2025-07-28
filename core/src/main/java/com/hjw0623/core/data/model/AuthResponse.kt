package com.hjw0623.core.data.model

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String,
    val nickname: String,
)
