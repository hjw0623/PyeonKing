package com.hjw0623.pyeonking.product_detail.presentation

import com.hjw0623.pyeonking.core.data.Product

sealed interface ProductDetailScreenEvent {
    data class Error(val error: String) : ProductDetailScreenEvent
    data class NavigateToReviewWrite(val product: Product) : ProductDetailScreenEvent
}