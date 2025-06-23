package com.hjw0623.pyeonking.search_result.presentation

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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hjw0623.pyeonking.R
import com.hjw0623.pyeonking.core.data.mockProductList
import com.hjw0623.pyeonking.core.presentation.designsystem.util.BackBar
import com.hjw0623.pyeonking.home.presentation.component.ProductCardLarge
import com.hjw0623.pyeonking.search_result.data.SearchResultSource
import com.hjw0623.pyeonking.ui.theme.PyeonKingTheme

@Composable
fun SearchResultScreenRoot(
    modifier: Modifier = Modifier,
) {
    var state by remember { mutableStateOf(SearchResultState()) }


    SearchResultScreen(
        state = state,
        onAction = { action ->
            when (action) {
                SearchResultAction.OnBackClick -> {

                }

                is SearchResultAction.OnProductClick -> {

                }
            }
        },
        modifier = modifier
    )
}

@Composable
fun SearchResultScreen(
    state: SearchResultState,
    onAction: (SearchResultAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        BackBar(
            onBackClick = { onAction(SearchResultAction.OnBackClick) },
            title = when (state.source) {
                SearchResultSource.CAMERA -> stringResource(R.string.camera_search_result)
                SearchResultSource.TEXT -> stringResource(R.string.search_result)
            }
        )

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
                    onCardClick = { onAction(SearchResultAction.OnProductClick(state.products[it])) },
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
            state = SearchResultState(
                source = SearchResultSource.TEXT,
                products = mockProductList,
                passedQuery = "콜라"
            ),
            onAction = {}
        )
    }
}