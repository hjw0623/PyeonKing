package com.hjw0623.pyeonking.text_sarch.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import com.hjw0623.pyeonking.core.data.Product
import com.hjw0623.pyeonking.core.data.SearchResultNavArgs
import com.hjw0623.pyeonking.core.data.mockProductList
import com.hjw0623.pyeonking.core.presentation.designsystem.util.showToast
import com.hjw0623.pyeonking.core.util.ObserveAsEvents
import com.hjw0623.pyeonking.search_result.data.SearchResultSource
import com.hjw0623.pyeonking.text_sarch.data.FilterType
import com.hjw0623.pyeonking.text_sarch.domain.filterProducts
import com.hjw0623.pyeonking.text_sarch.presentation.component.SearchTextField
import com.hjw0623.pyeonking.text_sarch.presentation.component.focused.SearchHistorySection
import com.hjw0623.pyeonking.text_sarch.presentation.component.unFocused.ProductListSection
import com.hjw0623.pyeonking.ui.theme.PyeonKingTheme
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch


@Composable
fun TextSearchScreenRoot(
    onNavigateToSearchResult: (SearchResultNavArgs) -> Unit,
    onNavigateToProductDetail: (Product) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var state by remember { mutableStateOf(TextSearchScreenState()) }
    val eventFlow = remember { MutableSharedFlow<TextSearchScreenEvent>() }

    ObserveAsEvents(flow = eventFlow) { event ->
        when (event) {
            is TextSearchScreenEvent.Error -> {
                showToast(context, event.error)
            }

            is TextSearchScreenEvent.NavigateToSearchResult -> {
                onNavigateToSearchResult(event.searchResultNavArgs)
            }

            is TextSearchScreenEvent.NavigateToProductDetail -> {
                onNavigateToProductDetail(event.product)
            }
        }
    }

    TextSearchScreen(
        state = state,
        onAction = { action ->
            when (action) {
                TextSearchScreenAction.OnClearClick -> {
                    state = state.copy(query = "")
                }

                is TextSearchScreenAction.OnDeleteSearchHistory -> {
                    state = state.copy(
                        searchHistory = state.searchHistory.filterNot { it == action.historyQuery }
                    )
                }

                is TextSearchScreenAction.OnHistoryClick -> {
                    state = state.copy(query = action.historyInput)
                }

                is TextSearchScreenAction.OnSearchClick -> {
                    if (state.query.isNotBlank()) {
                        state = state.copy(
                            query = action.searchInput,
                            searchHistory = listOf(state.query) + state.searchHistory.filterNot { it == state.query }
                        )
                        scope.launch {
                            eventFlow.emit(
                                TextSearchScreenEvent.NavigateToSearchResult(
                                    searchResultNavArgs = SearchResultNavArgs(
                                        source = SearchResultSource.TEXT,
                                        passedQuery = action.searchInput,
                                        passedImagePath = null
                                    )
                                )
                            )
                        }
                    } else {
                        scope.launch {
                            eventFlow.emit(TextSearchScreenEvent.Error("검색어를 입력해주세요."))
                        }
                    }
                }

                is TextSearchScreenAction.OnQueryChange -> {
                    state = state.copy(query = action.query)
                }

                is TextSearchScreenAction.OnProductClick -> {
                    state = state.copy(selectedProduct = action.product)
                    scope.launch {
                        eventFlow.emit(TextSearchScreenEvent.NavigateToProductDetail(action.product))
                    }
                }

                is TextSearchScreenAction.OnToggleFilter -> {
                    val updatedFilters = state.selectedFilters.toMutableMap()
                    updatedFilters[action.filterType] =
                        state.selectedFilters[action.filterType] != true

                    val filteredProducts = filterProducts(
                        allProducts = mockProductList,
                        filters = updatedFilters
                    )

                    state = state.copy(
                        selectedFilters = updatedFilters,
                        products = filteredProducts
                    )
                }
            }
        },
        modifier = modifier
    )
}

@Composable
fun TextSearchScreen(
    state: TextSearchScreenState,
    onAction: (TextSearchScreenAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        SearchTextField(
            searchQuery = state.query,
            onQueryChange = { onAction(TextSearchScreenAction.OnQueryChange(it)) },
            onClearClick = { onAction(TextSearchScreenAction.OnClearClick) },
            onBackClick = {
                focusManager.clearFocus(force = true)
                keyboardController?.hide()
            },
            onSearchClick = {
                onAction(TextSearchScreenAction.OnSearchClick(state.query))
                keyboardController?.hide()
            },
            onFocusChange = { isFocused = it },
            focusRequester = focusRequester
        )

        if (isFocused) {
            SearchHistorySection(
                searchHistory = state.searchHistory,
                onHistoryClick = { onAction(TextSearchScreenAction.OnHistoryClick(it)) },
                onRemoveClick = { onAction(TextSearchScreenAction.OnDeleteSearchHistory(it)) }
            )
        } else {
            ProductListSection(
                selectedFilter = state.selectedFilters,
                products = state.products,
                onProductClick = { onAction(TextSearchScreenAction.OnProductClick(it)) },
                onFilterToggle = { onAction(TextSearchScreenAction.OnToggleFilter(it)) }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun TextSearchScreenPreview() {
    PyeonKingTheme {
        TextSearchScreen(
            state = TextSearchScreenState(
                selectedFilters = mapOf(
                    FilterType.ONE_PLUS_ONE to true,
                    FilterType.CU to false,
                    FilterType.GS25 to false
                ),
                searchHistory = listOf(
                    "11111",
                    "11111",
                    "11111",
                    "11111",
                ),
                products = mockProductList
            ),
            onAction = {},
        )
    }
}