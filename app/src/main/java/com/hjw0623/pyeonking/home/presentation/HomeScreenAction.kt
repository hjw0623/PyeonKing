package com.hjw0623.pyeonking.home.presentation

sealed interface HomeScreenAction {
    data class OnSearchClick(val query: String) : HomeScreenAction
    data class OnSearchQueryChange(val query: String) : HomeScreenAction
    data class OnCardClick(val productName: String) : HomeScreenAction
}
