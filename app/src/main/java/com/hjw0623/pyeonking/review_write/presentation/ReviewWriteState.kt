package com.hjw0623.pyeonking.review_write.presentation

import com.hjw0623.pyeonking.core.data.Product

data class ReviewWriteState(
    val product: Product,
    val rating: Int = 0,
    val content: String = "",
)


