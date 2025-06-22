package com.hjw0623.pyeonking.text_sarch.presentation

import com.hjw0623.pyeonking.core.data.Product
import com.hjw0623.pyeonking.text_sarch.data.FilterType

data class TextSearchScreenState(
    val query: String = "",
    val searchHistory: List<String> = emptyList(),
    val products: List<Product> = emptyList(),
    val selectedFilters: Map<FilterType, Boolean> = emptyMap(),
    val selectedProductName: String = ""
)
