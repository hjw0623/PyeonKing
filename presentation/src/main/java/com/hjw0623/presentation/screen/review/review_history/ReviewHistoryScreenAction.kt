package com.hjw0623.presentation.screen.review.review_history

import com.hjw0623.core.domain.review.review_history.ReviewInfo

sealed interface ReviewHistoryScreenAction {
    data class OnEditReviewClick(val reviewInfo: ReviewInfo) : ReviewHistoryScreenAction
}