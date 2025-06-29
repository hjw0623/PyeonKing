package com.hjw0623.pyeonking.review.review_write.presentation

import com.hjw0623.pyeonking.core.data.Product

data class ReviewWriteScreenState(
    val product: Product,
    val rating: Int = 0,
    val content: String = "",
)


