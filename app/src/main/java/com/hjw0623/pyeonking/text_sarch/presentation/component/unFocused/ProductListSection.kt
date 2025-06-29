package com.hjw0623.pyeonking.text_sarch.presentation.component.unFocused

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hjw0623.pyeonking.core.data.Product
import com.hjw0623.pyeonking.core.data.mockProductList
import com.hjw0623.pyeonking.text_sarch.data.FilterType
import com.hjw0623.pyeonking.ui.theme.PyeonKingTheme

@Composable
fun ProductListSection(
    selectedFilter: Map<FilterType, Boolean>,
    products: List<Product>,
    onProductClick: (Product) -> Unit,
    onFilterToggle: (FilterType) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        CategoryBar(
            selectedFilters = selectedFilter,
            onFilterToggle = onFilterToggle
        )
        ProductList(
            products = products,
            onProductClick = onProductClick
        )
    }
}

@Preview
@Composable
private fun AllProductScreenPreview() {
    PyeonKingTheme {
        ProductListSection(
            selectedFilter = mapOf(
                FilterType.ONE_PLUS_ONE to true,
                FilterType.CU to false,
                FilterType.GS25 to false
            ),
            onFilterToggle = {},
            onProductClick = {},
            products = mockProductList
        )
    }
}