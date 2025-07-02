package com.hjw0623.pyeonking.review.review_history.presentation

import com.hjw0623.pyeonking.review.review_history.data.ReviewInfo

sealed interface ReviewHistoryScreenAction {
    data class OnEditReviewClick(val reviewInfo: ReviewInfo) : ReviewHistoryScreenAction
}