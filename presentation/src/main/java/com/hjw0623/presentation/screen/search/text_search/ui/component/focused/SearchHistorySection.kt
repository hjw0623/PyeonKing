package com.hjw0623.presentation.screen.search.text_search.ui.component.focused

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hjw0623.core.presentation.designsystem.theme.PyeonKingTheme
import com.hjw0623.presentation.R

@Composable
fun SearchHistorySection(
    searchHistory: List<String>,
    onHistoryClick: (String) -> Unit,
    onRemoveClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = stringResource(R.string.label_recent_searches),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        if (searchHistory.isEmpty()) {
            Text(
                text = stringResource(R.string.message_no_recent_searches),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            FlowRow(
                modifier = Modifier.padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                searchHistory.forEach { name ->
                    SearchHistoryChip(
                        text = name,
                        onClick = { onHistoryClick(name) },
                        onRemove = { onRemoveClick(name) }
                    )
                }
            }
        }
    }
}


@Preview
@Composable
private fun SearchHistoryScreenPreview() {
    PyeonKingTheme {
        SearchHistorySection(
            searchHistory = listOf(
                "콜라",
                "사이다",
                "하늘 보리",
                "블래 보리"
            ),
            onHistoryClick = {},
            onRemoveClick = {}
        )
    }
}