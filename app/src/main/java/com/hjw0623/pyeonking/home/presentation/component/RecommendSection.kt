package com.hjw0623.pyeonking.home.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hjw0623.pyeonking.R
import com.hjw0623.pyeonking.home.data.mockHomeScreenState
import com.hjw0623.pyeonking.home.presentation.HomeScreenState
import com.hjw0623.pyeonking.ui.theme.PyeonKingTheme

@Composable
fun RecommendSection(
    state: HomeScreenState,
    onCardClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            )
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.today_recommend),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                count = state.recommendList.size,
                key = { state.recommendList[it].uuid }
            ) {
                ProductCardLarge(
                    onCardClick = { onCardClick(state.recommendList[it].name)},
                    product = state.recommendList[it]
                )
            }
        }
    }
}

@Preview
@Composable
private fun RecommendSectionPreview() {
    PyeonKingTheme {
        RecommendSection(
            onCardClick = {},
            state = mockHomeScreenState
        )
    }
}