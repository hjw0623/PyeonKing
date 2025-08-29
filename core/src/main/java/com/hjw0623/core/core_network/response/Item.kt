package com.hjw0623.core.core_network.response

import com.hjw0623.core.domain.model.product.Product
import com.hjw0623.core.ui.util.changePromotionName
import com.hjw0623.core.ui.util.getFullImageUrl

data class Item(
    val id: Long,
    val name: String,
    val imgUrl: String?,
    val brand: String,
    val promotion: String,
    val pricePerUnit: Int,
    val pricePerGroup: Int,
    val discountedUnitPrice: Int
)

fun Item.toProduct(): Product {
    val fullImgUrl = this.imgUrl?.let { getFullImageUrl(it) }
    val promotion = changePromotionName(this.promotion)

    return Product(
        id = this.id.toString(),
        imgUrl = fullImgUrl,
        name = this.name,
        price = this.pricePerUnit.toString(),
        priceForEach = this.discountedUnitPrice.toString(),
        promotion = promotion,
        brand = this.brand
    )
}
