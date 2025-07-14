package com.hjw0623.core.util.mockdata

import android.annotation.SuppressLint

@SuppressLint("DefaultLocale")
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