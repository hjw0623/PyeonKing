package com.hjw0623.core.core_domain.model.request

data class AuthRequest(
    val email: String,
    val password: String,
    val nickname: String? = null
)