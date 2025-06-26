package com.hjw0623.pyeonking.review_history.presentation

sealed interface ReviewHistoryScreenAction {
    object OnBackClick : ReviewHistoryScreenAction
    object OnEditReviewClick : ReviewHistoryScreenAction
}