package com.hjw0623.pyeonking.product_detail.data

val mockReviewList = listOf(
    ReviewItem(
        reviewId = "1",
        rating = 5f,
        content = "맛있어요",
        userName = "fwf",
        createdAt = "2023-06-23"
    ),
    ReviewItem(
        reviewId = "2",
        rating = 4f,
        content = "맛있어요",
        userName = "fdf",
        createdAt = "2023-06-23"
    ),
    ReviewItem(
        reviewId = "3",
        rating = 3f,
        content = "맛있어요",
        userName = "fdfd",
        createdAt = "2023-06-23"
    )
)

val mockRatingList  = listOf(
    Pair(5, 10),
    Pair(4, 8),
    Pair(3, 5),
    Pair(2, 4),
    Pair(1, 2),
)

val mockAvgRating = calculateAverageRating(mockRatingList)

fun calculateAverageRating(ratingList: List<Pair<Int, Int>>): Float {
    val totalPoints = ratingList.sumOf { (rating, count) -> rating * count }
    val totalCount = ratingList.sumOf { it.second }

    if (totalCount == 0) return 0f

    val rawAverage = totalPoints.toFloat() / totalCount
    return String.format("%.1f", rawAverage).toFloat()
}
fun calculateReviewSum(ratingList: List<Pair<Int, Int>>): Int {
    val totalReview = ratingList.sumOf { it.second }
    return totalReview
}