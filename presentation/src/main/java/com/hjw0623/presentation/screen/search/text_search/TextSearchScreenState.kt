package com.hjw0623.presentation.screen.search.text_search

import com.hjw0623.core.domain.product.Product
import com.hjw0623.core.domain.search.text_search.FilterType
import com.hjw0623.core.mockdata.mockProductList


data class TextSearchScreenState(
    val query: String = "",
    val searchHistory: List<String> = emptyList(),
    val products: List<Product> = mockProductList,
    val selectedFilters: Map<FilterType, Boolean> = emptyMap(),
    val selectedProduct: Product? = null
)
