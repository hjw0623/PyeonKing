package com.hjw0623.presentation.screen.review.review_edit.ui

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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.hjw0623.core.core_domain.model.review.ReviewInfo
import com.hjw0623.core.core_ui.designsystem.components.LoadingButton
import com.hjw0623.core.core_ui.designsystem.components.showToast
import com.hjw0623.core.core_ui.designsystem.theme.PyeonKingTheme
import com.hjw0623.core.core_ui.util.ObserveAsEvents
import com.hjw0623.core.core_ui.util.getFullImageUrl
import com.hjw0623.core.core_ui.util.rememberThrottledOnClick
import com.hjw0623.presentation.R
import com.hjw0623.presentation.screen.review.review_write.ui.component.StarRatingSelector
import com.hjw0623.presentation.screen.review.viewmodel.ReviewEditViewModel

@Composable
fun ReviewEditScreenRoot(
    modifier: Modifier = Modifier,
    reviewInfo: ReviewInfo,
    reviewEditViewModel: ReviewEditViewModel = hiltViewModel(),
    onNavigateReviewHistory: () -> Unit
) {
    val context = LocalContext.current
    val state by reviewEditViewModel.state.collectAsStateWithLifecycle()

    val throttledEditClick = rememberThrottledOnClick {
        reviewEditViewModel.onEditClick()
    }

    LaunchedEffect(key1 = reviewInfo) {
        reviewEditViewModel.fetchInitReview(reviewInfo)
    }

    ObserveAsEvents(flow = reviewEditViewModel.event) { event ->
        when (event) {
            is ReviewEditScreenEvent.Error -> {
                showToast(context, event.error)
            }

            is ReviewEditScreenEvent.NavigateReviewHistory -> {
                showToast(context, event.message)
                onNavigateReviewHistory()
            }
        }
    }

    ReviewEditScreen(
        modifier = modifier,
        state = state,
        onContentChanged = reviewEditViewModel::onContentChanged,
        onStarRatingChanged = reviewEditViewModel::onStarRatingChanged,
        onEditClick = throttledEditClick
    )
}

@Composable
fun ReviewEditScreen(
    modifier: Modifier = Modifier,
    state: ReviewEditScreenState,
    onContentChanged: (String) -> Unit,
    onStarRatingChanged: (Int) -> Unit,
    onEditClick: () -> Unit,
) {
    val keyboard = LocalSoftwareKeyboardController.current

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
                model = getFullImageUrl(state.productImgUrl).takeIf { it.isNotBlank() },
                contentDescription = null,
                fallback = painterResource(com.hjw0623.core.R.drawable.no_image),
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = state.productName,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                StarRatingSelector(
                    rating = state.newStarRating,
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
                value = state.newContent,
                onValueChange = onContentChanged,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions {
                    keyboard?.hide()
                },
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

        LoadingButton(
            text = stringResource(R.string.action_edit_review_complete),
            onClick = onEditClick,
            enabled = state.isEditButtonEnabled,
            loading = state.isEditing,
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ReviewEditScreenPreview() {
    PyeonKingTheme {
        ReviewEditScreen(
            state = ReviewEditScreenState(
                productName = "스팸 김치볶음밥",
                productImgUrl = "",
                newContent = "이거 정말 맛있네요! 최고!",
                newStarRating = 5,
            ),
            onContentChanged = {},
            onStarRatingChanged = {},
            onEditClick = {},
        )
    }
}