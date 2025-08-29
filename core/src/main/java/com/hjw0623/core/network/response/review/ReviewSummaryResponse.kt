package com.hjw0623.core.network.response

data class ReviewSummaryResponse(
    val totalCount: Int,
    val averageRating: Double,
    val ratingDistribution: Map<Int, Long>
)