package com.hjw0623.pyeonking.home.presentation

import com.hjw0623.pyeonking.core.data.Product


data class HomeScreenState(
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val selectedItemName: String = "",
    val recommendList: List<Product> = emptyList(),
)