package com.hjw0623.presentation.screen.review.review_edit

import com.hjw0623.core.domain.review.review_history.ReviewInfo
import com.hjw0623.core.mockdata.mockReviewInfo

data class ReviewEditScreenState(
    val reviewInfo: ReviewInfo = mockReviewInfo,
    val newContent: String = "",
    val newStarRating: Int = 0,
) {
    val isEditButtonEnabled: Boolean
        get() = newContent.isNotBlank() && reviewInfo.content != newContent
}
