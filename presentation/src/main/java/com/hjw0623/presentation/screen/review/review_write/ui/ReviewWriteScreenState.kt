package com.hjw0623.presentation.screen.review.review_write.ui

import com.hjw0623.core.business_logic.model.product.Product

data class ReviewWriteScreenState(
    val product: Product? = null,
    val rating: Int = 0,
    val content: String = "",
    val isSubmitting: Boolean = false,
) {
    val isSubmitButtonEnabled: Boolean
        get() = !isSubmitting && rating > 0 && content.isNotBlank() && product != null
}