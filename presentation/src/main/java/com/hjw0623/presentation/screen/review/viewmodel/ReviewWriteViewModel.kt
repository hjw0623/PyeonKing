package com.hjw0623.presentation.screen.review.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjw0623.core.domain.product.Product
import com.hjw0623.presentation.screen.review.review_write.ui.ReviewWriteScreenEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ReviewWriteViewModel(
    // private val reviewRepository: ReviewRepository
): ViewModel() {

    private val _product = MutableStateFlow<Product?>(null)
    val product = _product.asStateFlow()

    private val _rating = MutableStateFlow(0)
    val rating = _rating.asStateFlow()

    private val _content = MutableStateFlow("")
    val content = _content.asStateFlow()

    private val _isSubmitting = MutableStateFlow(false)

    val isSubmitButtonEnabled = combine(
        rating,
        content,
        _isSubmitting
    ) { rating, content, isSubmitting ->
        rating > 0 && content.isNotBlank() && !isSubmitting
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = false
    )

    private val _event = MutableSharedFlow<ReviewWriteScreenEvent>()
    val event = _event.asSharedFlow()

    /**
     * 화면이 처음 시작될 때 상품 정보로 상태 초기화
     */
    fun init(product: Product) {
        _product.value = product
    }

    /**
     * 별점 변경 시 호출
     */
    fun onRatingChange(newRating: Int) {
        _rating.value = newRating
    }

    /**
     * 리뷰 내용 변경 시 호출
     */
    fun onContentChange(newContent: String) {
        _content.value = newContent
    }

    /**
     * '제출하기' 버튼 클릭 시 호출
     */
    fun onSubmitClick() {
        viewModelScope.launch {
            _isSubmitting.value = true
            try {
                delay(1500) // 리뷰 제출 API 호출 시뮬레이션
                // TODO: repository.submitReview(productId, rating.value, content.value)
                _event.emit(ReviewWriteScreenEvent.NavigateBackToProductDetail)
            } catch (e: Exception) {
                _event.emit(ReviewWriteScreenEvent.Error("리뷰 제출에 실패했습니다."))
            } finally {
                _isSubmitting.value = false
            }
        }
    }
}