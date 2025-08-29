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
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hjw0623.core.domain.model.product.Product
import com.hjw0623.core.domain.model.product.ProductDetailTab
import com.hjw0623.core.ui.designsystem.components.PyeonKingButton
import com.hjw0623.core.ui.designsystem.components.TopRoundedBackground
import com.hjw0623.core.ui.designsystem.components.showToast
import com.hjw0623.core.ui.designsystem.theme.PyeonKingTheme
import com.hjw0623.core.ui.util.ObserveAsEvents
import com.hjw0623.core.ui.util.rememberThrottledOnClick
import com.hjw0623.core.android.util.mockdata.mockProduct
import com.hjw0623.core.android.util.mockdata.mockReviewList
import com.hjw0623.presentation.R
import com.hjw0623.presentation.screen.home.ui.component.LoginPrompt
import com.hjw0623.presentation.screen.product.ui.componet.EmptyReview
import com.hjw0623.presentation.screen.product.ui.componet.ProductCardDetail
import com.hjw0623.presentation.screen.product.ui.componet.RatingDistribution
import com.hjw0623.presentation.screen.product.ui.componet.RatingStars
import com.hjw0623.presentation.screen.product.ui.componet.ReviewListItem
import com.hjw0623.presentation.screen.product.ui.componet.TabSection
import com.hjw0623.presentation.screen.product.ui.map.MapTab
import com.hjw0623.presentation.screen.product.viewmodel.ProductViewModel

@Composable
fun ProductDetailScreenRoot(
    modifier: Modifier = Modifier,
    product: Product,
    productViewModel: ProductViewModel = hiltViewModel(),
    onNavigateToReviewWrite: (Product) -> Unit
) {
    val context = LocalContext.current
    val state by productViewModel.state.collectAsStateWithLifecycle()

    val throttledWriteReviewClick = rememberThrottledOnClick {
        productViewModel.onWriteReviewClick()
    }

    val throttledFetchNextReviewPage = rememberThrottledOnClick {
        state.product?.id?.toLongOrNull()?.let { safeId ->
            productViewModel.fetchNextReviewPage(safeId)
        }
    }

    LaunchedEffect(product) {
        productViewModel.initializeIfNeeded(product)
    }

    ObserveAsEvents(flow = productViewModel.event) { event ->
        when (event) {
            is ProductDetailScreenEvent.Error -> showToast(context, event.error)
            is ProductDetailScreenEvent.NavigateToReviewWrite -> onNavigateToReviewWrite(event.product)
        }
    }

    ProductDetailScreen(
        modifier = modifier,
        state = state,
        onTabClick = productViewModel::changeTab,
        onWriteReviewClick = throttledWriteReviewClick,
        fetchNextReviewPage = throttledFetchNextReviewPage
    )
}

@Composable
fun ProductDetailScreen(
    modifier: Modifier = Modifier,
    state: ProductDetailScreenState,
    onTabClick: (ProductDetailTab) -> Unit,
    onWriteReviewClick: () -> Unit,
    fetchNextReviewPage: () -> Unit
) {
    val listState = rememberLazyListState()

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
            contentPadding = PaddingValues(bottom = 24.dp),
        ) {
            item {
                TopRoundedBackground {
                    state.product?.let { ProductCardDetail(product = it) }
                }
            }

            item {
                TabSection(
                    selectedTab = state.selectedTab,
                    onTabClick = onTabClick
                )
            }

            when (state.selectedTab) {
                ProductDetailTab.REVIEW -> {
                    if (!state.isLoggedIn) {
                        item {
                            LoginPrompt(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .height(500.dp),
                                title = stringResource(R.string.review_title_require_login),
                                message = stringResource(R.string.review_message_require_login)
                            )
                        }
                    } else {
                        item {
                            if (state.isSummaryLoading) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            } else {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {
                                    Text(
                                        text = state.avgRating.toString(),
                                        style = MaterialTheme.typography.headlineSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        RatingStars(
                                            rating = state.avgRating.toFloat(),
                                            modifier = Modifier.weight(1f)
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = stringResource(
                                            R.string.text_total_reviews,
                                            state.reviewSum
                                        ),
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    RatingDistribution(
                                        ratingList = state.ratingList.mapIndexed { idx, cnt -> (5 - idx) to cnt },
                                    )
                                    Spacer(modifier = Modifier.height(24.dp))
                                }
                            }
                        }

                        if (state.isReviewLoading && state.reviewList.isEmpty()) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 100.dp),
                                    contentAlignment = Alignment.Center
                                ) { CircularProgressIndicator() }
                            }
                        } else {
                            if (state.reviewList.isEmpty()) {
                                item {
                                    EmptyReview(
                                        modifier = Modifier.padding(horizontal = 16.dp),
                                        title = stringResource(R.string.text_no_reviews),
                                        description = stringResource(R.string.empty_review_description)
                                    )
                                }
                            } else {
                                items(
                                    items = state.reviewList,
                                    key = { it.reviewId }
                                ) { review ->
                                    ReviewListItem(
                                        modifier = Modifier.padding(horizontal = 16.dp),
                                        review = review
                                    )
                                }

                                if (state.currentPage != state.lastPage) {
                                    item {
                                        PyeonKingButton(
                                            text =
                                                if (state.isReviewLoading)
                                                    stringResource(R.string.loading)
                                                else
                                                    stringResource(
                                                        R.string.action_see_more_review,
                                                        state.currentPage,
                                                        state.lastPage
                                                    ),
                                            onClick = fetchNextReviewPage,
                                            enabled = !state.isReviewLoading,
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
                }

                ProductDetailTab.MAP -> {
                    item {
                        MapTab(
                            brandName = state.product?.brand ?: "",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }

        if (state.selectedTab == ProductDetailTab.REVIEW) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                if (state.isLoggedIn) {
                    PyeonKingButton(
                        text = stringResource(R.string.action_write_review),
                        onClick = onWriteReviewClick,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductDetailScreenPreview() {
    PyeonKingTheme {
        ProductDetailScreen(
            state = ProductDetailScreenState(
                isSummaryLoading = false,
                isReviewLoading = false,
                product = mockProduct,
                selectedTab = ProductDetailTab.REVIEW,
                reviewList = mockReviewList,
                ratingList = listOf(80, 20, 10, 5, 10),
                avgRating = 4.3,
                reviewSum = 125,
                isLoggedIn = false
            ),
            onTabClick = {},
            onWriteReviewClick = {},
            fetchNextReviewPage = {}
        )
    }
}