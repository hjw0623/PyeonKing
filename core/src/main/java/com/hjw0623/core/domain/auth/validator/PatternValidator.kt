package com.hjw0623.core.domain.auth.validator

interface PatternValidator {
    fun matches(value: String): Boolean
}