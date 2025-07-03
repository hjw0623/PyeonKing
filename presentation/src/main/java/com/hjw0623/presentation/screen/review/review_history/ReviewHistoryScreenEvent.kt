package com.hjw0623.presentation.screen.review.review_history

import com.hjw0623.core.domain.review.review_history.ReviewInfo

sealed interface ReviewHistoryScreenEvent {
    data class Error(val error: String) : ReviewHistoryScreenEvent
    data class NavigateToReviewEdit(val reviewInfo: ReviewInfo) : ReviewHistoryScreenEvent
}