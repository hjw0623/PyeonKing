package com.hjw0623.presentation.screen.review.review_history.ui

import com.hjw0623.core.core_domain.model.review.ReviewInfo

data class ReviewHistoryScreenState(
    val isLoading: Boolean = false,
    val reviews: List<ReviewInfo> = emptyList(),
    val currentPage: Int = 1,
    val lastPage: Int = 1
) {
    val canLoadMore: Boolean get() = currentPage < lastPage
}