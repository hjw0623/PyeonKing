package com.hjw0623.presentation.screen.product.ui.componet

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.hjw0623.core.core_domain.model.product.ProductDetailTab
import com.hjw0623.presentation.R


@Composable
fun TabSection(
    selectedTab: ProductDetailTab,
    onTabClick: (ProductDetailTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    val tabs = listOf(ProductDetailTab.MAP, ProductDetailTab.REVIEW)
    TabRow(
        selectedTabIndex = tabs.indexOf(selectedTab),
        modifier = modifier.fillMaxWidth()
    ) {
        tabs.forEach { tab ->
            Tab(
                selected = selectedTab == tab,
                onClick = { onTabClick(tab) },
                text = {
                    Text(
                        text = when (tab) {
                            ProductDetailTab.REVIEW -> stringResource(R.string.label_review)
                            ProductDetailTab.MAP -> stringResource(R.string.label_map)
                        }
                    )
                }
            )
        }
    }

}