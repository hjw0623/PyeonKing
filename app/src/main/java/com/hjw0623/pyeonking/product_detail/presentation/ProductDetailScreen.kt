package com.hjw0623.pyeonking.product_detail.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.hjw0623.pyeonking.core.data.mockProduct
import com.hjw0623.pyeonking.core.presentation.designsystem.util.BackBar
import com.hjw0623.pyeonking.core.presentation.designsystem.util.TopRoundedBackground
import com.hjw0623.pyeonking.product_detail.data.ProductDetailTab
import com.hjw0623.pyeonking.product_detail.presentation.componet.MapTab
import com.hjw0623.pyeonking.product_detail.presentation.componet.ProductCardDetail
import com.hjw0623.pyeonking.product_detail.presentation.componet.ReviewTab
import com.hjw0623.pyeonking.product_detail.presentation.componet.TabSection
import com.hjw0623.pyeonking.ui.theme.PyeonKingTheme

@Composable
fun ProductDetailScreenRoot(
    modifier: Modifier = Modifier,
) {
    var state by remember { mutableStateOf(ProductDetailState()) }
    ProductDetailScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is ProductDetailAction.OnChangeTab -> {
                    if (
                        state.selectedTab == action.tab
                    ) return@ProductDetailScreen
                    state = state.copy(
                        selectedTab = action.tab
                    )
                }

                ProductDetailAction.OnWriteReviewClick -> {

                }
            }
        },
        modifier = modifier
    )
}


@Composable
fun ProductDetailScreen(
    state: ProductDetailState,
    onAction: (ProductDetailAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        BackBar(
            onBackClick = {
            },
            iconTint = MaterialTheme.colorScheme.onPrimary,
            backgroundColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(Alignment.TopStart)
                .zIndex(1f)
        )
        TopRoundedBackground {}
        Column(
            modifier = modifier
                .padding(top = 24.dp)
        ) {
            state.product?.let { product ->
                ProductCardDetail(
                    product = product
                )
            }

            TabSection(
                selectedTab = state.selectedTab,
                onTabClick = {
                    onAction(ProductDetailAction.OnChangeTab(it))
                },
            )

            when (state.selectedTab) {
                ProductDetailTab.REVIEW -> ReviewTab(
                    avgRating = state.avgRating,
                    ratingList = state.ratingList,
                    totalReviewSum = state.reviewSum,
                    reviewList = state.reviewList,
                    onWriteReviewClick = {
                        onAction(ProductDetailAction.OnWriteReviewClick)
                    }
                )

                ProductDetailTab.MAP -> MapTab()
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
            state = ProductDetailState(
                product = mockProduct
            ),
            onAction = {}
        )
    }
}
