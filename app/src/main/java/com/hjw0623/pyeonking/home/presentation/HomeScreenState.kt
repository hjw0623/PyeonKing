package com.hjw0623.pyeonking.home.presentation

import com.hjw0623.pyeonking.home.data.RecommendItem
import com.hjw0623.pyeonking.home.data.mockRecommendItemList

data class HomeScreenState(
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val selectedItemName: String = "",
    val recommendList: List<RecommendItem> = mockRecommendItemList
)