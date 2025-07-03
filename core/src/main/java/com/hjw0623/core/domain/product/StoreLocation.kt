package com.hjw0623.core.domain.product

data class StoreLocation(
    val name: String,
    val brand: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val distance: Double
)