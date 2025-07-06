package com.hjw0623.presentation.screen.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjw0623.core.domain.product.Product
import com.hjw0623.core.domain.search.search_result.SearchResultNavArgs
import com.hjw0623.core.domain.search.search_result.SearchResultSource
import com.hjw0623.core.mockdata.mockProductList
import com.hjw0623.presentation.screen.search.search_result.ui.SearchResultScreenEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchResultViewModel(
    // private val searchRepository: SearchRepository
): ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products = _products.asStateFlow()

    private val _searchTitle = MutableStateFlow("")
    val searchTitle = _searchTitle.asStateFlow()

    private val _event = MutableSharedFlow<SearchResultScreenEvent>()
    val event = _event.asSharedFlow()

    fun searchProducts(navArgs: SearchResultNavArgs) {
        viewModelScope.launch {
            _isLoading.value = true
            when (navArgs.source) {
                SearchResultSource.TEXT -> {
                    _searchTitle.value = "'${navArgs.passedQuery}' 검색 결과"
                    // TODO: searchRepository.searchByText(navArgs.passedQuery)
                }
                SearchResultSource.CAMERA -> {
                    _searchTitle.value = "사진으로 검색한 결과"
                    // TODO: searchRepository.searchByImage(navArgs.passedImagePath)
                }
            }

            try {
                delay(1500)
                _products.value = mockProductList.shuffled()
            } catch (e: Exception) {
                _event.emit(SearchResultScreenEvent.Error("검색 결과를 불러오는 데 실패했습니다."))
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onProductClick(product: Product) {
        viewModelScope.launch {
            _event.emit(SearchResultScreenEvent.NavigateToProductDetail(product))
        }
    }
}