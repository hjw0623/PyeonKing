package com.hjw0623.pyeonking.product_detail.presentation

import com.hjw0623.pyeonking.product_detail.data.ProductDetailTab

sealed interface ProductDetailScreenAction {
    data class OnChangeTab(val tab: ProductDetailTab) : ProductDetailScreenAction
    data object OnWriteReviewClick : ProductDetailScreenAction
}