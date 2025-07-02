package com.hjw0623.pyeonking.main_screen.text_sarch.presentation

import com.hjw0623.pyeonking.core.data.Product
import com.hjw0623.pyeonking.core.data.mockProductList
import com.hjw0623.pyeonking.main_screen.text_sarch.data.FilterType


data class TextSearchScreenState(
    val query: String = "",
    val searchHistory: List<String> = emptyList(),
    val products: List<Product> = mockProductList,
    val selectedFilters: Map<FilterType, Boolean> = emptyMap(),
    val selectedProduct: Product? = null
)
