package com.hjw0623.pyeonking.main_screen.text_sarch.presentation

import com.hjw0623.pyeonking.core.data.Product
import com.hjw0623.pyeonking.core.data.SearchResultNavArgs

sealed interface TextSearchScreenEvent {
    data class Error(val error: String) : TextSearchScreenEvent
    data class NavigateToProductDetail(val product: Product) : TextSearchScreenEvent
    data class NavigateToSearchResult(val searchResultNavArgs: SearchResultNavArgs) : TextSearchScreenEvent
}