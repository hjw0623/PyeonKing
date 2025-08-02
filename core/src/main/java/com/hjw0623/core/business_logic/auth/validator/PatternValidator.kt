package com.hjw0623.core.business_logic.auth.validator

interface PatternValidator {
    fun matches(value: String): Boolean
}