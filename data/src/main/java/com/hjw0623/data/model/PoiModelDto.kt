package com.hjw0623.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PoiResponse(
    @Json(name = "documents")
    val documents: List<Document>
)

@JsonClass(generateAdapter = true)
data class Document(
    @Json(name = "id")
    val id: String?,
    @Json(name = "place_name")
    val placeName: String?,
    @Json(name = "category_name")
    val categoryName: String?,
    @Json(name = "phone")
    val phone: String?,
    @Json(name = "address_name")
    val addressName: String?,
    @Json(name = "road_address_name")
    val roadAddressName: String?,
    @Json(name = "x")
    val x: String?,
    @Json(name = "y")
    val y: String?
)
