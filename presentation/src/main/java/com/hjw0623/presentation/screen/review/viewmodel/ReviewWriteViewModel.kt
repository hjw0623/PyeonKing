package com.hjw0623.presentation.screen.review.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjw0623.core.core_domain.model.network.DataResourceResult
import com.hjw0623.core.core_domain.model.product.Product
import com.hjw0623.core.core_domain.model.request.ReviewPostBody
import com.hjw0623.core.core_domain.repository.ReviewRepository
import com.hjw0623.presentation.screen.review.review_write.ui.ReviewWriteScreenEvent
import com.hjw0623.presentation.screen.review.review_write.ui.ReviewWriteScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewWriteViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ReviewWriteScreenState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<ReviewWriteScreenEvent>()
    val event = _event.asSharedFlow()

    fun init(product: Product) {
        _state.update { it.copy(product = product) }
    }

    fun onRatingChange(newRating: Int) {
        _state.update { it.copy(rating = newRating) }
    }

    fun onContentChange(newContent: String) {
        _state.update { it.copy(content = newContent) }
    }

    fun onSubmitClick() {
        val currentState = _state.value
        if (currentState.isSubmitting) return
        if (!currentState.isSubmitButtonEnabled) return

        val product = currentState.product ?: return
        val reviewPostBody = ReviewPostBody(
            promotionId = product.id.toLong(),
            star = currentState.rating,
            content = currentState.content
        )

        viewModelScope.launch {
            reviewRepository.postReview(reviewPostBody).collectLatest { result ->
                when (result) {
                    DataResourceResult.Loading -> {
                        _state.update { it.copy(isSubmitting = true) }
                    }

                    is DataResourceResult.Success -> {
                        _state.update { it.copy(isSubmitting = false) }
                        val message = result.data.message
                        _event.emit(ReviewWriteScreenEvent.NavigateBackToProductDetail(message))
                    }

                    is DataResourceResult.Failure -> {
                        _state.update { it.copy(isSubmitting = false) }
                        val message = result.exception.message.toString()
                        _event.emit(ReviewWriteScreenEvent.Error(message))
                    }

                    else -> Unit
                }
            }
        }
    }
}