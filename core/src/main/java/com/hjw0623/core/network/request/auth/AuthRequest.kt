package com.hjw0623.core.network.request.auth

data class AuthRequest(
    val email: String,
    val password: String,
    val nickname: String? = null
)