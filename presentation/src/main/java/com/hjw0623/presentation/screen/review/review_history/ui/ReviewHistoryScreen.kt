package com.hjw0623.presentation.screen.review.review_history.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hjw0623.core.domain.model.review.ReviewInfo
import com.hjw0623.core.core_ui.designsystem.components.PyeonKingButton
import com.hjw0623.core.core_ui.designsystem.components.showToast
import com.hjw0623.core.core_ui.designsystem.theme.PyeonKingTheme
import com.hjw0623.core.core_ui.util.ObserveAsEvents
import com.hjw0623.core.android.util.mockdata.mockReviewHistoryList
import com.hjw0623.presentation.R
import com.hjw0623.presentation.screen.review.review_history.ui.component.ReviewHistoryListItem
import com.hjw0623.presentation.screen.review.viewmodel.ReviewHistoryViewModel

@Composable
fun ReviewHistoryScreenRoot(
    modifier: Modifier = Modifier,
    reviewHistoryViewModel: ReviewHistoryViewModel = hiltViewModel(),
    onNavigateToReviewEdit: (ReviewInfo) -> Unit
) {
    val context = LocalContext.current
    val state by reviewHistoryViewModel.state.collectAsStateWithLifecycle()


    LaunchedEffect(Unit) {
        reviewHistoryViewModel.fetchReviewHistory(1)
    }

    ObserveAsEvents(flow = reviewHistoryViewModel.event) { event ->
        when (event) {
            is ReviewHistoryScreenEvent.Error -> {
                showToast(context, event.error)
            }

            is ReviewHistoryScreenEvent.NavigateToReviewEdit -> {
                onNavigateToReviewEdit(event.reviewInfo)
            }
        }
    }

    ReviewHistoryScreen(
        modifier = modifier,
        state = state,
        onEditReviewClick = reviewHistoryViewModel::onEditReviewClick,
        onLoadMoreClick = reviewHistoryViewModel::fetchNextReviewPage
    )
}

@Composable
private fun ReviewHistoryScreen(
    modifier: Modifier = Modifier,
    state: ReviewHistoryScreenState,
    onEditReviewClick: (ReviewInfo) -> Unit,
    onLoadMoreClick: () -> Unit
) {
    LazyColumn(
        modifier = modifier
            .padding(horizontal = 20.dp)
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
            items = state.reviews,
            key = { it.reviewId }
        ) { reviewItem ->
            ReviewHistoryListItem(
                reviewInfo = reviewItem,
                onEditClick = { onEditReviewClick(reviewItem) }
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (state.reviews.last() != reviewItem) {
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(Color.LightGray)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        if (state.currentPage < state.lastPage) {
            item {
                Spacer(modifier = Modifier.height(20.dp))
                PyeonKingButton(
                    text = stringResource(
                        R.string.action_see_more_review, state.currentPage, state.lastPage
                    ),
                    onClick = onLoadMoreClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(40.dp))
            }
        }

        if (state.isLoading) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReviewHistoryScreenPreview() {
    PyeonKingTheme {
        ReviewHistoryScreen(
            state = ReviewHistoryScreenState(
                reviews = mockReviewHistoryList,
                isLoading = false,
                currentPage = 1,
                lastPage = 3
            ),
            onEditReviewClick = {},
            onLoadMoreClick = {},
        )
    }
}