package com.hjw0623.presentation.util

import com.hjw0623.presentation.R

fun getBrandLogoResId(brand: String): Int? {
    return when (brand.uppercase()) {
        "SEVENELEVEN" -> R.drawable.seveneleven_logo
        "GS25" -> R.drawable.gs25_logo
        "EMART24" -> R.drawable.emart24_logo
        "CU" -> R.drawable.cu_logo
        else -> null
    }
}
