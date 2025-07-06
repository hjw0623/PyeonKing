package com.hjw0623.presentation.screen.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjw0623.core.domain.product.Product
import com.hjw0623.core.domain.search.search_result.SearchResultNavArgs
import com.hjw0623.core.domain.search.search_result.SearchResultSource
import com.hjw0623.core.mockdata.mockProductList
import com.hjw0623.presentation.screen.home.ui.HomeScreenEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val MIN_SEARCH_QUERY_LENGTH = 2

class HomeViewModel(
    //private val productRepository: ProductRepository,
    //private val searchRepository: SearchRepository
): ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _recommendList = MutableStateFlow<List<Product>>(emptyList())
    val recommendList = _recommendList.asStateFlow()

    private val _event = MutableSharedFlow<HomeScreenEvent>()
    val event = _event.asSharedFlow()

    init {
        fetchRecommendList()
    }

    private fun fetchRecommendList() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                delay(1000)
                _recommendList.value = mockProductList
            } catch (e: Exception) {
                _event.emit(HomeScreenEvent.Error("추천 상품을 불러오는 데 실패했습니다."))
            } finally {
                _isLoading.value = false
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
        val query = searchQuery.value
        if (query.length < MIN_SEARCH_QUERY_LENGTH) {
            viewModelScope.launch {
                _event.emit(HomeScreenEvent.Error("검색어는 ${MIN_SEARCH_QUERY_LENGTH}자 이상 입력해주세요."))
            }
            return
        }

        viewModelScope.launch {
            try {
                delay(500)
                val navArgs = SearchResultNavArgs(
                    source = SearchResultSource.TEXT,
                    passedQuery = query,
                    passedImagePath = null
                )
                _event.emit(HomeScreenEvent.NavigateToSearchResult(navArgs))
            } catch (e: Exception) {
                _event.emit(HomeScreenEvent.Error("검색결과가 없습니다."))
            } finally {
                _isLoading.value = false
            }
        }
    }
}