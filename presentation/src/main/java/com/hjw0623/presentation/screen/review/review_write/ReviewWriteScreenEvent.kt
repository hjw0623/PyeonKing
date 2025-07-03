package com.hjw0623.presentation.screen.review.review_write

sealed interface ReviewWriteScreenEvent {
    data class Error(val error: String) : ReviewWriteScreenEvent
    object NavigateBackToProductDetail : ReviewWriteScreenEvent
}