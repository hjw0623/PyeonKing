package com.hjw0623.pyeonking.product_detail.presentation

import com.hjw0623.pyeonking.core.data.Product
import com.hjw0623.pyeonking.core.data.mockProduct
import com.hjw0623.pyeonking.product_detail.data.ProductDetailTab
import com.hjw0623.pyeonking.product_detail.data.ReviewItem
import com.hjw0623.pyeonking.product_detail.data.StoreLocation
import com.hjw0623.pyeonking.product_detail.data.calculateReviewSum
import com.hjw0623.pyeonking.product_detail.data.mockAvgRating
import com.hjw0623.pyeonking.product_detail.data.mockRatingList
import com.hjw0623.pyeonking.product_detail.data.mockReviewList


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

