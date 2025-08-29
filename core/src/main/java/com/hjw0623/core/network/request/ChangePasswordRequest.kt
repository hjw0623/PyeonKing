package com.hjw0623.core.network.request

data class ChangePasswordRequest(
    val email: String,
    val password: String,
    val newPassword: String
)