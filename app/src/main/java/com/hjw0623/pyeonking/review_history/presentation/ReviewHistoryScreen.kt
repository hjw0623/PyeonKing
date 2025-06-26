package com.hjw0623.pyeonking.review_history.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hjw0623.pyeonking.R
import com.hjw0623.pyeonking.core.presentation.designsystem.util.BackBar
import com.hjw0623.pyeonking.review_history.presentation.component.ReviewHistoryListItem
import com.hjw0623.pyeonking.ui.theme.PyeonKingTheme

@Composable
fun ReviewHistoryScreenRoot(
    onNavigateEditReview: () -> Unit,
    onNavigateBack: () -> Unit,
) {

    var state by remember { mutableStateOf(ReviewHistoryScreenState()) }

    ReviewHistoryScreen(
        state = state,
        onAction = { action ->
            when (action) {
                ReviewHistoryScreenAction.OnBackClick -> onNavigateBack()
                ReviewHistoryScreenAction.OnEditReviewClick -> onNavigateEditReview()
            }
        },
        modifier = Modifier
    )
}

@Composable
private fun ReviewHistoryScreen(
    state: ReviewHistoryScreenState,
    onAction: (ReviewHistoryScreenAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            BackBar(
                onBackClick = { onAction(ReviewHistoryScreenAction.OnBackClick) }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .fillMaxSize()
        ) {
            item {
                Text(
                    text = stringResource(R.string.label_review_history),
                    style = MaterialTheme.typography.headlineLarge
                )

                Spacer(modifier = Modifier.height(40.dp))
            }

            items(
                items = state.reviewHistoryList,
                key = { it.reviewId }
            ) {
                ReviewHistoryListItem(
                    reviewInfo = it,
                    onEditClick = { onAction(ReviewHistoryScreenAction.OnEditReviewClick) }
                )
                Spacer(modifier = Modifier.height(16.dp))
                if (state.reviewHistoryList.last() != it) {
                    Spacer(
                        modifier = Modifier
                            .height(1.dp)
                            .fillMaxWidth()
                            .background(Color.LightGray)
                    )
                }
            }
        }
    }

}

@Preview
@Composable
private fun ReviewHistoryScreenPreview() {
    PyeonKingTheme {
        ReviewHistoryScreen(
            state = ReviewHistoryScreenState(),
            onAction = {}
        )
    }
}