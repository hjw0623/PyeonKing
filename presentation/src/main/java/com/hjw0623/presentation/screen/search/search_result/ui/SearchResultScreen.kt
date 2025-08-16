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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hjw0623.core.business_logic.model.product.Product
import com.hjw0623.core.business_logic.model.search.search_result.SearchResultNavArgs
import com.hjw0623.core.business_logic.model.search.search_result.SearchResultSource
import com.hjw0623.core.presentation.designsystem.components.showToast
import com.hjw0623.core.presentation.designsystem.theme.PyeonKingTheme
import com.hjw0623.core.presentation.ui.ObserveAsEvents
import com.hjw0623.core.presentation.ui.rememberThrottledOnClick
import com.hjw0623.presentation.R
import com.hjw0623.presentation.screen.search.search_result.ui.component.NoSearchResult
import com.hjw0623.presentation.screen.search.text_search.ui.component.unFocused.ProductCardSmall
import com.hjw0623.presentation.screen.search.viewmodel.SearchResultViewModel

@Composable
fun SearchResultScreenRoot(
    navArgs: SearchResultNavArgs,
    searchResultViewModel: SearchResultViewModel,
    onNavigateToProductDetail: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val state by searchResultViewModel.state.collectAsStateWithLifecycle()

    val throttledProductClick = rememberThrottledOnClick<Product> { product ->
        searchResultViewModel.onProductClick(product)
    }

    LaunchedEffect(key1 = navArgs) {
        searchResultViewModel.searchProducts(navArgs)
    }

    ObserveAsEvents(flow = searchResultViewModel.event) { event ->
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
        state = state,
        onProductClick = throttledProductClick
    )
}

@Composable
fun SearchResultScreen(
    modifier: Modifier = Modifier,
    state: SearchResultScreenState,
    onProductClick: (Product) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            text = state.searchTitle,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) { CircularProgressIndicator() }
            }

            state.products.isEmpty() -> {
                val (title, message, tips) =
                    when (state.source) {
                        SearchResultSource.TEXT -> {
                            val title = stringResource(R.string.text_search_empty_title)
                            val message = stringResource(R.string.text_search_empty_message)
                            val tipsList = listOf(
                                stringResource(R.string.text_search_empty_tip_typo),
                                stringResource(R.string.text_search_empty_tip_length)
                            )
                            Triple(title, message, tipsList)
                        }

                        SearchResultSource.CAMERA -> {
                            val title = stringResource(R.string.camera_search_empty_title)
                            val message = stringResource(R.string.camera_search_empty_message)
                            val tipsList = listOf(
                                stringResource(R.string.camera_search_empty_tip_light),
                                stringResource(R.string.camera_search_empty_tip_frame),
                                stringResource(R.string.camera_search_empty_tip_steady)
                            )
                            Triple(title, message, tipsList)
                        }
                    }

                NoSearchResult(
                    title = title,
                    message = message,
                    tips = tips,
                    modifier = Modifier.fillMaxSize()
                )
            }

            else -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(items = state.products, key = { it.id }) { product ->
                        ProductCardSmall(
                            onClick = { onProductClick(product) },
                            product = product
                        )
                    }
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
            state = SearchResultScreenState(
                isLoading = false,
                products = emptyList(),
                searchTitle = "'콜라' 검색 결과",
                source = SearchResultSource.TEXT,
                query = "콜라"
            ),
            onProductClick = {}
        )
    }
}