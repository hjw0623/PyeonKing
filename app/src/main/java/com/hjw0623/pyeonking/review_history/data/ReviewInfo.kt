package com.hjw0623.pyeonking.review_history.data


data class ReviewInfo(
    val reviewId: Long,
    val content: String,
    val starRating: Int,
    val createdAt: String,
    val productName: String,
    val productImgUrl: String
)
