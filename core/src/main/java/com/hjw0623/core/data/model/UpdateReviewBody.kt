package com.hjw0623.core.data.model

data class UpdateReviewBody(
    val commentId: Long,
    val star: Int,
    val content: String
)