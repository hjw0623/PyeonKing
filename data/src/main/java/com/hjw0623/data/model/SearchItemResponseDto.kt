package com.hjw0623.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchItemResponseDto(
    @field:Json(name = "searchItems") val searchItems: List<ItemDto>
)
