package com.hjw0623.presentation.screen.review.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjw0623.core.business_logic.model.network.DataResourceResult
import com.hjw0623.core.business_logic.model.product.Product
import com.hjw0623.core.business_logic.model.request.ReviewPostBody
import com.hjw0623.core.business_logic.repository.ReviewRepository
import com.hjw0623.presentation.screen.review.review_write.ui.ReviewWriteScreenEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ReviewWriteViewModel(
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    private val _product = MutableStateFlow<Product?>(null)
    val product = _product.asStateFlow()

    private val _rating = MutableStateFlow(0)
    val rating = _rating.asStateFlow()

    private val _content = MutableStateFlow("")
    val content = _content.asStateFlow()

    private val _isSubmitting = MutableStateFlow(false)
    val isSubmitting = _isSubmitting.asStateFlow()

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

    fun init(product: Product) {
        _product.value = product
    }

    fun onRatingChange(newRating: Int) {
        _rating.value = newRating
    }

    fun onContentChange(newContent: String) {
        _content.value = newContent
    }

    fun onSubmitClick() {
        viewModelScope.launch {
            val reviewPostBody = ReviewPostBody(
                promotionId = _product.value!!.id.toLong(),
                star = _rating.value,
                content = _content.value
            )
            reviewRepository.postReview(reviewPostBody).collectLatest { result ->
                when (result) {
                    DataResourceResult.Loading -> {
                        _isSubmitting.value = true
                    }

                    is DataResourceResult.Success -> {
                        _isSubmitting.value = false
                        val message = result.data.message
                        _event.emit(ReviewWriteScreenEvent.NavigateBackToProductDetail(message))
                    }

                    is DataResourceResult.Failure -> {
                        _isSubmitting.value = false
                        val message = result.exception.message.toString()
                        _event.emit(ReviewWriteScreenEvent.Error(message))
                    }

                    else -> Unit
                }
            }
        }
    }
}