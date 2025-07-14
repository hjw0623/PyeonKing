package com.hjw0623.presentation.screen.review.review_edit.ui

sealed interface ReviewEditScreenEvent {
    data class Error(val error: String) : ReviewEditScreenEvent
    data object NavigateReviewHistory : ReviewEditScreenEvent
}