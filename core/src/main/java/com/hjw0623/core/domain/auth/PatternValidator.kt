package com.hjw0623.core.domain.auth

interface PatternValidator {
    fun matches(value: String): Boolean
}