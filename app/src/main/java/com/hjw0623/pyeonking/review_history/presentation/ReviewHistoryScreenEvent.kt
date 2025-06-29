package com.hjw0623.pyeonking.review_history.presentation

import com.hjw0623.pyeonking.review_history.data.ReviewInfo

sealed interface ReviewHistoryScreenEvent {
    data class Error(val error: String) : ReviewHistoryScreenEvent
    data class NavigateToReviewEdit(val reviewInfo: ReviewInfo) : ReviewHistoryScreenEvent
}