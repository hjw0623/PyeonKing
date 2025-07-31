package com.hjw0623.core.domain.model

data class PoiInfo(
    val id: String,
    val placeName: String,
    val categoryName: String,
    val phone: String,
    val addressName: String,
    val roadAddressName: String,
    val longitude: Double,
    val latitude: Double
)
