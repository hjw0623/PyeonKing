package com.hjw0623.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReviewSummaryResponseDto(
    @field: Json(name = "totalCount") val totalCount: Int,
    @field: Json(name = "averageRating") val averageRating: Double,
    @field: Json(name = "ratingDistribution") val ratingDistribution: Map<Int, Long>
)
