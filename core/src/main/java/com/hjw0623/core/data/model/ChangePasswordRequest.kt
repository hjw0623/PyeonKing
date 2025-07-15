package com.hjw0623.core.data.model

data class ChangePasswordRequest(
    val email: String,
    val password: String,
    val newPassword: String
)
