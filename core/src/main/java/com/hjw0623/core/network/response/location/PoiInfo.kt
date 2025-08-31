package com.hjw0623.core.network.response.location

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