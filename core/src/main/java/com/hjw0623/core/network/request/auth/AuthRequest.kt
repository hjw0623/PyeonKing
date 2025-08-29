package com.hjw0623.core.network.request

data class AuthRequest(
    val email: String,
    val password: String,
    val nickname: String? = null
)