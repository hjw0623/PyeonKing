package com.hjw0623.presentation.screen.search.search_result

import com.hjw0623.core.domain.product.Product

sealed interface SearchResultScreenAction {
    data class OnProductClick(val product: Product) : SearchResultScreenAction
}
