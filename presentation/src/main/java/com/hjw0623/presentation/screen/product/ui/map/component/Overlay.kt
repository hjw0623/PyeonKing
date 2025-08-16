package com.hjw0623.presentation.screen.product.ui.map.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hjw0623.presentation.R

@Composable
fun ErrorOverlay(
    message: String,
    onRetry: () -> Unit
) {
    Surface(
        tonalElevation = 6.dp,
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.map_search_faild),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(12.dp))

            OutlinedButton(
                onClick = onRetry
            ) {
                Text(text = stringResource(R.string.map_retry))
            }
        }
    }
}

@Composable
fun EmptyResultOverlay(
    brandName: String,
    onRetry: () -> Unit
) {
    Surface(
        tonalElevation = 6.dp,
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.map_empty_poi_title),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.map_empty_poi_message, brandName),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(12.dp))

            OutlinedButton(
                onClick = onRetry
            ) {
                Text(text = stringResource(R.string.map_retry_search))
            }
        }
    }
}

@Composable
fun PermissionOverlay(
    permanentlyDenied: Boolean,
    onRequest: () -> Unit,
    onOpenSettings: () -> Unit
) {
    Surface(
        tonalElevation = 6.dp,
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.map_permission_title),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.map_permission_message),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(12.dp))

            if (permanentlyDenied) {
                Button(
                    onClick = onOpenSettings
                ) {
                    Text(text = stringResource(R.string.map_permission_open_settings))
                }
            } else {
                Button(
                    onClick = onRequest
                ) {
                    Text(text = stringResource(R.string.map_permission_request))
                }
            }
        }
    }
}

@Composable
fun MapTitleOverlay(
    title: String,
    modifier: Modifier = Modifier
) {
    Surface(
        tonalElevation = 4.dp,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
        )
    }
}