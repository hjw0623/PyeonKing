package com.hjw0623.presentation.screen.review.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjw0623.core.data.model.UpdateReviewBody
import com.hjw0623.core.domain.review.ReviewRepository
import com.hjw0623.core.domain.review.review_history.ReviewInfo
import com.hjw0623.core.network.DataResourceResult
import com.hjw0623.presentation.screen.review.review_edit.ui.ReviewEditScreenEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ReviewEditViewModel(
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    private val _originalReview = MutableStateFlow<ReviewInfo?>(null)

    private val _productName = MutableStateFlow("")
    val productName = _productName.asStateFlow()

    private val _productImgUrl = MutableStateFlow<String?>("")
    val productImgUrl = _productImgUrl.asStateFlow()

    private val _newContent = MutableStateFlow("")
    val newContent = _newContent.asStateFlow()

    private val _newStarRating = MutableStateFlow(0)
    val newStarRating = _newStarRating.asStateFlow()

    private val _isEditing = MutableStateFlow(false)

    val isEditButtonEnabled = combine(
        _originalReview,
        newContent,
        newStarRating,
        _isEditing
    ) { originalReview, content, rating, isEditing ->
        originalReview != null && !isEditing &&
                (originalReview.content != content || originalReview.starRating != rating) &&
                content.isNotBlank()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = false
    )

    private val _event = MutableSharedFlow<ReviewEditScreenEvent>()
    val event = _event.asSharedFlow()

    fun init(reviewInfo: ReviewInfo) {
        _originalReview.value = reviewInfo
        _productName.value = reviewInfo.productName
        _productImgUrl.value = reviewInfo.productImgUrl
        _newContent.value = reviewInfo.content
        _newStarRating.value = reviewInfo.starRating
    }


    fun onContentChanged(content: String) {
        _newContent.value = content
    }

    fun onStarRatingChanged(starRating: Int) {
        _newStarRating.value = starRating
    }

    fun onEditClick() {
        viewModelScope.launch {
            val updateReviewBody = UpdateReviewBody(
                commentId = _originalReview.value!!.reviewId,
                star = _newStarRating.value,
                content = _newContent.value
            )
            reviewRepository.updateReview(updateReviewBody).collectLatest { result ->
                when (result) {
                    is DataResourceResult.Loading -> {
                        _isEditing.value = true
                    }

                    is DataResourceResult.Success -> {
                        _isEditing.value = false
                        val message = result.data.message
                        _event.emit(ReviewEditScreenEvent.NavigateReviewHistory(message))
                    }

                    is DataResourceResult.Failure -> {
                        val message = result.exception.message.toString()
                        _event.emit(ReviewEditScreenEvent.Error(message))
                        _isEditing.value = false
                    }

                    else -> Unit
                }
            }
        }
    }
}