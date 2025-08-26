package com.hjw0623.presentation.screen.product.ui

import com.hjw0623.core.business_logic.model.product.Product
import com.hjw0623.core.business_logic.model.product.ProductDetailTab
import com.hjw0623.core.business_logic.model.product.ReviewItem

data class ProductDetailScreenState(
    val isSummaryLoading: Boolean = false,
    val isReviewLoading: Boolean = false,
    val product: Product? = null,
    val selectedTab: ProductDetailTab = ProductDetailTab.REVIEW,
    val reviewList: List<ReviewItem> = emptyList(),
    val ratingList: List<Int> = List(5) { 0 },
    val avgRating: Double = 0.0,
    val reviewSum: Int = 0,
    val currentPage: Int = 1,
    val lastPage: Int = 0,
    val isLoggedIn: Boolean = false
)
