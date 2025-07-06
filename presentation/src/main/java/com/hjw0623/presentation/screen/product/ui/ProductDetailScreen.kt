package com.hjw0623.presentation.screen.product.ui

import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hjw0623.core.domain.product.Product
import com.hjw0623.core.domain.product.ProductDetailTab
import com.hjw0623.core.mockdata.mockProduct
import com.hjw0623.core.presentation.designsystem.components.PyeonKingButton
import com.hjw0623.core.presentation.designsystem.components.TopRoundedBackground
import com.hjw0623.core.presentation.designsystem.components.showToast
import com.hjw0623.core.presentation.designsystem.theme.PyeonKingTheme
import com.hjw0623.core.presentation.ui.ObserveAsEvents
import com.hjw0623.presentation.R
import com.hjw0623.presentation.screen.product.ui.ProductDetailScreenEvent
import com.hjw0623.presentation.screen.product.ui.componet.MapTab
import com.hjw0623.presentation.screen.product.ui.componet.ProductCardDetail
import com.hjw0623.presentation.screen.product.ui.componet.RatingDistribution
import com.hjw0623.presentation.screen.product.ui.componet.RatingStars
import com.hjw0623.presentation.screen.product.ui.componet.ReviewListItem
import com.hjw0623.presentation.screen.product.ui.componet.TabSection
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

@Composable
fun ProductDetailScreenRoot(
    product: Product,
    onNavigateToReviewWrite: (Product) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var state by remember { mutableStateOf(ProductDetailScreenState(product = product)) }
    val eventFlow = remember { MutableSharedFlow<ProductDetailScreenEvent>() }

    ObserveAsEvents(flow = eventFlow) { event ->
        when (event) {
            is ProductDetailScreenEvent.Error -> {
                showToast(context, event.error)
            }

            is ProductDetailScreenEvent.NavigateToReviewWrite -> {
                onNavigateToReviewWrite(event.product)
            }
        }
    }

    ProductDetailScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is ProductDetailScreenAction.OnChangeTab -> {
                    if (state.selectedTab != action.tab) {
                        state = state.copy(selectedTab = action.tab)
                    }
                }

                ProductDetailScreenAction.OnWriteReviewClick -> {
                    scope.launch {
                        eventFlow.emit(ProductDetailScreenEvent.NavigateToReviewWrite(product))
                    }
                }
            }
        },
        modifier = modifier
    )
}


@Composable
fun ProductDetailScreen(
    state: ProductDetailScreenState,
    onAction: (ProductDetailScreenAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 70.dp)
    ) {

        item {
            TopRoundedBackground {
                state.product?.let { product ->
                    ProductCardDetail(product = product)
                }
            }
        }

        item {
            TabSection(
                selectedTab = state.selectedTab,
                onTabClick = {
                    onAction(ProductDetailScreenAction.OnChangeTab(it))
                }
            )
        }

        when (state.selectedTab) {
            ProductDetailTab.REVIEW -> {
                item {
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

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RatingStars(
                                rating = state.avgRating,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = stringResource(R.string.text_total_reviews, state.reviewSum),
                            style = MaterialTheme.typography.bodySmall
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        RatingDistribution(ratingList = state.ratingList)

                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
                items(state.reviewList.size) { index ->
                    ReviewListItem(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        review = state.reviewList[index]
                    )
                }


                item {
                    PyeonKingButton(
                        text = stringResource(R.string.action_write_review),
                        onClick = { onAction(ProductDetailScreenAction.OnWriteReviewClick) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }

            ProductDetailTab.MAP -> {
                item {
                    MapTab()
                }
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun ProductDetailScreenPreview() {
    PyeonKingTheme {
        ProductDetailScreen(
            state = ProductDetailScreenState(
                product = mockProduct
            ),
            onAction = {},
        )
    }
}
