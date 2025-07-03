package com.hjw0623.presentation.screen.home

import com.hjw0623.core.domain.product.Product
import com.hjw0623.core.mockdata.mockProduct
import com.hjw0623.core.mockdata.mockProductList

data class HomeScreenState(
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val selectedProduct: Product? = mockProduct,
    val recommendList: List<Product> = mockProductList,
)