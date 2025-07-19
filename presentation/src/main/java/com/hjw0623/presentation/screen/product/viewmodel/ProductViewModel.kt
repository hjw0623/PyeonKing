package com.hjw0623.presentation.screen.product.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjw0623.core.data.model.BaseResponse
import com.hjw0623.core.data.model.ReviewPage
import com.hjw0623.core.data.model.toReviewItem
import com.hjw0623.core.domain.product.Product
import com.hjw0623.core.domain.product.ProductDetailTab
import com.hjw0623.core.domain.product.ProductRepository
import com.hjw0623.core.domain.product.ReviewItem
import com.hjw0623.core.network.DataResourceResult
import com.hjw0623.presentation.screen.product.ui.ProductDetailScreenEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.text.DecimalFormat

class ProductViewModel(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _product = MutableStateFlow<Product?>(null)
    val product = _product.asStateFlow()

    private val _selectedTab = MutableStateFlow(ProductDetailTab.REVIEW)
    val selectedTab = _selectedTab.asStateFlow()

    private val _reviewList = MutableStateFlow<DataResourceResult<BaseResponse<ReviewPage>>>(
        DataResourceResult.DummyConstructor
    )
    val reviewList = _reviewList.asStateFlow()

    private val _reviewListUi = MutableStateFlow<List<ReviewItem>>(emptyList())
    val reviewListUi = _reviewListUi.asStateFlow()

    private val _ratingList = MutableStateFlow(List(5) { 0 })
    val ratingList = _ratingList.asStateFlow()

    private val _avgRating = MutableStateFlow(0.0)
    val avgRating = _avgRating.asStateFlow()

    private val _reviewSum = MutableStateFlow(0)
    val reviewSum = _reviewSum.asStateFlow()

    private val _currentPage = MutableStateFlow(1)
    val currentPage = _currentPage.asStateFlow()

    private val _lastPage = MutableStateFlow(0)
    val lastPage = _lastPage.asStateFlow()

    private val _event = MutableSharedFlow<ProductDetailScreenEvent>()
    val event = _event.asSharedFlow()

    fun initializeIfNeeded(product: Product) {
        if (_product.value?.id == product.id && _product.value != null) return

        _product.value = product
        _currentPage.value = 1
        _lastPage.value = 0
        _reviewList.value = DataResourceResult.DummyConstructor
        _reviewListUi.value = emptyList()
        _ratingList.value = List(5) { 0 }
        _avgRating.value = 0.0
        _reviewSum.value = 0

        fetchReviewSummary(product.id.toLong())
        fetchInitReview(product.id.toLong(), 1)
    }

    fun changeTab(tab: ProductDetailTab) {
        _selectedTab.value = tab
    }

    fun onWriteReviewClick() {
        viewModelScope.launch {
            _product.value?.let {
                _event.emit(ProductDetailScreenEvent.NavigateToReviewWrite(it))
            }
        }
    }

    private fun fetchReviewSummary(promotionId: Long) {
        viewModelScope.launch {
            productRepository.getReviewSummaryByItemId(promotionId).collectLatest { result ->
                when (result) {
                    is DataResourceResult.Loading -> {
                        _isLoading.value = true
                    }

                    is DataResourceResult.Success -> {
                        val summary = result.data.data
                        _reviewSum.value = summary.totalCount
                        _avgRating.value =
                            DecimalFormat("#.#").apply { roundingMode = RoundingMode.HALF_UP }
                                .format(summary.averageRating).toDouble()

                        _ratingList.value = List(5) { index ->
                            val star = 5 - index
                            summary.ratingDistribution[star]?.toInt() ?: 0
                        }
                        _isLoading.value = false
                    }

                    is DataResourceResult.Failure -> {
                        val message = result.exception.message.toString()
                        _event.emit(ProductDetailScreenEvent.Error(message))
                        _isLoading.value = true
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun fetchInitReview(promotionId: Long, page: Int) {
        viewModelScope.launch {
            productRepository.getReviewByItemId(promotionId, page).collectLatest { result ->
                when (result) {
                    DataResourceResult.Loading -> {
                        _isLoading.value = true
                    }

                    is DataResourceResult.Success -> {
                        _lastPage.value = result.data.data.totalPages
                        _reviewList.value = result
                        _reviewListUi.value = result.data.data.content.map { it.toReviewItem() }
                        _isLoading.value = false
                    }

                    is DataResourceResult.Failure -> {
                        val message = result.exception.message.toString()
                        _event.emit(ProductDetailScreenEvent.Error(message))
                        _isLoading.value = false
                    }

                    else -> Unit
                }
            }
        }
    }

    fun fetchNextReviewPage(promotionId: Long) {
        viewModelScope.launch {
            val nextPage = _currentPage.value + 1
            if (nextPage > _lastPage.value) return@launch

            productRepository.getReviewByItemId(promotionId, nextPage).collectLatest { result ->
                when (result) {

                    is DataResourceResult.Success -> {
                        _currentPage.value = nextPage
                        _lastPage.value = result.data.data.totalPages
                        _reviewListUi.value =
                            _reviewListUi.value + result.data.data.content.map { it.toReviewItem() }
                    }

                    is DataResourceResult.Failure -> {
                        val message = result.exception.message.toString()
                        _event.emit(ProductDetailScreenEvent.Error(message))
                    }

                    else -> Unit
                }
            }
        }
    }
}