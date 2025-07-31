package com.hjw0623.presentation.util

import android.content.Context
import android.graphics.BitmapFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.hjw0623.presentation.R
import androidx.core.graphics.scale
import com.hjw0623.core.constants.Brand

fun getBrandLogoResId(brand: String): Int? {
    return when (brand.uppercase()) {
        Brand.SEVEN -> R.drawable.seveneleven_logo
        Brand.GS25 -> R.drawable.gs25_logo
        Brand.EMART24-> R.drawable.emart24_logo
        Brand.CU -> R.drawable.cu_logo
        else -> null
    }
}

fun getMarkerIconByBrand(context: Context, brandName: String): BitmapDescriptor {
    val resId = when (brandName.uppercase()) {
        Brand.SEVEN -> R.drawable.marker_7eleven
        Brand.GS25 -> R.drawable.marker_gs25
        Brand.EMART24 -> R.drawable.marker_emart24
        Brand.CU -> R.drawable.marker_cu
        else -> R.drawable.marker_default
    }

    val originalBitmap = BitmapFactory.decodeResource(context.resources, resId)

    val resizedBitmap = originalBitmap.scale(120, 120, false)

    return BitmapDescriptorFactory.fromBitmap(resizedBitmap)
}