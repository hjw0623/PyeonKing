package com.hjw0623.presentation.screen.review.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjw0623.core.data.model.toReviewInfo
import com.hjw0623.core.domain.review.ReviewRepository
import com.hjw0623.core.domain.review.review_history.ReviewInfo
import com.hjw0623.core.network.DataResourceResult
import com.hjw0623.presentation.screen.review.review_history.ui.ReviewHistoryScreenEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ReviewHistoryViewModel(
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _reviewHistoryList = MutableStateFlow<List<ReviewInfo>>(emptyList())
    val reviewHistoryList = _reviewHistoryList.asStateFlow()

    private val _currentPage = MutableStateFlow(1)
    val currentPage = _currentPage.asStateFlow()

    private val _lastPage = MutableStateFlow(1)
    val lastPage = _lastPage.asStateFlow()

    private val _event = MutableSharedFlow<ReviewHistoryScreenEvent>()
    val event = _event.asSharedFlow()

    init {
        fetchReviewHistory()
    }

    fun fetchReviewHistory(page: Int = 1) {
        viewModelScope.launch {
            _isLoading.value = true
            reviewRepository.getReviewByUserId(page = page).collectLatest { result ->
                when (result) {
                    DataResourceResult.Loading -> _isLoading.value = true

                    is DataResourceResult.Success -> {
                        _isLoading.value = false

                        val newList = result.data.data.content.map { it.toReviewInfo() }

                        if (page == 1) {
                            _reviewHistoryList.value = newList
                        } else {
                            _reviewHistoryList.value += newList
                        }

                        _currentPage.value = page
                        _lastPage.value = result.data.data.totalPages
                    }

                    is DataResourceResult.Failure -> {
                        val message = result.exception.message.toString()
                        _event.emit(ReviewHistoryScreenEvent.Error(message))
                        _isLoading.value = false
                    }

                    else -> Unit
                }
            }
        }
    }

    fun fetchNextReviewPage() {
        val nextPage = _currentPage.value + 1
        if (nextPage <= _lastPage.value) {
            fetchReviewHistory(nextPage)
        }
    }


    fun onEditReviewClick(reviewInfo: ReviewInfo) {
        viewModelScope.launch {
            _event.emit(ReviewHistoryScreenEvent.NavigateToReviewEdit(reviewInfo))
        }
    }
}