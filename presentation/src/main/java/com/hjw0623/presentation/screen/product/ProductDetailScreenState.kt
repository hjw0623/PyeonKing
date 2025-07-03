package com.hjw0623.presentation.screen.product

import com.hjw0623.core.domain.product.Product
import com.hjw0623.core.domain.product.ProductDetailTab
import com.hjw0623.core.domain.product.ReviewItem
import com.hjw0623.core.domain.product.StoreLocation
import com.hjw0623.core.mockdata.calculateReviewSum
import com.hjw0623.core.mockdata.mockAvgRating
import com.hjw0623.core.mockdata.mockProduct
import com.hjw0623.core.mockdata.mockRatingList
import com.hjw0623.core.mockdata.mockReviewList

data class ProductDetailScreenState(
    val isLoading: Boolean = false,
    val product: Product? = mockProduct,
    val selectedTab: ProductDetailTab = ProductDetailTab.MAP,
    val reviews: List<ReviewItem> = mockReviewList,
    val storeLocations: List<StoreLocation> = emptyList(),
    val avgRating: Float = mockAvgRating,
    val ratingList: List<Pair<Int, Int>> = mockRatingList,
    val reviewSum: Int = calculateReviewSum(mockRatingList),
    val reviewList: List<ReviewItem> = mockReviewList
)

