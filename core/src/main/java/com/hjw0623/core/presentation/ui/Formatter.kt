package com.hjw0623.core.presentation.ui

import androidx.compose.ui.graphics.Color
import com.hjw0623.core.constants.Api.IMAGE_URL
import com.hjw0623.core.constants.Brand
import com.hjw0623.core.constants.Promotion
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

fun changePromotionName(promotion: String): String {
    return when (promotion) {
        Promotion.ONE_PLUS_ONE_RESPONSE -> Promotion.ONE_PLUS_ONE
        Promotion.TWO_PLUS_ONE_RESPONSE -> Promotion.TWO_PLUS_ONE
        else -> promotion
    }
}

fun getFullImageUrl(imgUrl: String?): String {
    return if (imgUrl.isNullOrBlank()) {
        ""
    } else if (imgUrl.startsWith("http")) {
        imgUrl
    } else {
        IMAGE_URL + imgUrl
    }
}