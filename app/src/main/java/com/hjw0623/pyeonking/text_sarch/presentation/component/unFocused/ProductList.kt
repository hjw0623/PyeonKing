package com.hjw0623.pyeonking.text_sarch.presentation.component.unFocused

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hjw0623.pyeonking.core.data.Product
import com.hjw0623.pyeonking.core.data.mockProductList
import com.hjw0623.pyeonking.ui.theme.PyeonKingTheme

@Composable
fun ProductList(
    products: List<Product>,
    modifier: Modifier = Modifier,
    onProductClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        items(
            items = products,
            key = { it.uuid }
        ) {
            ProductCardSmall(
                onClick = { onProductClick(it.name) },
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
            onProductClick = {}
        )
    }
}