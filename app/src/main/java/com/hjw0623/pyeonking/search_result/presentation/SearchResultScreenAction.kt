package com.hjw0623.pyeonking.search_result.presentation

import com.hjw0623.pyeonking.core.data.Product

sealed interface SearchResultScreenAction {
    data class OnProductClick(val product: Product) : SearchResultScreenAction
}
