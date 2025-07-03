package com.hjw0623.presentation.screen.home

import com.hjw0623.core.domain.product.Product
import com.hjw0623.core.domain.search.search_result.SearchResultNavArgs

sealed interface HomeScreenEvent {
    data class Error(val error: String) : HomeScreenEvent
    data class NavigateToProductDetail(val product: Product) : HomeScreenEvent
    data class NavigateToSearchResult(val searchResultNavArgs: SearchResultNavArgs) : HomeScreenEvent
}