package com.hjw0623.pyeonking.product_detail.presentation.componet

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hjw0623.pyeonking.core.presentation.ui.theme.PyeonKingYellow

@Composable
fun RatingStars(
    rating: Float,
    modifier: Modifier = Modifier,
) {
    val fullStars = rating.toInt()
    val decimalPart = rating - fullStars

    val hasHalfStar = decimalPart in 0.25f..0.8f
    val finalFullStars = fullStars
    val finalHalfStars = if (hasHalfStar) 1 else 0
    val totalFilledStars = finalFullStars + finalHalfStars
    val emptyStars = 5 - totalFilledStars

    Row(modifier = modifier) {
        repeat(finalFullStars) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = PyeonKingYellow
            )
        }
        if (hasHalfStar) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.StarHalf,
                contentDescription = null,
                tint = PyeonKingYellow
            )
        }
        repeat(emptyStars) {
            Icon(
                imageVector = Icons.Outlined.StarBorder,
                contentDescription = null,
                tint = PyeonKingYellow
            )
        }
    }
}

