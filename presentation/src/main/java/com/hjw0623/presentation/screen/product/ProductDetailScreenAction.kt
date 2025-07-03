package com.hjw0623.presentation.screen.product

import com.hjw0623.core.domain.product.ProductDetailTab

sealed interface ProductDetailScreenAction {
    data class OnChangeTab(val tab: ProductDetailTab) : ProductDetailScreenAction
    data object OnWriteReviewClick : ProductDetailScreenAction
}