package com.hjw0623.core.data.model

data class ReviewResponse(
    val commentId: Long,
    val promotionId: Long,
    val star: Int,
    val content: String,
    val userName: String,
    val createdAt: String,
    val product: ProductDto
)

data class ProductDto(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val brand: String
)
