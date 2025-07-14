package com.hjw0623.presentation.screen.review.review_edit.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.hjw0623.core.domain.review.review_history.ReviewInfo
import com.hjw0623.core.presentation.designsystem.components.PyeonKingButton
import com.hjw0623.core.presentation.designsystem.components.showToast
import com.hjw0623.core.presentation.designsystem.theme.PyeonKingTheme
import com.hjw0623.core.presentation.ui.ObserveAsEvents
import com.hjw0623.core.presentation.ui.rememberThrottledOnClick
import com.hjw0623.presentation.R
import com.hjw0623.presentation.screen.factory.ReviewViewModelFactory
import com.hjw0623.presentation.screen.review.review_write.ui.component.StarRatingSelector
import com.hjw0623.presentation.screen.review.viewmodel.ReviewViewModel

@Composable
fun ReviewEditScreenRoot(
    reviewInfo: ReviewInfo,
    onNavigateReviewHistory: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val reviewViewModelFactory = ReviewViewModelFactory()
    val viewModel: ReviewViewModel = viewModel(factory = reviewViewModelFactory)

    val productName by viewModel.productName.collectAsStateWithLifecycle()
    val productImgUrl by viewModel.productImgUrl.collectAsStateWithLifecycle()
    val newContent by viewModel.newContent.collectAsStateWithLifecycle()
    val newStarRating by viewModel.newStarRating.collectAsStateWithLifecycle()
    val isEditButtonEnabled by viewModel.isEditButtonEnabled.collectAsStateWithLifecycle()

    val throttledEditClick = rememberThrottledOnClick {
        viewModel.onEditClick()
    }

    LaunchedEffect(key1 = reviewInfo) {
        viewModel.init(reviewInfo)
    }

    ObserveAsEvents(flow = viewModel.event) { event ->
        when (event) {
            is ReviewEditScreenEvent.Error -> {
                showToast(context, event.error)
            }
            ReviewEditScreenEvent.NavigateReviewHistory -> {
                onNavigateReviewHistory()
            }
        }
    }

    ReviewEditScreen(
        modifier = modifier,
        productName = productName,
        productImgUrl = productImgUrl,
        newContent = newContent,
        newStarRating = newStarRating,
        isEditButtonEnabled = isEditButtonEnabled,
        onContentChanged = viewModel::onContentChanged,
        onStarRatingChanged = viewModel::onStarRatingChanged,
        onEditClick = throttledEditClick
    )
}

@Composable
fun ReviewEditScreen(
    modifier: Modifier = Modifier,
    productName: String,
    productImgUrl: String,
    newContent: String,
    newStarRating: Int,
    isEditButtonEnabled: Boolean,
    onContentChanged: (String) -> Unit,
    onStarRatingChanged: (Int) -> Unit,
    onEditClick: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            AsyncImage(
                model = productImgUrl,
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = productName,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                StarRatingSelector(
                    rating = newStarRating,
                    onRatingChange = onStarRatingChanged
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.prompt_edit_review),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = newContent,
                onValueChange = onContentChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                placeholder = {
                    Text(
                        text = stringResource(R.string.hint_review_input),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        PyeonKingButton(
            text = stringResource(R.string.action_edit_review_complete),
            onClick = onEditClick,
            enabled = isEditButtonEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ReviewEditScreenPreview() {
    PyeonKingTheme {
        ReviewEditScreen(
            productName = "스팸 김치볶음밥",
            productImgUrl = "",
            newContent = "이거 정말 맛있네요! 최고!",
            newStarRating = 5,
            isEditButtonEnabled = true,
            onContentChanged = {},
            onStarRatingChanged = {},
            onEditClick = {}
        )
    }
}