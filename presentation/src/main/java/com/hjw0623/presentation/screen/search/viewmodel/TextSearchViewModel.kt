package com.hjw0623.presentation.screen.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjw0623.core.constants.error.ErrorMessage
import com.hjw0623.core.domain.model.product.Product
import com.hjw0623.core.domain.model.search.filterProducts
import com.hjw0623.core.domain.model.search.SearchResultNavArgs
import com.hjw0623.core.domain.model.search.SearchResultSource
import com.hjw0623.core.domain.model.search.FilterType
import com.hjw0623.core.domain.repository.SearchRepository
import com.hjw0623.core.domain.repository.UserDataStoreRepository
import com.hjw0623.core.network.common.DataResourceResult
import com.hjw0623.core.network.response.search.toProduct
import com.hjw0623.presentation.screen.search.text_search.ui.TextSearchScreenEvent
import com.hjw0623.presentation.screen.search.text_search.ui.TextSearchScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TextSearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val userDataStoreRepository: UserDataStoreRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TextSearchScreenState())
    val state = _state.asStateFlow()
    private val _event = MutableSharedFlow<TextSearchScreenEvent>()
    val event = _event.asSharedFlow()

    init {
        observeSearchHistory()
        fetchAllProducts()
    }

    fun fetchAllProducts() {
        viewModelScope.launch {
            searchRepository.getAllItems().collectLatest { result ->
                when (result) {
                    DataResourceResult.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }

                    is DataResourceResult.Success -> {
                        val products = result.data.data.searchItems.map { it.toProduct() }
                        _state.update {
                            it.copy(
                                isLoading = false,
                                allProducts = products,
                                filteredProducts = applyFilters(
                                    products = products,
                                    filters = it.selectedFilters
                                )
                            )
                        }
                    }

                    is DataResourceResult.Failure -> {
                        _state.update { it.copy(isLoading = false) }
                        val message = result.exception.message.toString()
                        _event.emit(TextSearchScreenEvent.Error(message))
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun observeSearchHistory() {
        viewModelScope.launch {
            userDataStoreRepository.searchHistoryFlow
                .distinctUntilChanged()
                .collectLatest { history ->
                    _state.update { it.copy(searchHistory = history) }
                }
        }
    }

    fun onQueryChange(newQuery: String) {
        _state.update { it.copy(query = newQuery) }
    }

    fun onClearClick() {
        _state.update { it.copy(query = "") }
    }

    fun onDeleteSearchHistory(history: String) {
        viewModelScope.launch {
            try {
                userDataStoreRepository.removeSearchHistory(history)
            } catch (e: Exception) {
                _event.emit(TextSearchScreenEvent.Error(ErrorMessage.ERROR_SEARCH_HISTORY_DELETE_FAILED))
            }
        }
    }

    fun onHistoryClick(history: String) {
        _state.update { it.copy(query = history) }
        onSearchClick()
    }

    fun onSearchClick() {
        val currentQuery = _state.value.query
        if (currentQuery.isBlank()) {
            viewModelScope.launch {
                _event.emit(TextSearchScreenEvent.Error(ErrorMessage.ERROR_INPUT_SEARCH_EMPTY))
            }
            return
        }

        viewModelScope.launch {
            try {
                userDataStoreRepository.saveSearchHistory(currentQuery)
            } catch (e: Exception) {
                _event.emit(TextSearchScreenEvent.Error(ErrorMessage.ERROR_SEARCH_HISTORY_SAVE_FAILED))
            }
        }

        viewModelScope.launch {
            val navArgs = SearchResultNavArgs(
                source = SearchResultSource.TEXT,
                passedQuery = currentQuery,
                passedImagePath = null
            )
            _state.update {
                it.copy(
                    query = ""
                )
            }
            _event.emit(TextSearchScreenEvent.NavigateToSearchResult(navArgs))
        }
    }

    fun onProductClick(product: Product) {
        viewModelScope.launch {
            _event.emit(TextSearchScreenEvent.NavigateToProductDetail(product))
        }
    }

    fun onToggleFilter(filterType: FilterType) {
        _state.update {
            val updated = it.selectedFilters.toMutableMap()
            updated[filterType] = it.selectedFilters[filterType] != true
            val newFiltered = applyFilters(
                products = it.allProducts,
                filters = updated
            )
            it.copy(
                selectedFilters = updated,
                filteredProducts = newFiltered
            )
        }
    }

    private fun applyFilters(
        products: List<Product>,
        filters: Map<FilterType, Boolean>
    ): List<Product> {
        return if (filters.values.none { it }) {
            products
        } else {
            filterProducts(
                allProducts = products,
                filters = filters
            )
        }
    }
}