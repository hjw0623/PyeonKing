package com.hjw0623.core.domain.auth

import java.util.regex.Pattern

fun isEmailValid(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9](.*)([@]{1})(.{1,})(\\.)(.{1,})"
    return email.isNotBlank() && Pattern.matches(emailRegex, email)
}

fun isPasswordValid(password: String): Boolean {
    val passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{6,20}$"
    return password.isNotBlank() && Pattern.matches(passwordRegex, password)
}

fun isConfirmPasswordValid(password: String, confirm: String): Boolean {
    return confirm.isNotBlank() && password == confirm
}