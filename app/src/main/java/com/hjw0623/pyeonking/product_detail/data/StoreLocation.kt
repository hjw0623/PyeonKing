package com.hjw0623.pyeonking.product_detail.data

data class StoreLocation(
    val name: String,
    val brand: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val distance: Double
)