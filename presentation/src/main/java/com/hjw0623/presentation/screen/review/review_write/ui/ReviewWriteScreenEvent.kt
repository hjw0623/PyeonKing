package com.hjw0623.presentation.screen.review.review_write.ui

sealed interface ReviewWriteScreenEvent {
    data class Error(val error: String) : ReviewWriteScreenEvent
    data class NavigateBackToProductDetail(val message: String) : ReviewWriteScreenEvent
}