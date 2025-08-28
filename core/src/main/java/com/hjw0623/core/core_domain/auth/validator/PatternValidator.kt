package com.hjw0623.core.core_domain.auth.validator

interface PatternValidator {
    fun matches(value: String): Boolean
}