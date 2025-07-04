package com.hjw0623.core.domain.auth

import android.util.Patterns

class EmailPatternValidator: PatternValidator {
    override fun matches(value: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(value.trim()).matches()
    }
}