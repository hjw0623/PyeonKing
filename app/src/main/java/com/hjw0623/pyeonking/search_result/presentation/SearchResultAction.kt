package com.hjw0623.pyeonking.search_result.presentation

import com.hjw0623.pyeonking.core.data.Product

sealed interface SearchResultAction {
    data object OnBackClick : SearchResultAction
    data class OnProductClick(val item: Product) : SearchResultAction
}
