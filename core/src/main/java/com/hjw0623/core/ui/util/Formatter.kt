package com.hjw0623.core.ui.util

import androidx.compose.ui.graphics.Color
import com.hjw0623.core.constants.api.ApiUrl.IMAGE_URL
import com.hjw0623.core.constants.brand.Brand
import com.hjw0623.core.constants.promotion.PromotionConstants
import com.hjw0623.core.ui.designsystem.theme.Cu
import com.hjw0623.core.ui.designsystem.theme.Emart24
import com.hjw0623.core.ui.designsystem.theme.Gs25
import com.hjw0623.core.ui.designsystem.theme.Seven
import com.hjw0623.core.ui.designsystem.theme.errorDark
import com.hjw0623.core.ui.designsystem.theme.errorLight

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
        PromotionConstants.ONE_PLUS_ONE_RESPONSE -> PromotionConstants.ONE_PLUS_ONE
        PromotionConstants.TWO_PLUS_ONE_RESPONSE -> PromotionConstants.TWO_PLUS_ONE
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