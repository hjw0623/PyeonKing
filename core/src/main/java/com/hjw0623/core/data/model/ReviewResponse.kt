package com.hjw0623.core.data.model

data class ReviewPage(
    val content: List<ReviewResponse>,
    val totalElements: Int,
    val totalPages: Int,
    val last: Boolean,
    val number: Int,
    val size: Int
)

data class ReviewResponse(
    val commentId: Long,
    val promotionId: Long,
    val star: Int,
    val content: String,
    val userName: String,
    val createdAt: String,
    val product: ReviewProduct
)

data class ReviewProduct(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val brand: String
)