package com.hjw0623.core.core_domain.model.request

data class ReviewPostBody(
    val promotionId: Long,
    val star: Int,
    val content: String
)