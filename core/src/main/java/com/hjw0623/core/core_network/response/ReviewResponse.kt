package com.hjw0623.core.core_network.response

import com.hjw0623.core.domain.model.product.ReviewItem
import com.hjw0623.core.domain.model.review.ReviewInfo
import com.hjw0623.core.ui.util.getFullImageUrl

data class ReviewPage(
    val content: List<ReviewResponse>,
    val totalElements: Int,
    val totalPages: Int,
    val last: Boolean,
    val number: Int,
    val size: Int
)

data class ReviewResponse(
    val commentId: Long,
    val promotionId: Long,
    val star: Int,
    val content: String,
    val userName: String,
    val createdAt: String,
    val product: ReviewProduct
)

data class ReviewProduct(
    val id: Long,
    val name: String,
    val imageUrl: String?,
    val brand: String
)

fun ReviewResponse.toReviewItem(): ReviewItem {
    return ReviewItem(
        reviewId = this.commentId.toString(),
        rating = this.star.toFloat(),
        content = this.content,
        userName = this.userName,
        createdAt = this.createdAt
    )
}

fun ReviewResponse.toReviewInfo(): ReviewInfo {
    return ReviewInfo(
        reviewId = this.commentId,
        content = this.content,
        starRating = this.star,
        createdAt = this.createdAt,
        productName = this.product.name,
        productImgUrl = getFullImageUrl(this.product.imageUrl)
    )
}