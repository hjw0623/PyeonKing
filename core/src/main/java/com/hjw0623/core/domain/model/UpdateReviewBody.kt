package com.hjw0623.core.domain.model

data class UpdateReviewBody(
    val commentId: Long,
    val star: Int,
    val content: String
)