package com.hjw0623.presentation.screen.search.text_search.ui.component.unFocused

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hjw0623.core.domain.model.product.Product
import com.hjw0623.core.domain.model.search.text_search.FilterType
import com.hjw0623.core.android.util.mockdata.mockProductList
import com.hjw0623.core.core_ui.designsystem.theme.PyeonKingTheme

@Composable
fun ProductListSection(
    selectedFilter: Map<FilterType, Boolean>,
    products: List<Product>,
    onProductClick: (Product) -> Unit,
    onFilterToggle: (FilterType) -> Unit,
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState()
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
            onProductClick = onProductClick,
            listState = listState
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