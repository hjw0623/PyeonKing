package com.hjw0623.presentation.screen.home

import com.hjw0623.core.domain.product.Product

sealed interface HomeScreenAction {
    data class OnSearchClick(val query: String) : HomeScreenAction
    data class OnSearchQueryChange(val query: String) : HomeScreenAction
    data class OnCardClick(val product: Product) : HomeScreenAction
}
