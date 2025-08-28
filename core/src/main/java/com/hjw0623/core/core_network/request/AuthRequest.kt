package com.hjw0623.core.core_network.request

data class AuthRequest(
    val email: String,
    val password: String,
    val nickname: String? = null
)