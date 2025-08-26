package com.hjw0623.presentation.screen.review.review_edit.ui

import com.hjw0623.core.business_logic.model.review.ReviewInfo

data class ReviewEditScreenState(
    val originalReview: ReviewInfo? = null,
    val productName: String = "",
    val productImgUrl: String? = null,
    val newContent: String = "",
    val newStarRating: Int = 0,
    val isEditing: Boolean = false,
) {
    val isEditButtonEnabled: Boolean
        get() = originalReview?.let { ori ->
            !isEditing &&
                    newContent.isNotBlank() &&
                    (ori.content != newContent || ori.starRating != newStarRating)
        } ?: false
}