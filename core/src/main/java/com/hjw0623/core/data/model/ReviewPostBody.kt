package com.hjw0623.core.data.model

data class ReviewPostBody(
    val promotionId: Long,
    val star: Int,
    val content: String
)