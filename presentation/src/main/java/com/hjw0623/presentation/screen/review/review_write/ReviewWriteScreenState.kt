package com.hjw0623.presentation.screen.review.review_write

import com.hjw0623.core.domain.product.Product

data class ReviewWriteScreenState(
    val product: Product,
    val rating: Int = 0,
    val content: String = "",
)


