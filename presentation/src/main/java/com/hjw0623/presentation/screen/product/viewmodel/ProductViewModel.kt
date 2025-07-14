package com.hjw0623.presentation.screen.product.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjw0623.core.domain.product.Product
import com.hjw0623.core.domain.product.ProductDetailTab
import com.hjw0623.core.domain.product.ReviewItem
import com.hjw0623.core.util.mockdata.mockReviewList
import com.hjw0623.presentation.screen.product.ui.ProductDetailScreenEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.roundToInt

class ProductViewModel(
    // private val productRepository: ProductRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _product = MutableStateFlow<Product?>(null)
    val product = _product.asStateFlow()

    private val _selectedTab = MutableStateFlow(ProductDetailTab.REVIEW)
    val selectedTab = _selectedTab.asStateFlow()

    private val _reviewList = MutableStateFlow<List<ReviewItem>>(emptyList())
    val reviewList = _reviewList.asStateFlow()

    private val _ratingList = MutableStateFlow<List<Int>>(List(5) { 0 })
    val ratingList = _ratingList.asStateFlow()

    private val _avgRating = MutableStateFlow(0.0)
    val avgRating = _avgRating.asStateFlow()

    private val _reviewSum = MutableStateFlow(0)
    val reviewSum = _reviewSum.asStateFlow()

    private val _event = MutableSharedFlow<ProductDetailScreenEvent>()
    val event = _event.asSharedFlow()

    fun loadProductDetails(product: Product) {
        _product.value = product
        fetchReviewData(product.name)
    }

    fun changeTab(tab: ProductDetailTab) {
        _selectedTab.value = tab
    }

    fun onWriteReviewClick() {
        viewModelScope.launch {
            _product.value?.let { product ->
                _event.emit(ProductDetailScreenEvent.NavigateToReviewWrite(product))
            }
        }
    }

    private fun fetchReviewData(product: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                delay(1000)
                val reviews = mockReviewList

                _reviewList.value = reviews
                _reviewSum.value = reviews.size

                val avg = if (reviews.isNotEmpty()) reviews.map { it.rating.toDouble() }.average() else 0.0
                val df = DecimalFormat("#.#").apply { roundingMode = RoundingMode.HALF_UP }
                _avgRating.value = df.format(avg).toDouble()

                _ratingList.value = List(5) { index ->
                    reviews.count { it.rating.roundToInt() == (5 - index) }
                }

            } catch (e: Exception) {
                _event.emit(ProductDetailScreenEvent.Error("리뷰를 불러오는 데 실패했습니다."))
            } finally {
                _isLoading.value = false
            }
        }
    }
}