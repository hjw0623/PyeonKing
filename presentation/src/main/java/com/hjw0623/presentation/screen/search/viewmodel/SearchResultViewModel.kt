package com.hjw0623.presentation.screen.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjw0623.core.core_network.network.DataResourceResult
import com.hjw0623.core.core_domain.model.product.Product
import com.hjw0623.core.core_network.response.toProduct
import com.hjw0623.core.core_domain.model.search.search_result.SearchResultNavArgs
import com.hjw0623.core.core_domain.model.search.search_result.SearchResultSource
import com.hjw0623.core.core_domain.repository.SearchRepository
import com.hjw0623.core.android.constants.Error
import com.hjw0623.core.android.constants.UiText
import com.hjw0623.presentation.screen.search.search_result.ui.SearchResultScreenEvent
import com.hjw0623.presentation.screen.search.search_result.ui.SearchResultScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SearchResultScreenState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<SearchResultScreenEvent>()
    val event = _event.asSharedFlow()

    fun searchProducts(navArgs: SearchResultNavArgs) {
        viewModelScope.launch {
            _state.update {
                when (navArgs.source) {
                    SearchResultSource.TEXT -> it.copy(
                        isLoading = false,
                        source = SearchResultSource.TEXT,
                        query = navArgs.passedQuery,
                        imagePath = null,
                        searchTitle = "'${navArgs.passedQuery.orEmpty()}' ${UiText.TITLE_TEXT_SEARCH}",
                        products = emptyList()
                    )

                    SearchResultSource.CAMERA -> it.copy(
                        isLoading = false,
                        source = SearchResultSource.CAMERA,
                        query = null,
                        imagePath = navArgs.passedImagePath,
                        searchTitle = UiText.TITLE_CAMERA_SEARCH,
                        products = emptyList()
                    )
                }
            }

            val currentState = _state.value

            when (currentState.source) {
                SearchResultSource.TEXT -> {
                    searchRepository.searchItems(navArgs.passedQuery!!).collectLatest { result ->
                        when (result) {
                            DataResourceResult.Loading -> {
                                _state.update { it.copy(isLoading = true) }
                            }

                            is DataResourceResult.Success -> {
                                val items = result.data.data.searchItems

                                if (items.isEmpty()) {
                                    _state.update {
                                        it.copy(
                                            isLoading = false,
                                            products = emptyList()
                                        )
                                    }
                                    _event.emit(SearchResultScreenEvent.Error(Error.NO_SEARCH_RESULT))
                                    return@collectLatest
                                }
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        products = items.map { dto -> dto.toProduct() }
                                    )
                                }
                            }

                            is DataResourceResult.Failure -> {
                                _state.update { it.copy(isLoading = false) }
                                val message = result.exception.message.toString()
                                _event.emit(SearchResultScreenEvent.Error(message))
                            }

                            else -> Unit
                        }
                    }
                }

                SearchResultSource.CAMERA -> {
                    val path = currentState.imagePath
                    if (path.isNullOrBlank()) {
                        _state.update { it.copy(isLoading = false) }
                        _event.emit(SearchResultScreenEvent.Error(Error.NO_IMAGE))
                        return@launch
                    }

                    val file = File(path)

                    searchRepository.searchItemsByImg(file).collectLatest { result ->
                        when (result) {
                            DataResourceResult.Loading -> {
                                _state.update { it.copy(isLoading = true) }
                            }

                            is DataResourceResult.Success -> {
                                val items = result.data.data.searchItems
                                if (items.isEmpty()) {
                                    _state.update {
                                        it.copy(
                                            isLoading = false,
                                            products = emptyList()
                                        )
                                    }
                                    _event.emit(SearchResultScreenEvent.Error(Error.NO_SEARCH_RESULT))
                                    return@collectLatest
                                }
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        products = items.map { dto -> dto.toProduct() }
                                    )
                                }
                            }

                            is DataResourceResult.Failure -> {
                                _state.update { it.copy(isLoading = false) }
                                val message = result.exception.message.toString()
                                _event.emit(
                                    SearchResultScreenEvent.Error(message)
                                )
                            }

                            else -> Unit
                        }
                    }
                }
            }
        }
    }

    fun onProductClick(product: Product) {
        viewModelScope.launch {
            _event.emit(SearchResultScreenEvent.NavigateToProductDetail(product))
        }
    }
}