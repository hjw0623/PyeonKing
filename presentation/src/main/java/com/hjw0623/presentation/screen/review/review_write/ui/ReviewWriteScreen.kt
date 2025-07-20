package com.hjw0623.presentation.screen.review.review_write.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.hjw0623.core.domain.product.Product
import com.hjw0623.core.presentation.designsystem.components.PyeonKingButton
import com.hjw0623.core.presentation.designsystem.components.showToast
import com.hjw0623.core.presentation.designsystem.theme.PyeonKingTheme
import com.hjw0623.core.presentation.ui.ObserveAsEvents
import com.hjw0623.core.presentation.ui.rememberThrottledOnClick
import com.hjw0623.core.util.mockdata.mockProduct
import com.hjw0623.presentation.R
import com.hjw0623.presentation.screen.review.review_write.ui.component.StarRatingSelector
import com.hjw0623.presentation.screen.review.review_write.ui.component.WritingSection
import com.hjw0623.presentation.screen.review.viewmodel.ReviewWriteViewModel

@Composable
fun ReviewWriteScreenRoot(
    modifier: Modifier = Modifier,
    product: Product,
    reviewWriteViewModel: ReviewWriteViewModel,
    onReviewWriteComplete: () -> Unit
) {
    val context = LocalContext.current
    val viewModel = reviewWriteViewModel

    val productState by viewModel.product.collectAsStateWithLifecycle()
    val rating by viewModel.rating.collectAsStateWithLifecycle()
    val content by viewModel.content.collectAsStateWithLifecycle()
    val isSubmitButtonEnabled by viewModel.isSubmitButtonEnabled.collectAsStateWithLifecycle()

    val throttledSubmitClick = rememberThrottledOnClick {
        viewModel.onSubmitClick()
    }

    LaunchedEffect(key1 = product) {
        viewModel.init(product)
    }

    ObserveAsEvents(flow = viewModel.event) { event ->
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
        modifier = modifier,
        product = productState,
        rating = rating,
        content = content,
        isSubmitButtonEnabled = isSubmitButtonEnabled,
        onRatingChange = viewModel::onRatingChange,
        onContentChange = viewModel::onContentChange,
        onSubmitClick = throttledSubmitClick
    )
}

@Composable
fun ReviewWriteScreen(
    modifier: Modifier = Modifier,
    product: Product?,
    rating: Int,
    content: String,
    isSubmitButtonEnabled: Boolean,
    onRatingChange: (Int) -> Unit,
    onContentChange: (String) -> Unit,
    onSubmitClick: () -> Unit
) {
    if (product == null) {
        return
    }

    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            AsyncImage(
                model = product.imgUrl,
                contentDescription = null,
                fallback = painterResource(com.hjw0623.core.R.drawable.no_image),
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                )
                StarRatingSelector(
                    rating = rating,
                    onRatingChange = onRatingChange
                )
            }
        }

        WritingSection(
            content = content,
            onContentChange = onContentChange
        )

        Spacer(modifier = Modifier.weight(1f))

        PyeonKingButton(
            text = stringResource(R.string.action_submit),
            onClick = onSubmitClick,
            enabled = isSubmitButtonEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 32.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ReviewWriteScreenPreview() {
    PyeonKingTheme {
        ReviewWriteScreen(
            product = mockProduct,
            rating = 4,
            content = "이 편의점 조합 정말 맛있네요. 추천합니다!",
            isSubmitButtonEnabled = true,
            onRatingChange = {},
            onContentChange = {},
            onSubmitClick = {}
        )
    }
}