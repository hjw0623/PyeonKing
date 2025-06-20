package com.hjw0623.pyeonking.home.presentation

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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hjw0623.pyeonking.home.presentation.component.RecommendSection
import com.hjw0623.pyeonking.home.presentation.component.TopHeader
import com.hjw0623.pyeonking.ui.theme.PyeonKingTheme

@Composable
fun HomeScreenRoot(
    modifier: Modifier = Modifier,
) {
    var state by remember { mutableStateOf(HomeScreenState()) }

    HomeScreen(
        modifier = modifier,
        state = state,
        onAction = { action ->
            when (action) {
                is HomeScreenAction.OnCardClick -> {
                    state = state.copy(selectedItemName = action.productName)
                }

                is HomeScreenAction.OnSearchClick -> {
                    state = state.copy(searchQuery = action.query)
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
            state = state,
            onCardClick = { onAction(HomeScreenAction.OnCardClick(it)) }
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun ScreenPreview() {
    PyeonKingTheme{
        HomeScreen(
            onAction = {}
        )
    }
}