package com.hjw0623.presentation.screen.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjw0623.core.core_network.network.DataResourceResult
import com.hjw0623.core.core_domain.model.product.Product
import com.hjw0623.core.core_domain.model.response.toProduct
import com.hjw0623.core.core_domain.model.search.search_result.SearchResultNavArgs
import com.hjw0623.core.core_domain.model.search.search_result.SearchResultSource
import com.hjw0623.core.core_domain.repository.ProductRepository
import com.hjw0623.core.core_domain.repository.UserDataStoreRepository
import com.hjw0623.core.core_andriod.constants.Error.BLANK_INPUT
import com.hjw0623.core.core_andriod.constants.Error.TOO_SHORT_SEARCH_QUERY_INPUT
import com.hjw0623.core.core_andriod.constants.Error.UNKNOWN_ERROR
import com.hjw0623.presentation.screen.home.ui.HomeScreenEvent
import com.hjw0623.presentation.screen.home.ui.HomeScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val MIN_SEARCH_QUERY_LENGTH = 2

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val userDataStoreRepository: UserDataStoreRepository
) : ViewModel() {


    private val _state = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<HomeScreenEvent>()
    val event = _event.asSharedFlow()

    init {
        viewModelScope.launch {
            userDataStoreRepository.isLoggedInFlow.collectLatest { loggedIn ->
                _state.update { it.copy(isLoggedIn = loggedIn) }
            }
        }
    }

    fun fetchRecommendList() {
        viewModelScope.launch {
            productRepository.getRecommendList().collectLatest { result ->
                _state.update {
                    when (result) {
                        is DataResourceResult.Loading -> it.copy(isLoading = true)

                        is DataResourceResult.Success -> {
                            val products = result.data.data.map { item -> item.toProduct() }

                            if (products.isEmpty()) {
                                _event.emit(HomeScreenEvent.Error("추천 상품이 없습니다"))
                            }
                            it.copy(
                                recommendList = products,
                                isLoading = false,
                                hasFetchedRecommendList = true
                            )
                        }

                        is DataResourceResult.Failure -> {
                            val message = result.exception.message ?: UNKNOWN_ERROR
                            _event.emit(HomeScreenEvent.Error(message))
                            it.copy(
                                isLoading = false,
                                hasFetchedRecommendList = true
                            )
                        }

                        is DataResourceResult.DummyConstructor -> it
                    }
                }
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _state.update { it.copy(searchQuery = query) }
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
        val query = state.value.searchQuery.trim()

        when {
            query.isBlank() -> {
                viewModelScope.launch {
                    _event.emit(HomeScreenEvent.Error(BLANK_INPUT))
                }
            }

            query.length < MIN_SEARCH_QUERY_LENGTH -> {
                viewModelScope.launch {
                    _event.emit(HomeScreenEvent.Error(TOO_SHORT_SEARCH_QUERY_INPUT))
                }
            }

            else -> {
                viewModelScope.launch {
                    val navArgs = SearchResultNavArgs(
                        source = SearchResultSource.TEXT,
                        passedQuery = query,
                        passedImagePath = null
                    )
                    _event.emit(HomeScreenEvent.NavigateToSearchResult(navArgs))
                    clearSearchQuery()
                }
            }
        }
    }

    fun clearSearchQuery() {
        _state.update { it.copy(searchQuery = "") }
    }
}