package com.hjw0623.presentation.screen.search.text_search.ui.component.unFocused

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hjw0623.core.domain.model.search.FilterType
import com.hjw0623.core.ui.designsystem.theme.PyeonKingTheme
import com.hjw0623.presentation.R

@Composable
fun CategoryBar(
    selectedFilters: Map<FilterType, Boolean>,
    onFilterToggle: (FilterType) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        FilterRowSection(
            label = stringResource(R.string.label_convenience_store),
            filters = listOf(
                FilterType.CU,
                FilterType.GS25,
                FilterType.EMART24,
                FilterType.SEVEN
            ),
            selectedFilters = selectedFilters,
            onFilterToggle = onFilterToggle
        )

        Spacer(modifier = Modifier.height(12.dp))

        FilterRowSection(
            label = stringResource(R.string.label_promotion_type),
            filters = listOf(
                FilterType.ONE_PLUS_ONE,
                FilterType.TWO_PLUS_ONE
            ),
            selectedFilters = selectedFilters,
            onFilterToggle = onFilterToggle
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun CategoryBarPreview() {
    val mockSelectedFilters = mapOf(
        FilterType.CU to true,
        FilterType.GS25 to false,
        FilterType.EMART24 to true,
        FilterType.SEVEN to false,
        FilterType.ONE_PLUS_ONE to true,
        FilterType.TWO_PLUS_ONE to false
    )
    PyeonKingTheme {
        CategoryBar(
            selectedFilters = mockSelectedFilters,
            onFilterToggle = {}
        )
    }
}