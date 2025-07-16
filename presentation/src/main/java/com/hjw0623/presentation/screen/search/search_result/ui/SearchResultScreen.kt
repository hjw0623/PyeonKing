package com.hjw0623.presentation.screen.search.search_result.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hjw0623.core.domain.product.Product
import com.hjw0623.core.domain.search.search_result.SearchResultNavArgs
import com.hjw0623.core.util.mockdata.mockProductList
import com.hjw0623.core.presentation.designsystem.components.showToast
import com.hjw0623.core.presentation.designsystem.theme.PyeonKingTheme
import com.hjw0623.core.presentation.ui.ObserveAsEvents
import com.hjw0623.core.presentation.ui.rememberThrottledOnClick
import com.hjw0623.presentation.screen.factory.SearchResultViewModelFactory
import com.hjw0623.presentation.screen.home.ui.component.ProductCardLarge
import com.hjw0623.presentation.screen.search.viewmodel.SearchResultViewModel

@Composable
fun SearchResultScreenRoot(
    navArgs: SearchResultNavArgs,
    onNavigateToProductDetail: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val searchResultViewModelFactory = SearchResultViewModelFactory()
    val viewModel: SearchResultViewModel = viewModel(factory = searchResultViewModelFactory)

    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val products by viewModel.products.collectAsStateWithLifecycle()
    val searchTitle by viewModel.searchTitle.collectAsStateWithLifecycle()

    val throttledProductClick = rememberThrottledOnClick<Product> { product ->
        viewModel.onProductClick(product)
    }
    
    LaunchedEffect(key1 = navArgs) {
        viewModel.searchProducts(navArgs)
    }

    ObserveAsEvents(flow = viewModel.event) { event ->
        when (event) {
            is SearchResultScreenEvent.Error -> {
                showToast(context, event.error)
            }

            is SearchResultScreenEvent.NavigateToProductDetail -> {
                onNavigateToProductDetail(event.product)
            }
        }
    }

    SearchResultScreen(
        modifier = modifier,
        isLoading = isLoading,
        products = products,
        searchTitle = searchTitle,
        onProductClick = throttledProductClick
    )
}

@Composable
fun SearchResultScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    products: List<Product>,
    searchTitle: String,
    onProductClick: (Product) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            text = searchTitle,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    items = products,
                    key = { it.id }
                ) { product ->
                    ProductCardLarge(
                        onCardClick = { onProductClick(product) },
                        product = product
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun SearchResultScreenPreview() {
    PyeonKingTheme {
        SearchResultScreen(
            isLoading = false,
            products = mockProductList,
            searchTitle = "'콜라' 검색 결과",
            onProductClick = {}
        )
    }
}