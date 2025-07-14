package com.hjw0623.presentation.screen.review.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjw0623.core.domain.review.review_history.ReviewInfo
import com.hjw0623.core.util.mockdata.mockReviewHistoryList
import com.hjw0623.presentation.screen.review.review_history.ui.ReviewHistoryScreenEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ReviewHistoryViewModel(
    // private val repository: ReviewRepository
): ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _reviewHistoryList = MutableStateFlow<List<ReviewInfo>>(emptyList())
    val reviewHistoryList = _reviewHistoryList.asStateFlow()

    private val _event = MutableSharedFlow<ReviewHistoryScreenEvent>()
    val event = _event.asSharedFlow()

    init {
        fetchReviewHistory()
    }

    private fun fetchReviewHistory() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                delay(1000)
                _reviewHistoryList.value = mockReviewHistoryList
            } catch (e: Exception) {
                _event.emit(ReviewHistoryScreenEvent.Error("리뷰 내역을 불러오는 데 실패했습니다."))
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onEditReviewClick(reviewInfo: ReviewInfo) {
        viewModelScope.launch {
            _event.emit(ReviewHistoryScreenEvent.NavigateToReviewEdit(reviewInfo))
        }
    }
}