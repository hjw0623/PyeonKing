package com.hjw0623.presentation.screen.product.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjw0623.core.business_logic.model.network.DataResourceResult
import com.hjw0623.core.business_logic.model.product.Product
import com.hjw0623.core.business_logic.model.product.ProductDetailTab
import com.hjw0623.core.business_logic.model.response.toReviewItem
import com.hjw0623.core.business_logic.repository.ProductRepository
import com.hjw0623.core.constants.Error
import com.hjw0623.presentation.screen.product.ui.ProductDetailScreenEvent
import com.hjw0623.presentation.screen.product.ui.ProductDetailScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(ProductDetailScreenState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<ProductDetailScreenEvent>()
    val event = _event.asSharedFlow()

    fun initializeIfNeeded(product: Product) {
        val alreadyInitialized = _state.value.product?.id == product.id && _state.value.product != null
        if (alreadyInitialized) return

        _state.update {
            ProductDetailScreenState(
                product = product,
                isSummaryLoading = true,
                isReviewLoading = true,
            )
        }
        product.id.toLongOrNull()?.let { id ->
            fetchReviewSummary(id)
            fetchInitReview(id)
        }
    }

    fun changeTab(tab: ProductDetailTab) {
        _state.update { it.copy(selectedTab = tab) }
    }

    fun onWriteReviewClick() {
        viewModelScope.launch {
            _state.value.product?.let {
                _event.emit(ProductDetailScreenEvent.NavigateToReviewWrite(it))
            }
        }
    }

    private fun fetchReviewSummary(promotionId: Long) {
        viewModelScope.launch {
            productRepository.getReviewSummaryByItemId(promotionId).collectLatest { result ->
                when (result) {
                    is DataResourceResult.Loading -> {
                        _state.update { it.copy(isSummaryLoading = true) }
                    }

                    is DataResourceResult.Success -> {
                        val summary = result.data.data
                        _state.update {
                            it.copy(
                                reviewSum = summary.totalCount,
                                avgRating = DecimalFormat("#.#")
                                    .apply { roundingMode = RoundingMode.HALF_UP }
                                    .format(summary.averageRating).toDouble(),
                                ratingList = List(5) { index ->
                                    val star = 5 - index
                                    summary.ratingDistribution[star]?.toInt() ?: 0
                                },
                                isSummaryLoading = false
                            )
                        }
                    }

                    is DataResourceResult.Failure -> {
                        _event.emit(ProductDetailScreenEvent.Error(result.exception.message.toString()))
                        _state.update { it.copy(isSummaryLoading = false) }
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun fetchInitReview(promotionId: Long) {
        viewModelScope.launch {
            productRepository.getReviewByItemId(promotionId, 1).collectLatest { result ->
                when (result) {
                    DataResourceResult.Loading -> {
                        _state.update { it.copy(isReviewLoading = true) }
                    }

                    is DataResourceResult.Success -> {
                        val reviewResult = result.data.data
                        _state.update {
                            it.copy(
                                lastPage = reviewResult.totalPages,
                                reviewList = reviewResult.content.map { it.toReviewItem() },
                                currentPage = 1,
                                isReviewLoading = false
                            )
                        }
                        if (reviewResult.totalPages == 0) {
                            _event.emit(ProductDetailScreenEvent.Error(Error.NO_REVIEW))
                        }
                    }

                    is DataResourceResult.Failure -> {
                        _event.emit(ProductDetailScreenEvent.Error(result.exception.message.toString()))
                        _state.update { it.copy(isReviewLoading = false) }
                    }

                    is DataResourceResult.DummyConstructor -> Unit
                }
            }
        }
    }

    fun fetchNextReviewPage(promotionId: Long) {
        viewModelScope.launch {
            val nextPage = _state.value.currentPage + 1
            if (nextPage > _state.value.lastPage) return@launch

            productRepository.getReviewByItemId(promotionId, nextPage).collectLatest { result ->
                when (result) {
                    is DataResourceResult.Loading -> {
                        _state.update { it.copy(isReviewLoading = true) }
                    }

                    is DataResourceResult.Success -> {
                        _state.update {
                            it.copy(
                                currentPage = nextPage,
                                lastPage = result.data.data.totalPages,
                                reviewList = it.reviewList + result.data.data.content.map { it.toReviewItem() },
                                isReviewLoading = false
                            )
                        }
                    }

                    is DataResourceResult.Failure -> {
                        _state.update { it.copy(isReviewLoading = false) }
                        _event.emit(ProductDetailScreenEvent.Error(result.exception.message.toString()))
                    }

                    is DataResourceResult.DummyConstructor -> Unit
                }
            }
        }
    }
}