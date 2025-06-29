package com.hjw0623.pyeonking.home.presentation

import com.hjw0623.pyeonking.core.data.Product
import com.hjw0623.pyeonking.core.data.SearchResultNavArgs

sealed interface HomeScreenEvent {
    data class Error(val error: String) : HomeScreenEvent
    data class NavigateToProductDetail(val product: Product) : HomeScreenEvent
    data class NavigateToSearchResult(val searchResultNavArgs: SearchResultNavArgs) : HomeScreenEvent
}