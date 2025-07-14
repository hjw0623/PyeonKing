package com.hjw0623.core.presentation.ui

import androidx.compose.ui.graphics.Color
import com.hjw0623.core.util.constants.Brand
import com.hjw0623.core.presentation.designsystem.theme.Cu
import com.hjw0623.core.presentation.designsystem.theme.Emart24
import com.hjw0623.core.presentation.designsystem.theme.Gs25
import com.hjw0623.core.presentation.designsystem.theme.Seven
import com.hjw0623.core.presentation.designsystem.theme.errorDark
import com.hjw0623.core.presentation.designsystem.theme.errorLight

fun getBrandColor(
    brand: String,
    isDarkTheme: Boolean
): Color {
    return when (brand) {
        Brand.CU -> Cu
        Brand.GS25 -> Gs25
        Brand.SEVEN -> Seven
        Brand.EMART24 -> Emart24
        else -> if (isDarkTheme) errorDark else errorLight
    }
}