package com.hjw0623.presentation.screen.review.review_history.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.hjw0623.core.core_domain.model.review.ReviewInfo
import com.hjw0623.core.core_ui.designsystem.components.PyeonKingButton
import com.hjw0623.core.core_ui.designsystem.theme.PyeonKingTheme
import com.hjw0623.core.core_ui.ui.getFullImageUrl
import com.hjw0623.core.util.mockdata.mockReviewInfo
import com.hjw0623.presentation.R
import com.hjw0623.presentation.screen.product.ui.componet.RatingStars

@Composable
fun ReviewHistoryListItem(
    reviewInfo: ReviewInfo,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = getFullImageUrl(reviewInfo.productImgUrl).takeIf { it.isNotBlank() },
                contentDescription = null,
                fallback = painterResource(com.hjw0623.core.R.drawable.no_image),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = reviewInfo.productName,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(4.dp))

                RatingStars(
                    rating = reviewInfo.starRating.toFloat()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = reviewInfo.content,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            PyeonKingButton(
                text = stringResource(R.string.action_edit_review),
                onClick = onEditClick
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ReviewHistoryListItemPreview() {
    PyeonKingTheme {
        ReviewHistoryListItem(
            reviewInfo = mockReviewInfo,
            onEditClick = {}
        )
    }
}