package com.hjw0623.presentation.screen.search.text_search.ui

import com.hjw0623.core.core_domain.model.product.Product
import com.hjw0623.core.core_domain.model.search.text_search.FilterType

data class TextSearchScreenState(
    val isLoading: Boolean = false,
    val query: String = "",
    val searchHistory: List<String> = emptyList(),
    val allProducts: List<Product> = emptyList(),
    val filteredProducts: List<Product> = emptyList(),
    val selectedFilters: Map<FilterType, Boolean> = emptyMap()
)
