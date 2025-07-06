package com.hjw0623.presentation.screen.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjw0623.core.domain.product.Product
import com.hjw0623.core.domain.search.search_result.SearchResultNavArgs
import com.hjw0623.core.domain.search.search_result.SearchResultSource
import com.hjw0623.core.domain.search.text_search.FilterType
import com.hjw0623.core.domain.search.text_search.filterProducts
import com.hjw0623.core.mockdata.mockProductList
import com.hjw0623.presentation.screen.search.text_search.ui.TextSearchScreenEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TextSearchViewModel(
    // private val repository: SearchRepository
) : ViewModel() {

    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())

    val query = MutableStateFlow("")
    val searchHistory = MutableStateFlow<List<String>>(emptyList())
    val products = MutableStateFlow<List<Product>>(emptyList())
    val selectedFilters = MutableStateFlow<Map<FilterType, Boolean>>(emptyMap())

    private val _event = MutableSharedFlow<TextSearchScreenEvent>()
    val event = _event.asSharedFlow()

    init {
        fetchAllProducts()
        fetchSearchHistory()
    }

    private fun fetchAllProducts() {
        viewModelScope.launch {
            // TODO:  Repository  전체 상품 목록
            _allProducts.value = mockProductList
            products.value = mockProductList
        }
    }

    private fun fetchSearchHistory() {
        viewModelScope.launch {
            // TODO: DataStore  최근 검색어 목록
            searchHistory.value = listOf("라면", "콜라", "아이스크림", "도시락")
        }
    }

    fun onQueryChange(newQuery: String) {
        query.value = newQuery
    }

    fun onClearClick() {
        query.value = ""
    }

    fun onDeleteSearchHistory(history: String) {
        searchHistory.update { currentList ->
            currentList.filterNot { it == history }
        }
    }

    fun onHistoryClick(history: String) {
        query.value = history
        onSearchClick()
    }

    fun onSearchClick() {
        val currentQuery = query.value
        if (currentQuery.isBlank()) {
            viewModelScope.launch { _event.emit(TextSearchScreenEvent.Error("검색어를 입력해주세요.")) }
            return
        }

        searchHistory.update { currentList ->
            listOf(currentQuery) + currentList.filterNot { it == currentQuery }
        }

        viewModelScope.launch {
            val navArgs = SearchResultNavArgs(
                source = SearchResultSource.TEXT,
                passedQuery = currentQuery,
                passedImagePath = null
            )
            _event.emit(TextSearchScreenEvent.NavigateToSearchResult(navArgs))
        }
    }

    fun onProductClick(product: Product) {
        viewModelScope.launch {
            _event.emit(TextSearchScreenEvent.NavigateToProductDetail(product))
        }
    }

    fun onToggleFilter(filterType: FilterType) {
        val updatedFilters = selectedFilters.value.toMutableMap()
        updatedFilters[filterType] = selectedFilters.value[filterType] != true
        selectedFilters.value = updatedFilters
        applyFilters()
    }

    private fun applyFilters() {
        products.value = filterProducts(
            allProducts = _allProducts.value,
            filters = selectedFilters.value
        )
    }
}