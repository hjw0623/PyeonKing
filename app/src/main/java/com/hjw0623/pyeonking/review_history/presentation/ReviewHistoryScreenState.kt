package com.hjw0623.pyeonking.review_history.presentation

import com.hjw0623.pyeonking.review_history.data.ReviewInfo
import com.hjw0623.pyeonking.review_history.data.mockReviewHistoryList

data class ReviewHistoryScreenState(
    val isLoading: Boolean = false,
    val reviewHistoryList: List<ReviewInfo> = mockReviewHistoryList
)
