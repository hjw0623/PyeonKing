package com.hjw0623.pyeonking.search_result.presentation

import com.hjw0623.pyeonking.core.data.Product

sealed interface SearchResultScreenEvent {
    data class Error(val error: String) : SearchResultScreenEvent
    data class NavigateToProductDetail(val product: Product) : SearchResultScreenEvent
}