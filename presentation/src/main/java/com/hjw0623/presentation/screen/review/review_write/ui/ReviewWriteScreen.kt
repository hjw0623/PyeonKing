package com.hjw0623.presentation.screen.review.review_write.ui

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hjw0623.core.domain.product.Product
import com.hjw0623.core.mockdata.mockProduct
import com.hjw0623.core.presentation.designsystem.components.PyeonKingButton
import com.hjw0623.core.presentation.designsystem.components.showToast
import com.hjw0623.core.presentation.designsystem.theme.PyeonKingTheme
import com.hjw0623.core.presentation.ui.ObserveAsEvents
import com.hjw0623.presentation.R
import com.hjw0623.presentation.screen.review.review_write.ReviewWriteScreenAction
import com.hjw0623.presentation.screen.review.review_write.ui.ReviewWriteScreenEvent
import com.hjw0623.presentation.screen.review.review_write.ReviewWriteScreenState
import com.hjw0623.presentation.screen.review.review_write.ui.component.StarRatingSelector
import com.hjw0623.presentation.screen.review.review_write.ui.component.WritingSection
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

@Composable
fun ReviewWriteScreenRoot(
    modifier: Modifier = Modifier,
    product: Product,
    onReviewWriteComplete: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var state by remember {
        mutableStateOf(ReviewWriteScreenState(product = product))
    }
    val eventFlow = remember { MutableSharedFlow<ReviewWriteScreenEvent>() }

    ObserveAsEvents(flow = eventFlow) { event ->
        when (event) {
            is ReviewWriteScreenEvent.Error -> {
                showToast(context, event.error)
            }

            is ReviewWriteScreenEvent.NavigateBackToProductDetail -> {
                onReviewWriteComplete()
            }
        }
    }

    ReviewWriteScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is ReviewWriteScreenAction.OnContentChange -> {
                    state = state.copy(content = action.content)
                }

                is ReviewWriteScreenAction.OnRatingChange -> {
                    state = state.copy(rating = action.rating)
                }

                is ReviewWriteScreenAction.OnSubmitClick -> {
                    scope.launch {
                        eventFlow.emit(ReviewWriteScreenEvent.NavigateBackToProductDetail)
                    }
                }
            }
        },
        modifier = modifier
    )
}

@Composable
fun ReviewWriteScreen(
    state: ReviewWriteScreenState,
    onAction: (ReviewWriteScreenAction) -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(modifier = modifier.fillMaxSize()) {


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
                    onRatingChange = { onAction(ReviewWriteScreenAction.OnRatingChange(it)) }
                )
            }
        }

        WritingSection(
            content = state.content,
            onContentChange = { onAction(ReviewWriteScreenAction.OnContentChange(it)) }
        )

        Spacer(modifier = Modifier.weight(1f))

        PyeonKingButton(
            text = stringResource(R.string.action_submit),
            onClick = {
                onAction(
                    ReviewWriteScreenAction.OnSubmitClick(
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
            state = ReviewWriteScreenState(product = mockProduct),
            onAction = {},
        )
    }
}
