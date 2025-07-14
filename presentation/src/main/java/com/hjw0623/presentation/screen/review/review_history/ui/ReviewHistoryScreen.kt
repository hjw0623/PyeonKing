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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hjw0623.core.domain.review.review_history.ReviewInfo
import com.hjw0623.core.util.mockdata.mockReviewHistoryList
import com.hjw0623.core.presentation.designsystem.components.showToast
import com.hjw0623.core.presentation.designsystem.theme.PyeonKingTheme
import com.hjw0623.core.presentation.ui.ObserveAsEvents
import com.hjw0623.presentation.R
import com.hjw0623.presentation.screen.factory.ReviewHistoryViewModelFactory
import com.hjw0623.presentation.screen.review.review_history.ui.component.ReviewHistoryListItem
import com.hjw0623.presentation.screen.review.viewmodel.ReviewHistoryViewModel

@Composable
fun ReviewHistoryScreenRoot(
    onNavigateToReviewEdit: (ReviewInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val reviewHistoryViewModelFactory = ReviewHistoryViewModelFactory()
    val viewModel: ReviewHistoryViewModel = viewModel(factory = reviewHistoryViewModelFactory)

    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val reviewHistoryList by viewModel.reviewHistoryList.collectAsStateWithLifecycle()

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
        onEditReviewClick = viewModel::onEditReviewClick
    )
}

@Composable
private fun ReviewHistoryScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    reviewHistoryList: List<ReviewInfo>,
    onEditReviewClick: (ReviewInfo) -> Unit,
) {
    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
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
            onEditReviewClick = {}
        )
    }
}