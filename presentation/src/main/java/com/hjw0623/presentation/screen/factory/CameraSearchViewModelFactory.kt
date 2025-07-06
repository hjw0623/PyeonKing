package com.hjw0623.presentation.screen.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hjw0623.presentation.screen.search.viewmodel.CameraSearchViewModel

class CameraSearchViewModelFactory(): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CameraSearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CameraSearchViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}