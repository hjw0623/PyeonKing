package com.hjw0623.presentation.screen.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hjw0623.core.domain.review.ReviewRepository
import com.hjw0623.presentation.screen.review.viewmodel.ReviewWriteViewModel

class ReviewWriteViewModelFactory(
    private val reviewRepository: ReviewRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReviewWriteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReviewWriteViewModel(reviewRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}