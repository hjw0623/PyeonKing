package com.hjw0623.core.business_logic.model.request

data class ChangePasswordRequest(
    val email: String,
    val password: String,
    val newPassword: String
)