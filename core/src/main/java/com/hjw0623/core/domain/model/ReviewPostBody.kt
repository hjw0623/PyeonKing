package com.hjw0623.core.domain.model

data class ReviewPostBody(
    val promotionId: Long,
    val star: Int,
    val content: String
)