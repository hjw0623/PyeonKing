package com.hjw0623.core.network.request.review

data class ReviewPostBody(
    val promotionId: Long,
    val star: Int,
    val content: String
)