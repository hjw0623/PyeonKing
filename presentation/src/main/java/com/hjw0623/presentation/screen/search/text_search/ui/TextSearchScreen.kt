package com.hjw0623.presentation.screen.search.text_search.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
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

@Composable
fun TextSearchScreenRoot(
    textSearchViewModel: TextSearchViewModel,
    onNavigateToSearchResult: (SearchResultNavArgs) -> Unit,
    onNavigateToProductDetail: (Product) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val viewModel = textSearchViewModel

    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val query by viewModel.query.collectAsStateWithLifecycle()
    val searchHistory by viewModel.searchHistory.collectAsStateWithLifecycle()
    val selectedFilters by viewModel.selectedFilters.collectAsStateWithLifecycle()
    val filteredProducts by viewModel.filteredProducts.collectAsStateWithLifecycle()

    val throttledSearchClick = rememberThrottledOnClick(onClick = viewModel::onSearchClick)
    val throttledHistoryClick =
        rememberThrottledOnClick(onClick = viewModel::onHistoryClick)
    val throttledDeleteHistoryClick =
        rememberThrottledOnClick(onClick = viewModel::onDeleteSearchHistory)
    val throttledProductClick =
        rememberThrottledOnClick(onClick = viewModel::onProductClick)

    LaunchedEffect(Unit) {
        viewModel.fetchAllProducts()
    }
    ObserveAsEvents(flow = viewModel.event) { event ->
        when (event) {
            is TextSearchScreenEvent.Error -> showToast(context, event.error)
            is TextSearchScreenEvent.NavigateToSearchResult -> onNavigateToSearchResult(event.searchResultNavArgs)
            is TextSearchScreenEvent.NavigateToProductDetail -> onNavigateToProductDetail(event.product)
        }
    }

    TextSearchScreen(
        modifier = modifier,
        isLoading = isLoading,
        query = query,
        searchHistory = searchHistory,
        allProducts = filteredProducts,
        selectedFilters = selectedFilters,
        onQueryChange = viewModel::onQueryChange,
        onClearClick = viewModel::onClearClick,
        onSearchClick = throttledSearchClick,
        onHistoryClick = throttledHistoryClick,
        onDeleteSearchHistory = throttledDeleteHistoryClick,
        onProductClick = throttledProductClick,
        onFilterToggle = viewModel::onToggleFilter
    )
}

@Composable
fun TextSearchScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    query: String,
    searchHistory: List<String>,
    allProducts: List<Product>,
    selectedFilters: Map<FilterType, Boolean>,
    onQueryChange: (String) -> Unit,
    onClearClick: () -> Unit,
    onSearchClick: () -> Unit,
    onHistoryClick: (String) -> Unit,
    onDeleteSearchHistory: (String) -> Unit,
    onProductClick: (Product) -> Unit,
    onFilterToggle: (FilterType) -> Unit,
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
            searchQuery = query,
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
                searchHistory = searchHistory,
                onHistoryClick = onHistoryClick,
                onRemoveClick = onDeleteSearchHistory
            )
        } else {
            ProductListSection(
                selectedFilter = selectedFilters,
                products = allProducts,
                onProductClick = onProductClick,
                onFilterToggle = onFilterToggle
            )
        }
        if (isLoading) {

            Box(
                modifier = Modifier.fillMaxWidth(),
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
            isLoading = false,
            query = "라면",
            searchHistory = listOf("콜라", "아이스크림", "도시락"),
            allProducts = mockProductList,
            selectedFilters = mapOf(FilterType.ONE_PLUS_ONE to true),
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