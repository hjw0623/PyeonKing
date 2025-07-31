package com.hjw0623.core.domain.review.review_history

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class ReviewInfo(
    val reviewId: Long,
    val content: String,
    val starRating: Int,
    val createdAt: String,
    val productName: String,
    val productImgUrl: String?
): Parcelable