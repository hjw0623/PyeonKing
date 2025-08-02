package com.hjw0623.presentation.screen.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjw0623.core.business_logic.model.response.toProduct
import com.hjw0623.core.business_logic.model.product.Product
import com.hjw0623.core.business_logic.repository.SearchRepository
import com.hjw0623.core.business_logic.model.search.search_result.SearchResultNavArgs
import com.hjw0623.core.business_logic.model.search.search_result.SearchResultSource
import com.hjw0623.core.business_logic.model.network.DataResourceResult
import com.hjw0623.presentation.screen.search.search_result.ui.SearchResultScreenEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File

class SearchResultViewModel(
    private val searchRepository: SearchRepository
) : ViewModel() {

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
                    searchRepository.searchItems(navArgs.passedQuery!!).collectLatest { result ->
                        when (result) {
                            DataResourceResult.Loading -> {
                                _isLoading.value = true
                            }

                            is DataResourceResult.Success -> {
                                if (result.data.data.searchItems.isEmpty()) {
                                    _event.emit(SearchResultScreenEvent.Error("검색 결과가 없습니다."))
                                    _isLoading.value = false
                                    return@collectLatest
                                }
                                _products.value =
                                    result.data.data.searchItems.map { it.toProduct() }
                                _isLoading.value = false
                            }

                            is DataResourceResult.Failure -> {
                                val message = result.exception.message.toString()
                                _event.emit(SearchResultScreenEvent.Error(message))
                            }

                            else -> Unit
                        }
                    }
                }

                SearchResultSource.CAMERA -> {
                    _searchTitle.value = "사진으로 검색한 결과"

                    val path = navArgs.passedImagePath
                    if (path == null) {
                        _event.emit(SearchResultScreenEvent.Error("이미지 경로가 없습니다."))
                        _isLoading.value = false
                        return@launch
                    }

                    val file = File(path)

                    searchRepository.searchItemsByImg(file).collectLatest { result ->
                        when (result) {
                            is DataResourceResult.Loading -> _isLoading.value = true

                            is DataResourceResult.Success -> {
                                if (result.data.data.searchItems.isEmpty()) {
                                    _event.emit(SearchResultScreenEvent.Error("검색 결과가 없습니다."))
                                    _isLoading.value = false
                                    return@collectLatest
                                }
                                _products.value =
                                    result.data.data.searchItems.map { it.toProduct() }
                                _isLoading.value = false
                            }

                            is DataResourceResult.Failure -> {
                                _event.emit(SearchResultScreenEvent.Error("이미지 검색 실패: ${result.exception.message}"))
                            }

                            else -> Unit
                        }
                    }
                }
            }

            _isLoading.value = false
        }
    }

    fun onProductClick(product: Product) {
        viewModelScope.launch {
            _event.emit(SearchResultScreenEvent.NavigateToProductDetail(product))
        }
    }
}