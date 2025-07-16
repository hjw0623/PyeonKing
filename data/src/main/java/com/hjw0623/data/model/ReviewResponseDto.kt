package com.hjw0623.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReviewResponseDto(
    @field:Json(name = "commentId") val commentId: Long,
    @field:Json(name = "promotionId") val promotionId: Long,
    @field:Json(name = "star") val star: Int,
    @field:Json(name = "content") val content: String,
    @field:Json(name = "userName") val userName: String,
    @field:Json(name = "createdAt") val createdAt: String,
    @field:Json(name = "product") val product: ProductDto
)

@JsonClass(generateAdapter = true)
data class ProductDto(
    @field:Json(name = "id") val id: Long,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "imageUrl") val imageUrl: String,
    @field:Json(name = "brand") val brand: String
)

@JsonClass(generateAdapter = true)
data class ReviewPageDto(
    @field:Json(name = "content") val content: List<ReviewResponseDto>,
    @field:Json(name = "totalElements") val totalElements: Int,
    @field:Json(name = "totalPages") val totalPages: Int,
    @field:Json(name = "last") val last: Boolean,
    @field:Json(name = "number") val number: Int,
    @field:Json(name = "size") val size: Int
)