package com.hjw0623.presentation.screen.search.text_search.ui.component.unFocused

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hjw0623.core.business_logic.model.product.Product
import com.hjw0623.core.business_logic.model.search.text_search.FilterType
import com.hjw0623.core.util.mockdata.mockProductList
import com.hjw0623.core.presentation.designsystem.theme.PyeonKingTheme

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