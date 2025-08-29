package com.hjw0623.core.network.request

data class ReviewPostBody(
    val promotionId: Long,
    val star: Int,
    val content: String
)