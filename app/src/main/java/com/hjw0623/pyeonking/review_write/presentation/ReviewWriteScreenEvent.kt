package com.hjw0623.pyeonking.review_write.presentation

sealed interface ReviewWriteScreenEvent {
    data class Error(val error: String) : ReviewWriteScreenEvent
    object NavigateBackToProductDetail : ReviewWriteScreenEvent
}