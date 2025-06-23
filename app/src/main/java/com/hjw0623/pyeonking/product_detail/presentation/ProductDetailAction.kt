package com.hjw0623.pyeonking.product_detail.presentation

import com.hjw0623.pyeonking.product_detail.data.ProductDetailTab

sealed interface ProductDetailAction {
    data class OnChangeTab(val tab: ProductDetailTab) : ProductDetailAction
    object OnWriteReviewClick : ProductDetailAction
}