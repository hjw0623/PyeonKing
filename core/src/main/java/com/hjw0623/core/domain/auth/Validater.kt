package com.hjw0623.core.domain.auth

import android.util.Patterns
import java.util.regex.Pattern

fun isEmailValid(email: String): Boolean {
    val emailRegex = Patterns.EMAIL_ADDRESS.toString()
    return email.isNotBlank() && Pattern.matches(emailRegex, email)
}

fun isPasswordValid(password: String): Boolean {
    val passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{6,20}$"
    return password.isNotBlank() && Pattern.matches(passwordRegex, password)
}

fun isConfirmPasswordValid(password: String, confirm: String): Boolean {
    return confirm.isNotBlank() && password == confirm
}