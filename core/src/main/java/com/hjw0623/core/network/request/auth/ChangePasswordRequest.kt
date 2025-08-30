package com.hjw0623.core.network.request.auth

data class ChangePasswordRequest(
    val email: String,
    val password: String,
    val newPassword: String
)