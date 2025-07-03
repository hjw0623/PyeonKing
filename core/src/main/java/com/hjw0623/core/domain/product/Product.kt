package com.hjw0623.core.domain.product

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Product(
    val uuid: String,
    val imgUrl: String,
    val name: String,
    val price: String,
    val priceForEach: String,
    val promotion: String,
    val brand: String,
) : Parcelable