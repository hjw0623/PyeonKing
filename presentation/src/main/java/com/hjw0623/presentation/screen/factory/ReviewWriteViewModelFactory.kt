package com.hjw0623.presentation.screen.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hjw0623.presentation.screen.review.viewmodel.ReviewWriteViewModel

class ReviewWriteViewModelFactory(): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReviewWriteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReviewWriteViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}