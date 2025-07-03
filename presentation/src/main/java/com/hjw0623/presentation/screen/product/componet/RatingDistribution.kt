package com.hjw0623.presentation.screen.product.componet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hjw0623.presentation.R


@Composable
fun RatingDistribution(
    ratingList: List<Pair<Int, Int>>,
    modifier: Modifier = Modifier,
) {
    val totalCount = ratingList.sumOf { it.second }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        (5 downTo 1).forEach { rating ->
            val count = ratingList.find { it.first == rating }?.second ?: 0
            val progress = if (totalCount > 0) count.toFloat() / totalCount else 0f

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "$rating",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.width(24.dp)
                )

                CustomProgressBar(
                    progress = progress,
                    modifier = Modifier
                        .width(300.dp)
                        .height(6.dp)
                        .padding(horizontal = 8.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.text_item_count, count),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
