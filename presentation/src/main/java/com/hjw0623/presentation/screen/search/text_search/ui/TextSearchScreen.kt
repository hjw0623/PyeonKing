package com.hjw0623.presentation.screen.search.text_search.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hjw0623.core.business_logic.model.product.Product
import com.hjw0623.core.business_logic.model.search.search_result.SearchResultNavArgs
import com.hjw0623.core.business_logic.model.search.text_search.FilterType
import com.hjw0623.core.presentation.designsystem.components.showToast
import com.hjw0623.core.presentation.designsystem.theme.PyeonKingTheme
import com.hjw0623.core.presentation.ui.ObserveAsEvents
import com.hjw0623.core.presentation.ui.rememberThrottledOnClick
import com.hjw0623.core.util.mockdata.mockProductList
import com.hjw0623.presentation.screen.search.text_search.ui.component.SearchTextField
import com.hjw0623.presentation.screen.search.text_search.ui.component.focused.SearchHistorySection
import com.hjw0623.presentation.screen.search.text_search.ui.component.unFocused.ProductListSection
import com.hjw0623.presentation.screen.search.viewmodel.TextSearchViewModel
import kotlinx.coroutines.launch

@Composable
fun TextSearchScreenRoot(
    modifier: Modifier = Modifier,
    textSearchViewModel: TextSearchViewModel = hiltViewModel(),
    onNavigateToSearchResult: (SearchResultNavArgs) -> Unit,
    onNavigateToProductDetail: (Product) -> Unit
) {
    val context = LocalContext.current
    val state by textSearchViewModel.state.collectAsStateWithLifecycle()

    val throttledSearchClick =
        rememberThrottledOnClick(onClick = textSearchViewModel::onSearchClick)
    val throttledHistoryClick =
        rememberThrottledOnClick(onClick = textSearchViewModel::onHistoryClick)
    val throttledDeleteHistoryClick =
        rememberThrottledOnClick(onClick = textSearchViewModel::onDeleteSearchHistory)
    val throttledProductClick =
        rememberThrottledOnClick(onClick = textSearchViewModel::onProductClick)

    ObserveAsEvents(flow = textSearchViewModel.event) { event ->
        when (event) {
            is TextSearchScreenEvent.Error -> showToast(context, event.error)
            is TextSearchScreenEvent.NavigateToSearchResult -> onNavigateToSearchResult(event.searchResultNavArgs)
            is TextSearchScreenEvent.NavigateToProductDetail -> onNavigateToProductDetail(event.product)
        }
    }

    TextSearchScreen(
        modifier = modifier,
        state = state,
        onQueryChange = textSearchViewModel::onQueryChange,
        onClearClick = textSearchViewModel::onClearClick,
        onSearchClick = throttledSearchClick,
        onHistoryClick = throttledHistoryClick,
        onDeleteSearchHistory = throttledDeleteHistoryClick,
        onProductClick = throttledProductClick,
        onFilterToggle = textSearchViewModel::onToggleFilter
    )
}

@Composable
fun TextSearchScreen(
    modifier: Modifier = Modifier,
    state: TextSearchScreenState,
    onQueryChange: (String) -> Unit,
    onClearClick: () -> Unit,
    onSearchClick: () -> Unit,
    onHistoryClick: (String) -> Unit,
    onDeleteSearchHistory: (String) -> Unit,
    onProductClick: (Product) -> Unit,
    onFilterToggle: (FilterType) -> Unit,
) {
    var isFocused by rememberSaveable { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(Modifier.fillMaxSize()) {
            SearchTextField(
                searchQuery = state.query,
                onQueryChange = onQueryChange,
                onClearClick = onClearClick,
                onBackClick = {
                    focusManager.clearFocus(force = true)
                    keyboardController?.hide()
                },
                onSearchClick = onSearchClick,
                onFocusChange = { isFocused = it },
                focusRequester = focusRequester
            )

            if (isFocused) {
                SearchHistorySection(
                    searchHistory = state.searchHistory,
                    onHistoryClick = onHistoryClick,
                    onRemoveClick = onDeleteSearchHistory
                )
            } else {
                ProductListSection(
                    selectedFilter = state.selectedFilters,
                    products = state.filteredProducts,
                    onProductClick = onProductClick,
                    onFilterToggle = onFilterToggle,
                    listState = listState
                )
            }
        }

        if (!isFocused && state.filteredProducts.isNotEmpty()) {
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        listState.animateScrollToItem(0)
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowUpward,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.32f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun TextSearchScreenPreview() {
    PyeonKingTheme {
        TextSearchScreen(
            state = TextSearchScreenState(
                isLoading = false,
                query = "라면",
                searchHistory = listOf("콜라", "아이스크림", "도시락"),
                allProducts = mockProductList,
                filteredProducts = mockProductList,
                selectedFilters = mapOf(FilterType.ONE_PLUS_ONE to true)
            ),
            onQueryChange = {},
            onClearClick = {},
            onSearchClick = {},
            onHistoryClick = {},
            onDeleteSearchHistory = {},
            onProductClick = {},
            onFilterToggle = {}
        )
    }
}