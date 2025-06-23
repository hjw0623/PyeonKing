package com.hjw0623.pyeonking.product_detail.presentation.componet

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hjw0623.pyeonking.R
import com.hjw0623.pyeonking.core.presentation.designsystem.util.PyeonKingButton
import com.hjw0623.pyeonking.product_detail.data.ReviewItem
import com.hjw0623.pyeonking.product_detail.data.mockReviewList
import com.hjw0623.pyeonking.ui.theme.PyeonKingTheme

@Composable
fun ReviewTab(
    avgRating: Float,
    ratingList: List<Pair<Int, Int>>,
    totalReviewSum: Int,
    reviewList: List<ReviewItem>,
    onWriteReviewClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        item {
            Text(
                text = avgRating.toString(),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RatingStars(
                    rating = avgRating,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = stringResource(R.string.text_total_reviews, totalReviewSum),
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            RatingDistribution(ratingList = ratingList)

            Spacer(modifier = Modifier.height(24.dp))
        }

        items(
            count = reviewList.size,
            key = { index -> reviewList[index].reviewId }
        ) { index ->
            ReviewListItem(review = reviewList[index])
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))

            PyeonKingButton(
                text = stringResource(R.string.action_write_review),
                onClick = { onWriteReviewClick() },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ReviewTabPreview() {
    PyeonKingTheme {
        ReviewTab(
            avgRating = 1.0f,
            ratingList = listOf(
                Pair(5, 10),
                Pair(4, 8),
                Pair(3, 5),
                Pair(2, 4),
                Pair(1, 2),
            ),
            totalReviewSum = 100,
            reviewList = mockReviewList,
            onWriteReviewClick = {}
        )
    }
}