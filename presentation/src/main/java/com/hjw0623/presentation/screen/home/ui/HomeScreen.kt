package com.hjw0623.presentation.screen.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hjw0623.core.domain.AuthManager
import com.hjw0623.core.domain.product.Product
import com.hjw0623.core.domain.search.search_result.SearchResultNavArgs
import com.hjw0623.core.mockdata.mockProductList
import com.hjw0623.core.presentation.designsystem.components.showToast
import com.hjw0623.core.presentation.designsystem.theme.PyeonKingTheme
import com.hjw0623.core.presentation.ui.ObserveAsEvents
import com.hjw0623.core.presentation.ui.rememberThrottledOnClick
import com.hjw0623.presentation.screen.factory.HomeViewModelFactory
import com.hjw0623.presentation.screen.home.ui.component.LoginPromptSection
import com.hjw0623.presentation.screen.home.ui.component.RecommendSection
import com.hjw0623.presentation.screen.home.ui.component.TopHeader
import com.hjw0623.presentation.screen.home.viewmodel.HomeViewModel

@Composable
fun HomeScreenRoot(
    modifier: Modifier = Modifier,
    onNavigateToProductDetail: (Product) -> Unit,
    onNavigateToSearchResult: (SearchResultNavArgs) -> Unit
) {
    val context = LocalContext.current
    val homeViewModel = HomeViewModelFactory()
    val viewModel: HomeViewModel = viewModel(factory = homeViewModel)

    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val recommendList by viewModel.recommendList.collectAsStateWithLifecycle()
    val isLoggedIn by AuthManager.isLoggedIn.collectAsStateWithLifecycle()

    val throttledSearchClick = rememberThrottledOnClick {
        viewModel.onSearchClick()
    }

    ObserveAsEvents(flow = viewModel.event) { event ->
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
        isLoggedIn = isLoggedIn,
        searchQuery = searchQuery,
        recommendList = recommendList,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onSearchQueryChangeDebounced = viewModel::onSearchQueryChangeDebounced,
        onCardClick = viewModel::onProductCardClick,
        onSearchClick = throttledSearchClick
    )
}

@Composable
private fun HomeScreen(
    modifier: Modifier = Modifier,
    isLoggedIn: Boolean,
    searchQuery: String,
    recommendList: List<Product>,
    onSearchQueryChange: (String) -> Unit,
    onSearchQueryChangeDebounced: (String) -> Unit,
    onCardClick: (Product) -> Unit,
    onSearchClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        TopHeader(
            query = searchQuery,
            onQueryChange = onSearchQueryChange,
            onSearchClick = onSearchClick,
            onSearchQueryChangeDebounced = onSearchQueryChangeDebounced
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoggedIn) {
            RecommendSection(
                recommendList = recommendList,
                onCardClick = onCardClick
            )
        } else {
            LoginPromptSection()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    PyeonKingTheme {
        HomeScreen(
            searchQuery = "편의점",
            isLoggedIn = true,
            recommendList = mockProductList,
            onSearchQueryChange = {},
            onSearchQueryChangeDebounced = {},
            onCardClick = {},
            onSearchClick = {}
        )
    }
}