package com.hjw0623.presentation.screen.search.search_result

import com.hjw0623.core.domain.product.Product
import com.hjw0623.core.domain.search.search_result.SearchResultSource
import com.hjw0623.core.mockdata.mockProductList

data class SearchResultScreenState(
    val source: SearchResultSource = SearchResultSource.TEXT,
    val products: List<Product> = mockProductList,
    val passedQuery: String = "",
    val passedImagePath: String? = null
)
