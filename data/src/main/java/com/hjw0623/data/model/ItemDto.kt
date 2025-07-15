package com.hjw0623.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ItemDto(
    @field:Json(name = "id") val id: Long,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "imgUrl") val imgUrl: String,
    @field:Json(name = "brand") val brand: String,
    @field:Json(name = "promotion") val promotion: String,
    @field:Json(name = "pricePerUnit") val pricePerUnit: Int,
    @field:Json(name = "pricePerGroup") val pricePerGroup: Int
)
