package com.hjw0623.core.core_ui.ui

import androidx.compose.ui.graphics.Color
import com.hjw0623.core.core_andriod.constants.Api.IMAGE_URL
import com.hjw0623.core.core_andriod.constants.Brand
import com.hjw0623.core.core_andriod.constants.Promotion
import com.hjw0623.core.core_ui.designsystem.theme.Cu
import com.hjw0623.core.core_ui.designsystem.theme.Emart24
import com.hjw0623.core.core_ui.designsystem.theme.Gs25
import com.hjw0623.core.core_ui.designsystem.theme.Seven
import com.hjw0623.core.core_ui.designsystem.theme.errorDark
import com.hjw0623.core.core_ui.designsystem.theme.errorLight

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

fun getBrandQuery(brand: String): String {
    return when (brand) {
        Brand.CU -> Brand.CU_KR
        Brand.GS25 -> Brand.GS25_KR
        Brand.SEVEN -> Brand.SEVEN_KR
        Brand.EMART24 -> Brand.EMART24_KR
        else -> brand
    }
}