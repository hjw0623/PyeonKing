package com.hjw0623.presentation.screen.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjw0623.core.constants.Error.BLANK_INPUT
import com.hjw0623.core.constants.Error.TOO_SHORT_SEARCH_QUERY_INPUT
import com.hjw0623.core.constants.Error.UNKNOWN_ERROR
import com.hjw0623.core.data.model.BaseResponse
import com.hjw0623.core.data.model.Item
import com.hjw0623.core.data.model.toProduct
import com.hjw0623.core.domain.product.ProductRepository
import com.hjw0623.core.domain.product.Product
import com.hjw0623.core.domain.search.search_result.SearchResultNavArgs
import com.hjw0623.core.domain.search.search_result.SearchResultSource
import com.hjw0623.core.network.DataResourceResult
import com.hjw0623.presentation.screen.home.ui.HomeScreenEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val MIN_SEARCH_QUERY_LENGTH = 2

class HomeViewModel(
    private val productRepository: ProductRepository,
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _recommendItemList = MutableStateFlow<DataResourceResult<BaseResponse<List<Item>>>>(
        DataResourceResult.DummyConstructor
    )
    private val _recommendProductList = MutableStateFlow<List<Product>>(emptyList())
    val recommendProductList = _recommendProductList.asStateFlow()

    private val _event = MutableSharedFlow<HomeScreenEvent>()
    val event = _event.asSharedFlow()

    init {
        fetchRecommendList()
    }

    private fun fetchRecommendList() {
        viewModelScope.launch {
            _isLoading.value = true
            _recommendItemList.value = DataResourceResult.Loading

            productRepository.getRecommendList().collectLatest { result ->
                when (result) {
                    is DataResourceResult.Success -> {
                        _recommendItemList.value = result

                        val items = result.data
                        val products = items.data.map { it.toProduct() }

                        _recommendProductList.value = products
                        _isLoading.value = false
                    }

                    is DataResourceResult.Failure -> {
                        val message = result.exception.message ?: UNKNOWN_ERROR
                        _event.emit(HomeScreenEvent.Error(message))
                        _isLoading.value = false
                    }

                    else -> _isLoading.value = false
                }
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onSearchQueryChangeDebounced(query: String) {
        //추후 기능 추가
    }

    fun onProductCardClick(product: Product) {
        viewModelScope.launch {
            _event.emit(HomeScreenEvent.NavigateToProductDetail(product))
        }
    }

    fun onSearchClick() {
        val query = searchQuery.value.trim()

        if (query.isBlank()) {
            viewModelScope.launch {
                _event.emit(HomeScreenEvent.Error(BLANK_INPUT))
            }
            return
        }

        if (query.length < MIN_SEARCH_QUERY_LENGTH) {
            viewModelScope.launch {
                _event.emit(HomeScreenEvent.Error(TOO_SHORT_SEARCH_QUERY_INPUT))
            }
            return
        }

        viewModelScope.launch {
            val navArgs = SearchResultNavArgs(
                source = SearchResultSource.TEXT,
                passedQuery = query,
                passedImagePath = null
            )
            _event.emit(HomeScreenEvent.NavigateToSearchResult(navArgs))
        }
    }
}