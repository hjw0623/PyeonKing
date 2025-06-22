package com.hjw0623.pyeonking.home.presentation.component

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hjw0623.pyeonking.R
import com.hjw0623.pyeonking.core.data.Product
import com.hjw0623.pyeonking.core.data.mockProduct
import com.hjw0623.pyeonking.core.util.getBrandColor
import com.hjw0623.pyeonking.ui.theme.PyeonKingTheme

@Composable
fun ProductCardLarge(
    recommendItem: Product,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick =  { onCardClick() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = recommendItem.imgUrl,
                contentDescription = null,
                modifier = Modifier.size(160.dp),
                contentScale = ContentScale.Crop,
            )
        }
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            ProductCardTextLarge(string = recommendItem.name)
            ProductCardTextLarge(string = recommendItem.promotion)
            ProductCardTextLarge(
                string = stringResource(
                    R.string.price_format,
                    recommendItem.price,
                    recommendItem.priceForEach
                )
            )
            ProductCardTextLarge(
                string = recommendItem.brand,
                color = getBrandColor(
                    brand = recommendItem.brand,
                    isDarkTheme = isSystemInDarkTheme()
                )
            )
        }
    }
}

@Preview
@Composable
private fun ProductCardPreview() {
    PyeonKingTheme {
        ProductCardLarge(
            onCardClick = {},
            recommendItem = mockProduct
        )
    }
}