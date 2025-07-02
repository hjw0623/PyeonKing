package com.hjw0623.pyeonking.review.review_edit

sealed interface ReviewEditScreenAction {
    object OnEditClick : ReviewEditScreenAction
    data class OnContentChanged(val content: String) : ReviewEditScreenAction
    data class OnStarRatingChanged(val starRating: Int) : ReviewEditScreenAction
}