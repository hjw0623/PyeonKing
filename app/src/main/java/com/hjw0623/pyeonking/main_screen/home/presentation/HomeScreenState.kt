package com.hjw0623.pyeonking.main_screen.home.presentation

import com.hjw0623.pyeonking.core.data.Product
import com.hjw0623.pyeonking.core.data.mockProduct
import com.hjw0623.pyeonking.core.data.mockProductList


data class HomeScreenState(
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val selectedProduct: Product? = mockProduct,
    val recommendList: List<Product> = mockProductList,
)