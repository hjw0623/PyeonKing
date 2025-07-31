package com.hjw0623.core.domain.model

data class ChangePasswordRequest(
    val email: String,
    val password: String,
    val newPassword: String
)
