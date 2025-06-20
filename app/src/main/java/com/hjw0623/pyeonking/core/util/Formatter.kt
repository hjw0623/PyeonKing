package com.hjw0623.pyeonking.core.util

import androidx.compose.ui.graphics.Color
import com.hjw0623.pyeonking.ui.theme.Cu
import com.hjw0623.pyeonking.ui.theme.Emart24
import com.hjw0623.pyeonking.ui.theme.Gs25
import com.hjw0623.pyeonking.ui.theme.Seven
import com.hjw0623.pyeonking.ui.theme.errorDark
import com.hjw0623.pyeonking.ui.theme.errorLight

fun getBrandColor(
    brand: String,
    isDarkTheme: Boolean
): Color {
    return when (brand) {
        "CU" -> Cu
        "GS25" -> Gs25
        "SEVENELEVEN" -> Seven
        "EMART24" -> Emart24
        else -> if (isDarkTheme) errorDark else errorLight
    }
}