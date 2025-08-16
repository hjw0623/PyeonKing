package com.hjw0623.presentation.screen.home.ui

import com.hjw0623.core.business_logic.model.product.Product

data class HomeScreenState(
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val recommendList: List<Product> = emptyList(),
    val isLoggedIn: Boolean = false,
    val hasFetchedRecommendList: Boolean = false
)
