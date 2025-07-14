package com.hjw0623.presentation.screen.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hjw0623.presentation.screen.review.viewmodel.ReviewHistoryViewModel

class ReviewHistoryViewModelFactory(): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReviewHistoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReviewHistoryViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}