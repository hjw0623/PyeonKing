package com.hjw0623.presentation.screen.review.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjw0623.core.domain.review.ReviewRepository
import com.hjw0623.core.domain.review.review_history.ReviewInfo
import com.hjw0623.presentation.screen.review.review_edit.ui.ReviewEditScreenEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ReviewEditViewModel(
    private val repository: ReviewRepository
): ViewModel() {

    // 원본 리뷰 정보를 저장 (내부 로직용)
    private val _originalReview = MutableStateFlow<ReviewInfo?>(null)

    // UI에 표시될 상태들
    private val _productName = MutableStateFlow("")
    val productName = _productName.asStateFlow()

    private val _productImgUrl = MutableStateFlow("")
    val productImgUrl = _productImgUrl.asStateFlow()

    private val _newContent = MutableStateFlow("")
    val newContent = _newContent.asStateFlow()

    private val _newStarRating = MutableStateFlow(0)
    val newStarRating = _newStarRating.asStateFlow()

    private val _isEditing = MutableStateFlow(false)

    // '수정 완료' 버튼 활성화 로직
    val isEditButtonEnabled = combine(
        _originalReview,
        newContent,
        newStarRating,
        _isEditing
    ) { originalReview, content, rating, isEditing ->
        originalReview != null && !isEditing &&
                // 내용이나 별점 둘 중 하나라도 바뀌었고, 내용이 비어있지 않을 때
                (originalReview.content != content || originalReview.starRating != rating) &&
                content.isNotBlank()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = false
    )

    private val _event = MutableSharedFlow<ReviewEditScreenEvent>()
    val event = _event.asSharedFlow()

    /**
     * 화면이 처음 시작될 때 원본 리뷰 데이터로 상태 초기화
     */
    fun init(reviewInfo: ReviewInfo) {
        _originalReview.value = reviewInfo
        _productName.value = reviewInfo.productName
        _productImgUrl.value = reviewInfo.productImgUrl
        _newContent.value = reviewInfo.content
        _newStarRating.value = reviewInfo.starRating
    }

    /**
     * 리뷰 내용 변경 시 호출
     */
    fun onContentChanged(content: String) {
        _newContent.value = content
    }

    /**
     * 별점 변경 시 호출
     */
    fun onStarRatingChanged(starRating: Int) {
        _newStarRating.value = starRating
    }

    /**
     * '수정 완료' 버튼 클릭 시 호출
     */
    fun onEditClick() {
        viewModelScope.launch {
            _isEditing.value = true
            try {
                delay(1000) // 수정 요청 API 호출 시뮬레이션
                // TODO: repository.updateReview(reviewId, newContent, newStarRating)
                _event.emit(ReviewEditScreenEvent.NavigateReviewHistory)
            } catch (e: Exception) {
                _event.emit(ReviewEditScreenEvent.Error("리뷰 수정에 실패했습니다."))
            } finally {
                _isEditing.value = false
            }
        }
    }
}