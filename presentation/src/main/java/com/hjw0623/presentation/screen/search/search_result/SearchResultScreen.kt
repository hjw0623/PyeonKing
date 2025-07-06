package com.hjw0623.presentation.screen.search.search_result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hjw0623.core.domain.product.Product
import com.hjw0623.core.domain.search.search_result.SearchResultNavArgs
import com.hjw0623.core.domain.search.search_result.SearchResultSource
import com.hjw0623.core.mockdata.mockProductList
import com.hjw0623.core.presentation.designsystem.components.showToast
import com.hjw0623.core.presentation.designsystem.theme.PyeonKingTheme
import com.hjw0623.core.presentation.ui.ObserveAsEvents
import com.hjw0623.presentation.R
import com.hjw0623.presentation.screen.home.ui.component.ProductCardLarge
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

@Composable
fun SearchResultScreenRoot(
    navArgs: SearchResultNavArgs,
    onNavigateToProductDetail: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var state by remember {
        mutableStateOf(
            SearchResultScreenState(
                source = navArgs.source,
                passedQuery = navArgs.passedQuery.let { "" },
                passedImagePath = navArgs.passedImagePath
            )
        )
    }
    val eventFlow = remember { MutableSharedFlow<SearchResultScreenEvent>() }

    ObserveAsEvents(flow = eventFlow) { event ->
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
        state = state,
        onAction = { action ->
            when (action) {
                is SearchResultScreenAction.OnProductClick -> {
                    scope.launch {
                        eventFlow.emit(SearchResultScreenEvent.NavigateToProductDetail(action.product))
                    }
                }
            }
        },
        modifier = modifier
    )
}

@Composable
fun SearchResultScreen(
    state: SearchResultScreenState,
    onAction: (SearchResultScreenAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Text(
            text = when (state.source) {
                SearchResultSource.CAMERA -> ""
                SearchResultSource.TEXT -> stringResource(
                    R.string.search_result_title_with_query,
                    state.passedQuery
                )
            },
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                count = state.products.size,
                key = { state.products[it].uuid }
            ) {
                ProductCardLarge(
                    onCardClick = { onAction(SearchResultScreenAction.OnProductClick(state.products[it])) },
                    product = state.products[it]
                )
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
                source = SearchResultSource.TEXT,
                products = mockProductList,
                passedQuery = "콜라"
            ),
            onAction = {}
        )
    }
}