package com.hjw0623.core.network.request

data class UpdateReviewBody(
    val commentId: Long,
    val star: Int,
    val content: String
)