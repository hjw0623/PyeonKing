package com.hjw0623.core.core_domain.model.response

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String,
    val nickname: String,
)