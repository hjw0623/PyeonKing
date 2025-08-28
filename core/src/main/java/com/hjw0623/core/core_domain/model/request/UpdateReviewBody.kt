package com.hjw0623.core.core_domain.model.request

data class UpdateReviewBody(
    val commentId: Long,
    val star: Int,
    val content: String
)