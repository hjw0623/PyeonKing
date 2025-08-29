package com.hjw0623.presentation.screen.search.search_result.ui

import com.hjw0623.core.domain.model.product.Product

sealed interface SearchResultScreenEvent {
    data class Error(val error: String) : SearchResultScreenEvent
    data class NavigateToProductDetail(val product: Product) : SearchResultScreenEvent
}