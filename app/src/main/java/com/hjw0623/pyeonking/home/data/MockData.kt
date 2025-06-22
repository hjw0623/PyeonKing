package com.hjw0623.pyeonking.home.data

import com.hjw0623.pyeonking.core.data.mockProductList
import com.hjw0623.pyeonking.home.presentation.HomeScreenState

val mockHomeScreenState = HomeScreenState(
    isLoading = false,
    searchQuery = "",
    selectedItemName = "",
    recommendList = mockProductList
)