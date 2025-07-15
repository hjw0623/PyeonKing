package com.hjw0623.core.data.model

data class ReviewPostResponse(
    val commentId: Long,
    val promotionId: Long,
    val star: Int,
    val content: String,
    val userName: String,
    val createdAt: String,
    val product: ProductDto
)