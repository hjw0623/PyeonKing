package com.hjw0623.presentation.screen.review.review_history

import com.hjw0623.core.domain.review.review_history.ReviewInfo
import com.hjw0623.core.mockdata.mockReviewHistoryList

data class ReviewHistoryScreenState(
    val isLoading: Boolean = false,
    val reviewHistoryList: List<ReviewInfo> = mockReviewHistoryList
)
