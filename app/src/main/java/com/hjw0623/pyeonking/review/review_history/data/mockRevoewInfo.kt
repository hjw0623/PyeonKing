package com.hjw0623.pyeonking.review.review_history.data

val mockReviewInfo = ReviewInfo(
    reviewId = 2,
    content = "상쾌하고 좋네요! 여름에 딱이에요.",
    starRating = 5,
    createdAt = "2025-06-27 02:16:06.000000",
    productName = "코카콜라제로캔350ML",
    productImgUrl = "https://image.woodongs.com/imgsvr/item/GD_8801094083205_003.jpg"
)

val mockReviewHistoryList = listOf(
    mockReviewInfo.copy(reviewId = 1),
    mockReviewInfo.copy(reviewId = 2),
    mockReviewInfo.copy(reviewId = 3),
    mockReviewInfo.copy(reviewId = 4),
    mockReviewInfo.copy(reviewId = 5),
)