package com.hjw0623.core.data.model

data class Item(
     val id: Long,
    val name: String,
    val imgUrl: String,
    val brand: String,
    val promotion: String,
    val pricePerUnit: Int,
    val pricePerGroup: Int
)
