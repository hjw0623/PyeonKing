package com.hjw0623.core.android.auth.validator

import android.util.Patterns
import com.hjw0623.core.domain.auth.validator.PatternValidator

object EmailPatternValidator: PatternValidator {
    override fun matches(value: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(value.trim()).matches()
    }
}