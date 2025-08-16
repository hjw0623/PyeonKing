package com.hjw0623.core.business_logic.auth.validator

import android.util.Patterns
import com.hjw0623.core.business_logic.auth.validator.PatternValidator

object EmailPatternValidator: PatternValidator {
    override fun matches(value: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(value.trim()).matches()
    }
}