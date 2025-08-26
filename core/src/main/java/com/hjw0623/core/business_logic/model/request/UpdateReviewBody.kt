package com.hjw0623.core.business_logic.model.request

data class UpdateReviewBody(
    val commentId: Long,
    val star: Int,
    val content: String
)