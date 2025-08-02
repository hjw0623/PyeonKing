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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hjw0623.core.business_logic.model.review.ReviewInfo
import com.hjw0623.core.presentation.designsystem.components.PyeonKingButton
import com.hjw0623.core.presentation.designsystem.components.showToast
import com.hjw0623.core.presentation.designsystem.theme.PyeonKingTheme
import com.hjw0623.core.presentation.ui.ObserveAsEvents
import com.hjw0623.core.util.mockdata.mockReviewHistoryList
import com.hjw0623.presentation.R
import com.hjw0623.presentation.screen.review.review_history.ui.component.ReviewHistoryListItem
import com.hjw0623.presentation.screen.review.viewmodel.ReviewHistoryViewModel

@Composable
fun ReviewHistoryScreenRoot(
    reviewHistoryViewModel: ReviewHistoryViewModel,
    onNavigateToReviewEdit: (ReviewInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val viewModel = reviewHistoryViewModel

    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val reviewHistoryList by viewModel.reviewHistoryList.collectAsStateWithLifecycle()
    val currentPage by viewModel.currentPage.collectAsStateWithLifecycle()
    val lastPage by viewModel.lastPage.collectAsStateWithLifecycle()


    LaunchedEffect(Unit) {
        viewModel.fetchReviewHistory(1)
    }

    ObserveAsEvents(flow = viewModel.event) { event ->
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
        isLoading = isLoading,
        reviewHistoryList = reviewHistoryList,
        currentPage = currentPage,
        lastPage = lastPage,
        onEditReviewClick = viewModel::onEditReviewClick,
        onLoadMoreClick = viewModel::fetchNextReviewPage
    )
}

@Composable
private fun ReviewHistoryScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    reviewHistoryList: List<ReviewInfo>,
    currentPage: Int,
    lastPage: Int,
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
            items = reviewHistoryList,
            key = { it.reviewId }
        ) { reviewItem ->
            ReviewHistoryListItem(
                reviewInfo = reviewItem,
                onEditClick = { onEditReviewClick(reviewItem) }
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (reviewHistoryList.last() != reviewItem) {
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(Color.LightGray)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        if (currentPage < lastPage) {
            item {
                Spacer(modifier = Modifier.height(20.dp))
                PyeonKingButton(
                    text = stringResource(
                        R.string.action_see_more_review, currentPage, lastPage
                    ),
                    onClick = onLoadMoreClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(40.dp))
            }
        }

        if (isLoading) {
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
            isLoading = false,
            reviewHistoryList = mockReviewHistoryList,
            onEditReviewClick = {},
            onLoadMoreClick = {},
            currentPage = 1,
            lastPage = 3
        )
    }
}