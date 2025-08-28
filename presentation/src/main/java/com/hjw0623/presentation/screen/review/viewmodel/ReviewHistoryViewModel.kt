package com.hjw0623.presentation.screen.review.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjw0623.core.core_domain.model.network.DataResourceResult
import com.hjw0623.core.core_domain.model.response.toReviewInfo
import com.hjw0623.core.core_domain.model.review.ReviewInfo
import com.hjw0623.core.core_domain.repository.ReviewRepository
import com.hjw0623.presentation.screen.review.review_history.ui.ReviewHistoryScreenEvent
import com.hjw0623.presentation.screen.review.review_history.ui.ReviewHistoryScreenState
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
class ReviewHistoryViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ReviewHistoryScreenState())
    val state = _state.asStateFlow()
    private val _event = MutableSharedFlow<ReviewHistoryScreenEvent>()
    val event = _event.asSharedFlow()

    init {
        fetchReviewHistory(page = 1)
    }

    fun fetchReviewHistory(page: Int) {
        val currentState = _state.value
        if (currentState.isLoading) return
        if (page != 1 && !currentState.canLoadMore) return

        viewModelScope.launch {
            reviewRepository.getReviewByUserId(page = page).collectLatest { result ->
                when (result) {
                    DataResourceResult.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }

                    is DataResourceResult.Success -> {
                        val newList = result.data.data.content.map { it.toReviewInfo() }
                        _state.update { s ->
                            s.copy(
                                isLoading = false,
                                reviews = if (page == 1) newList else s.reviews + newList,
                                currentPage = page,
                                lastPage = result.data.data.totalPages
                            )
                        }
                    }

                    is DataResourceResult.Failure -> {
                        _state.update { it.copy(isLoading = false) }
                        val message = result.exception.message.toString()
                        _event.emit(ReviewHistoryScreenEvent.Error(message))
                    }

                    else -> Unit
                }
            }
        }
    }

    fun fetchNextReviewPage() {
        val next = _state.value.currentPage + 1
        if (_state.value.canLoadMore) fetchReviewHistory(next)
    }


    fun onEditReviewClick(reviewInfo: ReviewInfo) {
        viewModelScope.launch {
            _event.emit(ReviewHistoryScreenEvent.NavigateToReviewEdit(reviewInfo))
        }
    }
}