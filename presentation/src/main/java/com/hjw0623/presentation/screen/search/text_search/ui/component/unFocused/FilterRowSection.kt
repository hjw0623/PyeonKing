package com.hjw0623.presentation.screen.search.text_search.ui.component.unFocused

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hjw0623.core.domain.search.text_search.FilterType
import com.hjw0623.presentation.R

@Composable
fun FilterRowSection(
    label: String,
    filters: List<FilterType>,
    selectedFilters: Map<FilterType, Boolean>,
    onFilterToggle: (FilterType) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .width(80.dp)
                .padding(top = 6.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            filters.forEach { type ->
                FilterToggleButton(
                    text = when (type) {
                        FilterType.CU -> stringResource(R.string.label_cu)
                        FilterType.GS25 -> stringResource(R.string.label_gs25)
                        FilterType.EMART24 -> stringResource(R.string.label_emart24)
                        FilterType.SEVEN -> stringResource(R.string.label_seven_eleven)
                        FilterType.ONE_PLUS_ONE -> stringResource(R.string.label_one_plus_one)
                        FilterType.TWO_PLUS_ONE -> stringResource(R.string.label_two_plus_one)
                    },
                    isSelected = selectedFilters[type] == true,
                    onToggle = { onFilterToggle(type) }
                )
            }
        }
    }
}
