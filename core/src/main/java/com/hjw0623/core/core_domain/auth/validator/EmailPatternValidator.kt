package com.hjw0623.core.core_domain.auth.validator

import android.util.Patterns

object EmailPatternValidator: PatternValidator {
    override fun matches(value: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(value.trim()).matches()
    }
}