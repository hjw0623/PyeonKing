package com.hjw0623.pyeonking.search_result.presentation

import com.hjw0623.pyeonking.core.data.Product
import com.hjw0623.pyeonking.core.data.mockProductList
import com.hjw0623.pyeonking.search_result.data.SearchResultSource

data class SearchResultScreenState(
    val source: SearchResultSource = SearchResultSource.TEXT,
    val products: List<Product> = mockProductList,
    val passedQuery: String = "",
    val passedImagePath: String? = null
)
