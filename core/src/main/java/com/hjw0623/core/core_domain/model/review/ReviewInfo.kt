package com.hjw0623.core.core_domain.model.review

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