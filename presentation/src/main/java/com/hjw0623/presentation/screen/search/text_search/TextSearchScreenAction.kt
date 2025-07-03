package com.hjw0623.presentation.screen.search.text_search

import com.hjw0623.core.domain.product.Product
import com.hjw0623.core.domain.search.text_search.FilterType

sealed interface TextSearchScreenAction  {
    data class OnDeleteSearchHistory(val historyQuery: String) : TextSearchScreenAction
    data class OnSearchClick(val searchInput: String) : TextSearchScreenAction
    data class OnHistoryClick(val historyInput: String) : TextSearchScreenAction
    data class OnProductClick(val product: Product ) : TextSearchScreenAction
    data class OnToggleFilter(val filterType: FilterType) : TextSearchScreenAction
    data class OnQueryChange(val query: String) : TextSearchScreenAction
    data object OnClearClick : TextSearchScreenAction
}
