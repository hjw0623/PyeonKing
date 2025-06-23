package com.hjw0623.pyeonking.product_detail.data

data class ReviewItem(
    val reviewId: String,
    val rating: Float,
    val content: String,
    val userName: String,
    val createdAt: String
) {
    val userImage = "https://robohash.org/${userName}.png"
}
