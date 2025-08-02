package com.hjw0623.presentation.screen.product.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hjw0623.core.business_logic.model.product.Product
import com.hjw0623.core.business_logic.model.product.ProductDetailTab
import com.hjw0623.core.business_logic.model.product.ReviewItem
import com.hjw0623.core.presentation.designsystem.components.PyeonKingButton
import com.hjw0623.core.presentation.designsystem.components.TopRoundedBackground
import com.hjw0623.core.presentation.designsystem.components.showToast
import com.hjw0623.core.presentation.designsystem.theme.PyeonKingTheme
import com.hjw0623.core.presentation.ui.ObserveAsEvents
import com.hjw0623.core.presentation.ui.rememberThrottledOnClick
import com.hjw0623.core.util.mockdata.mockProduct
import com.hjw0623.core.util.mockdata.mockReviewList
import com.hjw0623.presentation.R
import com.hjw0623.presentation.screen.product.ui.componet.MapTab
import com.hjw0623.presentation.screen.product.ui.componet.ProductCardDetail
import com.hjw0623.presentation.screen.product.ui.componet.RatingDistribution
import com.hjw0623.presentation.screen.product.ui.componet.RatingStars
import com.hjw0623.presentation.screen.product.ui.componet.ReviewListItem
import com.hjw0623.presentation.screen.product.ui.componet.TabSection
import com.hjw0623.presentation.screen.product.viewmodel.ProductViewModel

@Composable
fun ProductDetailScreenRoot(
    product: Product,
    productViewModel: ProductViewModel,
    onNavigateToReviewWrite: (Product) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val viewModel = productViewModel

    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val productFromVm by viewModel.product.collectAsStateWithLifecycle()
    val selectedTab by viewModel.selectedTab.collectAsStateWithLifecycle()
    val reviewList by viewModel.reviewListUi.collectAsStateWithLifecycle()
    val ratingList by viewModel.ratingList.collectAsStateWithLifecycle()
    val avgRating by viewModel.avgRating.collectAsStateWithLifecycle()
    val reviewSum by viewModel.reviewSum.collectAsStateWithLifecycle()
    val currentPage by viewModel.currentPage.collectAsStateWithLifecycle()
    val lastPage by viewModel.lastPage.collectAsStateWithLifecycle()

    val throttledWriteReviewClick = rememberThrottledOnClick {
        viewModel.onWriteReviewClick()
    }
    val throttledFetchNextReviewPage = rememberThrottledOnClick {
        viewModel.fetchNextReviewPage(product.id.toLong())
    }

    LaunchedEffect(key1 = product) {
        viewModel.initializeIfNeeded(product)
    }

    ObserveAsEvents(flow = viewModel.event) { event ->
        when (event) {
            is ProductDetailScreenEvent.Error -> showToast(context, event.error)
            is ProductDetailScreenEvent.NavigateToReviewWrite -> onNavigateToReviewWrite(event.product)
        }
    }

    ProductDetailScreen(
        modifier = modifier,
        isLoading = isLoading,
        product = productFromVm,
        selectedTab = selectedTab,
        reviewList = reviewList,
        ratingList = ratingList,
        avgRating = avgRating,
        reviewSum = reviewSum,
        currentPage = currentPage,
        lastPage = lastPage,
        onTabClick = viewModel::changeTab,
        onWriteReviewClick = throttledWriteReviewClick,
        fetchNextReviewPage = throttledFetchNextReviewPage
    )
}

@Composable
fun ProductDetailScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    product: Product?,
    selectedTab: ProductDetailTab,
    reviewList: List<ReviewItem>,
    ratingList: List<Int>,
    avgRating: Double,
    reviewSum: Int,
    currentPage: Int,
    lastPage: Int,
    onTabClick: (ProductDetailTab) -> Unit,
    onWriteReviewClick: () -> Unit,
    fetchNextReviewPage: () -> Unit
) {


    Box(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
            contentPadding = PaddingValues(bottom = 24.dp),
        ) {
            item {
                TopRoundedBackground {
                    product?.let { ProductCardDetail(product = it) }
                }
            }

            item {
                TabSection(
                    selectedTab = selectedTab,
                    onTabClick = onTabClick
                )
            }

            when (selectedTab) {
                ProductDetailTab.REVIEW -> {
                    if (isLoading) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 100.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    } else {
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = avgRating.toString(),
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    RatingStars(
                                        rating = avgRating.toFloat(),
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = stringResource(R.string.text_total_reviews, reviewSum),
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                RatingDistribution(
                                    ratingList = ratingList.mapIndexed { index, count -> (5 - index) to count },
                                )
                                Spacer(modifier = Modifier.height(24.dp))
                            }
                        }

                        if (reviewList.isNotEmpty()) {
                            items(
                                items = reviewList,
                                key = { it.reviewId }
                            ) { review ->
                                ReviewListItem(
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    review = review
                                )
                            }

                            if (currentPage != lastPage) {
                                item {
                                    PyeonKingButton(
                                        text = stringResource(
                                            R.string.action_see_more_review,
                                            currentPage,
                                            lastPage
                                        ),
                                        onClick = {
                                            fetchNextReviewPage()
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp)
                                    )
                                }
                            }
                        }

                        item { Spacer(modifier = Modifier.height(16.dp)) }
                    }
                }

                ProductDetailTab.MAP -> {
                    item {
                        MapTab(
                            brandName = product?.brand ?: "",
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            PyeonKingButton(
                text = stringResource(R.string.action_write_review),
                onClick = onWriteReviewClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ProductDetailScreenPreview() {
    PyeonKingTheme {
        ProductDetailScreen(
            isLoading = false,
            product = mockProduct,
            selectedTab = ProductDetailTab.REVIEW,
            reviewList = mockReviewList,
            ratingList = listOf(80, 20, 10, 5, 10),
            avgRating = 4.3,
            reviewSum = 125,
            onTabClick = {},
            onWriteReviewClick = {},
            fetchNextReviewPage = {},
            currentPage = 1,
            lastPage = 2,
        )
    }
}