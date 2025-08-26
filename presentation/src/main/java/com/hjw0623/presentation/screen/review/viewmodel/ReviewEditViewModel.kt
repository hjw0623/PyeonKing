package com.hjw0623.presentation.screen.review.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjw0623.core.business_logic.model.network.DataResourceResult
import com.hjw0623.core.business_logic.model.request.UpdateReviewBody
import com.hjw0623.core.business_logic.model.review.ReviewInfo
import com.hjw0623.core.business_logic.repository.ReviewRepository
import com.hjw0623.presentation.screen.review.review_edit.ui.ReviewEditScreenEvent
import com.hjw0623.presentation.screen.review.review_edit.ui.ReviewEditScreenState
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
class ReviewEditViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ReviewEditScreenState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<ReviewEditScreenEvent>()
    val event = _event.asSharedFlow()


    fun fetchInitReview(reviewInfo: ReviewInfo) {
        _state.update {
            it.copy(
                originalReview = reviewInfo,
                productName = reviewInfo.productName,
                productImgUrl = reviewInfo.productImgUrl,
                newContent = reviewInfo.content,
                newStarRating = reviewInfo.starRating
            )
        }
    }

    fun onContentChanged(content: String) {
        _state.update { it.copy(newContent = content) }
    }

    fun onStarRatingChanged(starRating: Int) {
        _state.update { it.copy(newStarRating = starRating) }
    }

    fun onEditClick() {
        val currentState = _state.value
        val originalReview = currentState.originalReview ?: return
        viewModelScope.launch {
            _state.update {
                it.copy(isEditing = true)
            }

            val updateReviewBody = UpdateReviewBody(
                commentId = originalReview.reviewId,
                star = currentState.newStarRating,
                content = currentState.newContent
            )
            reviewRepository.updateReview(updateReviewBody).collectLatest { result ->
                when (result) {
                    is DataResourceResult.Loading -> {
                        _state.update { it.copy(isEditing = true) }
                    }

                    is DataResourceResult.Success -> {
                        _state.update { it.copy(isEditing = false) }
                        val message = result.data.message
                        _event.emit(ReviewEditScreenEvent.NavigateReviewHistory(message))
                    }

                    is DataResourceResult.Failure -> {
                        _state.update { it.copy(isEditing = false) }
                        val message = result.exception.message.toString()
                        _event.emit(ReviewEditScreenEvent.Error(message))
                    }

                    else -> Unit
                }
            }
        }
    }
}