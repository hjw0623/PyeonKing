package com.hjw0623.presentation.screen.product.ui.componet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

import com.hjw0623.core.core_domain.model.product.Product
import com.hjw0623.core.core_andriod.util.mockdata.mockProduct
import com.hjw0623.core.core_ui.designsystem.theme.PyeonKingTheme
import com.hjw0623.core.core_ui.ui.getBrandColor
import com.hjw0623.presentation.R
import com.hjw0623.presentation.util.CoilImageRequest

@Composable
fun ProductCardDetail(
    product: Product,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    Card(
        modifier = modifier
            .padding(60.dp)
            .fillMaxWidth()
            .height(400.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.padding(40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                AsyncImage(
                    model = CoilImageRequest.getImageRequest(
                        context = context,
                        sourceImage = product.imgUrl
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(120.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = product.promotion,
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
                            .padding(horizontal = 18.dp, vertical = 4.dp),
                        color = Color.White
                    )
                    Text(
                        text = product.brand,
                        modifier = Modifier
                            .border(
                                1.dp,
                                getBrandColor(product.brand, isDarkTheme = isSystemInDarkTheme()),
                                RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 18.dp, vertical = 4.dp),
                        color = getBrandColor(product.brand, isDarkTheme = isSystemInDarkTheme())
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(stringResource(R.string.label_unit_price, product.price))
                Text(
                    stringResource(
                        R.string.label_unit_price_with_promotion,
                        product.promotion,
                        product.priceForEach
                    ),
                    fontWeight = FontWeight.Bold
                )
            }

        }
    }
}

@Preview
@Composable
private fun ProductCardDetailPreview() {
    PyeonKingTheme {
        ProductCardDetail(
            product = mockProduct
        )
    }
}