package com.hjw0623.presentation.screen.search.text_search.ui

import com.hjw0623.core.domain.product.Product
import com.hjw0623.core.domain.search.search_result.SearchResultNavArgs

sealed interface TextSearchScreenEvent {
    data class Error(val error: String) : TextSearchScreenEvent
    data class NavigateToProductDetail(val product: Product) : TextSearchScreenEvent
    data class NavigateToSearchResult(val searchResultNavArgs: SearchResultNavArgs) : TextSearchScreenEvent
}