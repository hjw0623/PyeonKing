package com.hjw0623.pyeonking.main_screen.text_sarch.presentation

import com.hjw0623.pyeonking.core.data.Product
import com.hjw0623.pyeonking.main_screen.text_sarch.data.FilterType

sealed interface TextSearchScreenAction  {
    data class OnDeleteSearchHistory(val historyQuery: String) : TextSearchScreenAction
    data class OnSearchClick(val searchInput: String) : TextSearchScreenAction
    data class OnHistoryClick(val historyInput: String) : TextSearchScreenAction
    data class OnProductClick(val product: Product ) : TextSearchScreenAction
    data class OnToggleFilter(val filterType: FilterType) : TextSearchScreenAction
    data class OnQueryChange(val query: String) : TextSearchScreenAction
    data object OnClearClick : TextSearchScreenAction
}
