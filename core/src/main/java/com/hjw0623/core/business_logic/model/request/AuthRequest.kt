package com.hjw0623.core.business_logic.model.request

data class AuthRequest(
    val email: String,
    val password: String,
    val nickname: String? = null
)