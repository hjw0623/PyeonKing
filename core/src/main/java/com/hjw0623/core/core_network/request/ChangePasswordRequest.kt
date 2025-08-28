package com.hjw0623.core.core_network.request

data class ChangePasswordRequest(
    val email: String,
    val password: String,
    val newPassword: String
)