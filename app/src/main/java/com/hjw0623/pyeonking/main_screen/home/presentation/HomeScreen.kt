package com.hjw0623.pyeonking.main_screen.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hjw0623.pyeonking.core.data.Product
import com.hjw0623.pyeonking.core.data.SearchResultNavArgs
import com.hjw0623.pyeonking.core.presentation.designsystem.util.showToast
import com.hjw0623.pyeonking.core.util.ObserveAsEvents
import com.hjw0623.pyeonking.main_screen.home.presentation.component.RecommendSection
import com.hjw0623.pyeonking.main_screen.home.presentation.component.TopHeader
import com.hjw0623.pyeonking.search_result.data.SearchResultSource
import com.hjw0623.pyeonking.core.presentation.ui.theme.PyeonKingTheme
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

@Composable
fun HomeScreenRoot(
    modifier: Modifier = Modifier,
    onNavigateToProductDetail: (Product) -> Unit,
    onNavigateToSearchResult: (SearchResultNavArgs) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var state by remember { mutableStateOf(HomeScreenState()) }
    val eventFlow = remember { MutableSharedFlow<HomeScreenEvent>() }

    ObserveAsEvents(flow = eventFlow) { event ->
        when (event) {
            is HomeScreenEvent.Error -> {
                showToast(context, event.error)
            }

            is HomeScreenEvent.NavigateToProductDetail -> {
                onNavigateToProductDetail(event.product)
            }

            is HomeScreenEvent.NavigateToSearchResult -> {
                onNavigateToSearchResult(event.searchResultNavArgs)
            }
        }
    }


    HomeScreen(
        modifier = modifier,
        state = state,
        onAction = { action ->
            when (action) {
                is HomeScreenAction.OnCardClick -> {
                    state = state.copy(selectedProduct = action.product)
                    coroutineScope.launch {
                        eventFlow.emit(HomeScreenEvent.NavigateToProductDetail(action.product))
                    }
                }

                is HomeScreenAction.OnSearchClick -> {
                    val args = SearchResultNavArgs(
                        source = SearchResultSource.TEXT,
                        passedQuery = action.query,
                        passedImagePath = null
                    )
                    coroutineScope.launch {
                        eventFlow.emit(
                            HomeScreenEvent.NavigateToSearchResult(
                                args
                            )
                        )
                    }
                }

                is HomeScreenAction.OnSearchQueryChange -> {
                    state = state.copy(searchQuery = action.query)
                }
            }
        }
    )
}

@Composable
private fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeScreenState = HomeScreenState(),
    onAction: (HomeScreenAction) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        TopHeader(
            query = state.searchQuery,
            onQueryChange = { onAction(HomeScreenAction.OnSearchQueryChange(it)) },
            onSearchClick = { onAction(HomeScreenAction.OnSearchClick(it)) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        RecommendSection(
            recommendList = state.recommendList,
            onCardClick = { onAction(HomeScreenAction.OnCardClick(it)) }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    PyeonKingTheme {
        HomeScreen(
            onAction = {}
        )
    }
}