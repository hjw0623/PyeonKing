package com.hjw0623.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReviewPostResponseDto(
    @field:Json(name = "commentId") val commentId: Long,
    @field:Json(name = "promotionId") val promotionId: Long,
    @field:Json(name = "star") val star: Int,
    @field:Json(name = "content") val content: String,
    @field:Json(name = "userName") val userName: String,
    @field:Json(name = "createdAt") val createdAt: String,
    @field:Json(name = "product") val product: ProductDto
)