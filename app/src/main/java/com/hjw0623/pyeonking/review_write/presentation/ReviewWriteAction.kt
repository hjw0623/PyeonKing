package com.hjw0623.pyeonking.review_write.presentation

sealed interface ReviewWriteAction {
    data class OnContentChange(val content: String) : ReviewWriteAction
    data class OnRatingChange(val rating: Int) : ReviewWriteAction
    data class OnSubmitClick(val rating: Int, val content: String) : ReviewWriteAction
    data object OnBackClick : ReviewWriteAction
}
