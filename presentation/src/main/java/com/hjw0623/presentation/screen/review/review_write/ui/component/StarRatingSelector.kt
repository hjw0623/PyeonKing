package com.hjw0623.presentation.screen.review.review_write.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.hjw0623.core.core_ui.designsystem.theme.PyeonKingYellow

@Composable
fun StarRatingSelector(
    modifier: Modifier = Modifier,
    rating: Int,
    stars: Int = 5,
    starColor: Color = PyeonKingYellow,
    onRatingChange: (Int) -> Unit,
) {
    Row {
        for (index in 1..stars) {
            Icon(
                modifier = modifier.clickable { onRatingChange(index) },
                tint = starColor,
                imageVector = if (index <= rating) Icons.Outlined.Star else Icons.Outlined.StarBorder,
                contentDescription = null,
            )
        }
    }
}



