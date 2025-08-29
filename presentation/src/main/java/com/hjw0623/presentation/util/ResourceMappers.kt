package com.hjw0623.presentation.util

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.hjw0623.core.android.constants.Brand
import com.hjw0623.presentation.R
import kotlin.math.roundToInt

fun getBrandLogoResId(brand: String): Int? {
    return when (brand.uppercase()) {
        Brand.SEVEN -> R.drawable.seveneleven_logo
        Brand.GS25 -> R.drawable.gs25_logo
        Brand.EMART24 -> R.drawable.emart24_logo
        Brand.CU -> R.drawable.cu_logo
        else -> null
    }
}

private fun Context.dpToPx(dp: Int) = (dp * resources.displayMetrics.density).roundToInt()

@DrawableRes
private fun getResByBrand(brandName: String): Int = when (brandName.uppercase()) {
    Brand.SEVEN -> R.drawable.marker_7eleven
    Brand.GS25 -> R.drawable.marker_gs25
    Brand.EMART24 -> R.drawable.marker_emart24
    Brand.CU -> R.drawable.marker_cu
    else -> R.drawable.marker_default
}

fun getMarkerIconByBrand(context: Context, brandName: String): BitmapDescriptor =
    runCatching {
        val resId = getResByBrand(brandName)
        val size = context.dpToPx(60)
        val dr = AppCompatResources.getDrawable(context, resId)
            ?: return BitmapDescriptorFactory.defaultMarker()
        val bmp = dr.toBitmap(width = size, height = size)
        BitmapDescriptorFactory.fromBitmap(bmp)
    }.getOrElse {
        BitmapDescriptorFactory.defaultMarker()
    }