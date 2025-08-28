package com.hjw0623.presentation.screen.product.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjw0623.core.core_domain.model.network.DataResourceResult
import com.hjw0623.core.core_domain.model.product.Product
import com.hjw0623.core.core_domain.model.product.ProductDetailTab
import com.hjw0623.core.core_domain.model.response.toReviewItem
import com.hjw0623.core.core_domain.repository.ProductRepository
import com.hjw0623.core.core_domain.repository.UserDataStoreRepository
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
    private val userDataStoreRepository: UserDataStoreRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ProductDetailScreenState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<ProductDetailScreenEvent>()
    val event = _event.asSharedFlow()

    init {
        viewModelScope.launch {
            userDataStoreRepository.isLoggedInFlow.collectLatest { isLoggedIn ->
                _state.update { it.copy(isLoggedIn = isLoggedIn) }

                val product = _state.value.product
                if (isLoggedIn && product != null) {
                    product.id.toLongOrNull()?.let { id ->
                        fetchReviewSummary(id)
                        fetchInitReview(id)
                    }
                }
            }
        }
    }

    fun initializeIfNeeded(product: Product) {
        val alreadyInitialized =
            _state.value.product?.id == product.id && _state.value.product != null
        if (alreadyInitialized) return

        _state.update {
            ProductDetailScreenState(
                product = product,
                isSummaryLoading = true,
                isReviewLoading = true,
                isLoggedIn = _state.value.isLoggedIn
            )
        }

    }

    fun refreshReviews(product: Product) {
        viewModelScope.launch {
            if (state.value.isLoggedIn) {
                product.id.toLongOrNull()?.let { id ->
                    fetchReviewSummary(id)
                    fetchInitReview(id)
                }
            }
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

    fun fetchReviewSummary(promotionId: Long) {
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

    fun fetchInitReview(promotionId: Long) {
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