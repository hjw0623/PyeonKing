package com.hjw0623.pyeonking.review.review_edit

sealed interface ReviewEditScreenEvent {
    data class Error(val error: String) : ReviewEditScreenEvent
    data object NavigateReviewHistory : ReviewEditScreenEvent
}