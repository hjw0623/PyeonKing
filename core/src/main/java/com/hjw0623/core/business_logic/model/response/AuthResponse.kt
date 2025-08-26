package com.hjw0623.core.business_logic.model.response

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String,
    val nickname: String,
)