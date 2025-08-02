package com.hjw0623.presentation.screen.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjw0623.core.business_logic.model.response.toProduct
import com.hjw0623.core.business_logic.model.product.Product
import com.hjw0623.core.business_logic.repository.SearchRepository
import com.hjw0623.core.business_logic.model.search.search_result.SearchResultNavArgs
import com.hjw0623.core.business_logic.model.search.search_result.SearchResultSource
import com.hjw0623.core.business_logic.model.search.text_search.FilterType
import com.hjw0623.core.business_logic.model.search.text_search.filterProducts
import com.hjw0623.core.business_logic.model.network.DataResourceResult
import com.hjw0623.presentation.screen.search.text_search.ui.TextSearchScreenEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TextSearchViewModel(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    private val _searchHistory = MutableStateFlow<List<String>>(emptyList())
    val searchHistory = _searchHistory.asStateFlow()

    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())
    val allProducts = _allProducts.asStateFlow()

    private val _selectedFilters = MutableStateFlow<Map<FilterType, Boolean>>(emptyMap())
    val selectedFilters = _selectedFilters.asStateFlow()
    private val _event = MutableSharedFlow<TextSearchScreenEvent>()
    val event = _event.asSharedFlow()

    init {
        fetchSearchHistory()
    }

    fun fetchAllProducts() {
        viewModelScope.launch {
            searchRepository.getAllItems().collectLatest { result ->
                when (result) {
                    DataResourceResult.Loading -> {
                        _isLoading.value = true
                    }

                    is DataResourceResult.Success -> {
                        _isLoading.value = false
                        _allProducts.value = result.data.data.searchItems.map { it.toProduct() }
                        val items = result.data.data.searchItems
                    }

                    is DataResourceResult.Failure -> {
                        val message = result.exception.message.toString()
                        _event.emit(TextSearchScreenEvent.Error(message))
                        _isLoading.value = false
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun fetchSearchHistory() {
        viewModelScope.launch {
            _searchHistory.value = listOf("라면", "콜라", "아이스크림", "도시락")
        }
    }

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }

    fun onClearClick() {
        _query.value = ""
    }

    fun onDeleteSearchHistory(history: String) {
        _searchHistory.update { currentList ->
            currentList.filterNot { it == history }
        }
    }

    fun onHistoryClick(history: String) {
        _query.value = history
        onSearchClick()
    }

    fun onSearchClick() {
        val currentQuery = query.value
        if (currentQuery.isBlank()) {
            viewModelScope.launch { _event.emit(TextSearchScreenEvent.Error("검색어를 입력해주세요.")) }
            return
        }

        _searchHistory.update { currentList ->
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
        _selectedFilters.value = updatedFilters
        applyFilters()
    }

    private fun applyFilters() {
        _allProducts.value = filterProducts(
            allProducts = _allProducts.value,
            filters = selectedFilters.value
        )
    }
}