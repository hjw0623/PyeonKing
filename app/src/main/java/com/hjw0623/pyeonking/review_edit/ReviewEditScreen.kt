package com.hjw0623.pyeonking.review_edit


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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import com.hjw0623.pyeonking.core.presentation.designsystem.util.BackBar
import com.hjw0623.pyeonking.core.presentation.designsystem.util.PyeonKingButton
import com.hjw0623.pyeonking.review_write.presentation.component.StarRatingSelector
import com.hjw0623.pyeonking.ui.theme.PyeonKingTheme

@Composable
fun ReviewEditScreenRoot(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var state by remember { mutableStateOf(ReviewEditScreenState()) }

    ReviewEditScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is ReviewEditScreenAction.OnContentChanged -> {
                    state = state.copy(newContent = action.content)
                }

                ReviewEditScreenAction.OnBackClick -> onNavigateBack()

                is ReviewEditScreenAction.OnStarRatingChanged -> {
                    state = state.copy(newStarRating = action.starRating)
                }

                is ReviewEditScreenAction.OnEditClick -> {}
            }
        },
        modifier = modifier
    )
}

@Composable
fun ReviewEditScreen(
    state: ReviewEditScreenState,
    onAction: (ReviewEditScreenAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            BackBar(
                title = stringResource(R.string.title_edit_review),
                onBackClick = { onAction(ReviewEditScreenAction.OnBackClick) }
            )
        },
        bottomBar = {
            PyeonKingButton(
                text = stringResource(R.string.action_edit_review_complete),
                onClick = { onAction(ReviewEditScreenAction.OnEditClick) },
                enabled = state.isEditButtonEnabled,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)
            )
        }

    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
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
                    model = state.reviewInfo.productImgUrl,
                    contentDescription = null,
                    modifier = Modifier.size(100.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = state.reviewInfo.productName,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    StarRatingSelector(
                        rating = if (state.newStarRating == 0) state.reviewInfo.starRating else state.newStarRating,
                        onRatingChange = { onAction(ReviewEditScreenAction.OnStarRatingChanged(it)) }
                    )
                }
            }

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.prompt_edit_review),
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = state.newContent,
                    onValueChange = { onAction(ReviewEditScreenAction.OnContentChanged(it)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    placeholder = {
                        Text(
                            text = state.reviewInfo.content,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    }
                )
            }
        }
    }

}

@Preview
@Composable
private fun ReviewEditScreenPreview() {
    PyeonKingTheme {
        ReviewEditScreen(
            state = ReviewEditScreenState(),
            onAction = {}
        )
    }
}