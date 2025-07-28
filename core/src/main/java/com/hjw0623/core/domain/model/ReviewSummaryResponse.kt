package com.hjw0623.core.domain.model

data class ReviewSummaryResponse(
    val totalCount: Int,
    val averageRating: Double,
    val ratingDistribution: Map<Int, Long>
)