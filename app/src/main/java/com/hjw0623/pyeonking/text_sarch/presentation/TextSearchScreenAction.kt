package com.hjw0623.pyeonking.text_sarch.presentation

import com.hjw0623.pyeonking.text_sarch.data.FilterType

sealed interface TextSearchScreenAction  {
    data class OnDeleteSearchHistory(val historyQuery: String) : TextSearchScreenAction
    data class OnSearchClick(val searchInput: String) : TextSearchScreenAction
    data class OnHistoryClick(val historyInput: String) : TextSearchScreenAction
    data class OnProductClick(val productName: String ) : TextSearchScreenAction
    data class OnToggleFilter(val filterType: FilterType) : TextSearchScreenAction
    data class OnQueryChange(val query: String) : TextSearchScreenAction
    data object OnClearClick : TextSearchScreenAction
}
