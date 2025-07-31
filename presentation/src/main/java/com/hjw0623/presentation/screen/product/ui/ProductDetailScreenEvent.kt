package com.hjw0623.presentation.screen.product.ui

import com.hjw0623.core.domain.product.Product

sealed interface ProductDetailScreenEvent {
    data class Error(val error: String) : ProductDetailScreenEvent
    data class NavigateToReviewWrite(val product: Product) : ProductDetailScreenEvent
}