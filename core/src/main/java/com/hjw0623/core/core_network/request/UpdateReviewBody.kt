package com.hjw0623.core.core_network.request

data class UpdateReviewBody(
    val commentId: Long,
    val star: Int,
    val content: String
)