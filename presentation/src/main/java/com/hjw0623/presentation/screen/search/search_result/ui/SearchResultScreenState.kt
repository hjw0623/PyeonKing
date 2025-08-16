package com.hjw0623.presentation.screen.search.search_result.ui

import com.hjw0623.core.business_logic.model.product.Product
import com.hjw0623.core.business_logic.model.search.search_result.SearchResultSource

data class SearchResultScreenState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val searchTitle: String = "",
    val source: SearchResultSource = SearchResultSource.TEXT,
    val query: String? = null,
    val imagePath: String? = null
)
