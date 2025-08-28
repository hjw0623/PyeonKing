package com.hjw0623.presentation.screen.search.text_search.ui.component.unFocused

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hjw0623.core.core_domain.model.product.Product
import com.hjw0623.core.util.mockdata.mockProductList
import com.hjw0623.core.presentation.designsystem.theme.PyeonKingTheme

@Composable
fun ProductList(
    products: List<Product>,
    modifier: Modifier = Modifier,
    onProductClick: (Product) -> Unit,
    listState: LazyListState
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
        state = listState
    ) {
        items(
            items = products,
            key = { it.id }
        ) {
            ProductCardSmall(
                onClick = { onProductClick(it) },
                product = it
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchHistoryScreenPreview() {
    PyeonKingTheme {
        ProductList(
            products = mockProductList,
            listState = rememberLazyListState(),
            onProductClick = {}
        )
    }
}