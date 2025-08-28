package com.hjw0623.core.core_network.request

data class ReviewPostBody(
    val promotionId: Long,
    val star: Int,
    val content: String
)