package com.hjw0623.core.business_logic.model.request

data class ReviewPostBody(
    val promotionId: Long,
    val star: Int,
    val content: String
)