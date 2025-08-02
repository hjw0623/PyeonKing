package com.hjw0623.presentation.screen.search.text_search.ui

import com.hjw0623.core.business_logic.model.product.Product
import com.hjw0623.core.business_logic.model.search.search_result.SearchResultNavArgs

sealed interface TextSearchScreenEvent {
    data class Error(val error: String) : TextSearchScreenEvent
    data class NavigateToProductDetail(val product: Product) : TextSearchScreenEvent
    data class NavigateToSearchResult(val searchResultNavArgs: SearchResultNavArgs) : TextSearchScreenEvent
}