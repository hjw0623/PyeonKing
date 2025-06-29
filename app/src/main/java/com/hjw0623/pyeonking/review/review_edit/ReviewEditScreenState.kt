package com.hjw0623.pyeonking.review.review_edit

import com.hjw0623.pyeonking.review.review_history.data.ReviewInfo
import com.hjw0623.pyeonking.review.review_history.data.mockReviewInfo

data class ReviewEditScreenState(
    val reviewInfo: ReviewInfo = mockReviewInfo,
    val newContent: String = "",
    val newStarRating: Int = 0,
) {
    val isEditButtonEnabled: Boolean
        get() = newContent.isNotBlank() && reviewInfo.content != newContent
}
