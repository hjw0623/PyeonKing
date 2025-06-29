package com.hjw0623.pyeonking.home.presentation

import com.hjw0623.pyeonking.core.data.Product

sealed interface HomeScreenAction {
    data class OnSearchClick(val query: String) : HomeScreenAction
    data class OnSearchQueryChange(val query: String) : HomeScreenAction
    data class OnCardClick(val product: Product) : HomeScreenAction
}
