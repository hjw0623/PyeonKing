package com.hjw0623.presentation.screen.review.review_history.ui

import com.hjw0623.core.core_domain.model.review.ReviewInfo

sealed interface ReviewHistoryScreenEvent {
    data class Error(val error: String) : ReviewHistoryScreenEvent
    data class NavigateToReviewEdit(val reviewInfo: ReviewInfo) : ReviewHistoryScreenEvent
}