package com.hjw0623.presentation.screen.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hjw0623.presentation.screen.product.viewmodel.ProductViewModel

class ProductViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}