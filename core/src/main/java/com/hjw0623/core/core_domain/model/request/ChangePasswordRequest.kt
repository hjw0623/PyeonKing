package com.hjw0623.core.core_domain.model.request

data class ChangePasswordRequest(
    val email: String,
    val password: String,
    val newPassword: String
)