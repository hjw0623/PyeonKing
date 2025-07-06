package com.hjw0623.presentation.screen.review.review_write.ui

sealed interface ReviewWriteScreenEvent {
    data class Error(val error: String) : ReviewWriteScreenEvent
    object NavigateBackToProductDetail : ReviewWriteScreenEvent
}