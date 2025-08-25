package com.hjw0623.presentation.screen.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hjw0623.core.business_logic.model.product.Product
import com.hjw0623.core.business_logic.model.search.search_result.SearchResultNavArgs
import com.hjw0623.core.presentation.designsystem.components.showToast
import com.hjw0623.core.presentation.designsystem.theme.PyeonKingTheme
import com.hjw0623.core.presentation.ui.ObserveAsEvents
import com.hjw0623.core.presentation.ui.rememberThrottledOnClick
import com.hjw0623.core.util.mockdata.mockProductList
import com.hjw0623.presentation.R
import com.hjw0623.presentation.screen.home.ui.component.LoginPrompt
import com.hjw0623.presentation.screen.home.ui.component.RecommendSection
import com.hjw0623.presentation.screen.home.ui.component.TopHeader
import com.hjw0623.presentation.screen.home.viewmodel.HomeViewModel

@Composable
fun HomeScreenRoot(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onNavigateToProductDetail: (Product) -> Unit,
    onNavigateToSearchResult: (SearchResultNavArgs) -> Unit
) {
    val context = LocalContext.current
    val state by homeViewModel.state.collectAsStateWithLifecycle()

    val throttledSearchClick = rememberThrottledOnClick {
        homeViewModel.onSearchClick()
    }

    LaunchedEffect(state.isLoggedIn) {
        if (state.isLoggedIn) {
            homeViewModel.fetchRecommendList()
        }
    }

    ObserveAsEvents(flow = homeViewModel.event) { event ->
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
        onSearchQueryChange = homeViewModel::onSearchQueryChange,
        onSearchQueryChangeDebounced = homeViewModel::onSearchQueryChangeDebounced,
        onCardClick = homeViewModel::onProductCardClick,
        onSearchClick = throttledSearchClick
    )
}

@Composable
private fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeScreenState,
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
            query = state.searchQuery,
            onQueryChange = onSearchQueryChange,
            onSearchClick = onSearchClick,
            onSearchQueryChangeDebounced = onSearchQueryChangeDebounced
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (state.isLoggedIn) {
            RecommendSection(
                isLoading = state.isLoading,
                recommendList = state.recommendList,
                hasFetched = state.hasFetchedRecommendList,
                onCardClick = onCardClick
            )
        } else {
            LoginPrompt(
                title = stringResource(R.string.home_title_require_login),
                message = stringResource(R.string.home_message_require_login),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    PyeonKingTheme {
        HomeScreen(
            state = HomeScreenState(
                searchQuery = "편의점",
                isLoggedIn = true,
                isLoading = true,
                recommendList = mockProductList
            ),
            onSearchQueryChange = {},
            onSearchQueryChangeDebounced = {},
            onCardClick = {},
            onSearchClick = {}
        )
    }
}