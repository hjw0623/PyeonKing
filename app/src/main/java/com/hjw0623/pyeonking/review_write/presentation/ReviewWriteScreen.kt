package com.hjw0623.pyeonking.review_write.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hjw0623.pyeonking.R
import com.hjw0623.pyeonking.core.data.Product
import com.hjw0623.pyeonking.core.data.mockProduct
import com.hjw0623.pyeonking.core.presentation.designsystem.util.BackBar
import com.hjw0623.pyeonking.core.presentation.designsystem.util.PyeonKingButton
import com.hjw0623.pyeonking.review_write.presentation.component.StarRatingSelector
import com.hjw0623.pyeonking.review_write.presentation.component.WritingSection
import com.hjw0623.pyeonking.ui.theme.PyeonKingTheme

@Composable
fun ReviewWriteScreenRoot(
    modifier: Modifier = Modifier,
    product: Product = mockProduct,
) {
    var state by remember {
        mutableStateOf(ReviewWriteState(product = product))
    }

    ReviewWriteScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is ReviewWriteAction.OnContentChange -> {
                    state = state.copy(content = action.content)
                }

                ReviewWriteAction.OnBackClick -> {

                }

                is ReviewWriteAction.OnRatingChange -> {
                    state = state.copy(rating = action.rating)
                }

                is ReviewWriteAction.OnSubmitClick -> {

                }
            }
        },
        modifier = modifier
    )
}

@Composable
fun ReviewWriteScreen(
    state: ReviewWriteState,
    onAction: (ReviewWriteAction) -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(modifier = modifier.fillMaxSize()) {
        BackBar(
            title = stringResource(R.string.title_write_review),
            icon = Icons.Default.Clear,
            onBackClick = {
                onAction(ReviewWriteAction.OnBackClick)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            AsyncImage(
                model = state.product.imgUrl,
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.padding(8.dp)
            ) {

                Text(
                    text = state.product.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),

                    )
                StarRatingSelector(
                    rating = state.rating,
                    onRatingChange = { onAction(ReviewWriteAction.OnRatingChange(it)) }
                )
            }
        }

        WritingSection(
            content = state.content,
            onContentChange = { onAction(ReviewWriteAction.OnContentChange(it)) }
        )

        Spacer(modifier = Modifier.weight(1f))

        PyeonKingButton(
            text = stringResource(R.string.action_submit),
            onClick = {
                onAction(
                    ReviewWriteAction.OnSubmitClick(
                        rating = state.rating,
                        content = state.content
                    )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun ReviewWriteScreenPreview() {
    PyeonKingTheme {
        ReviewWriteScreen(
            state = ReviewWriteState(product = mockProduct),
            onAction = {},
        )
    }
}
