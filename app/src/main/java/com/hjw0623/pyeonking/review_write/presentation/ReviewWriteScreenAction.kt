package com.hjw0623.pyeonking.review_write.presentation

sealed interface ReviewWriteScreenAction {
    data class OnContentChange(val content: String) : ReviewWriteScreenAction
    data class OnRatingChange(val rating: Int) : ReviewWriteScreenAction
    data class OnSubmitClick(val rating: Int, val content: String) : ReviewWriteScreenAction
}
