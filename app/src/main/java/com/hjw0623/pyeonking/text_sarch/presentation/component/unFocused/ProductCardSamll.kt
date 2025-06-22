package com.hjw0623.pyeonking.text_sarch.presentation.component.unFocused

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hjw0623.pyeonking.R
import com.hjw0623.pyeonking.core.data.Product
import com.hjw0623.pyeonking.core.data.mockProduct
import com.hjw0623.pyeonking.core.util.getBrandColor
import com.hjw0623.pyeonking.ui.theme.PyeonKingTheme

@Composable
fun ProductCardSmall(
    product: Product,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Surface(
        tonalElevation = 1.dp,
        shadowElevation = 3.dp,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, Color.LightGray, shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp)),
        onClick = { onClick() }
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(vertical = 22.dp, horizontal = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.brand,
                    color = getBrandColor(product.brand, isSystemInDarkTheme()),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = product.promotion,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = stringResource(
                        R.string.price_format,
                        product.price,
                        product.priceForEach
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            AsyncImage(
                model = product.imgUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductCardSmallPreview() {
    PyeonKingTheme {
        ProductCardSmall(
            onClick = {},
            product = mockProduct
        )
    }
}