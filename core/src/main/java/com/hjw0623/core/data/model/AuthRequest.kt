package com.hjw0623.core.data.model

data class AuthRequest(
    val email: String,
    val password: String,
    val nickname: String? = null
)
