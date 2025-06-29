package com.hjw0623.pyeonking.camera.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hjw0623.pyeonking.R
import com.hjw0623.pyeonking.core.presentation.designsystem.util.PyeonKingButton
import com.hjw0623.pyeonking.ui.theme.PyeonKingTheme
import java.io.File

@Composable
fun CapturedImageScreen(
    imagePath: String,
    onRetake: () -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.Black)
        ) {
            AsyncImage(
                model = File(imagePath),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                PyeonKingButton(
                    text = stringResource(R.string.action_retake_photo),
                    onClick = onRetake,
                )

                PyeonKingButton(
                    text = stringResource(R.string.action_search),
                    onClick = onSearchClick
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CapturedImageScreenPreview() {
    PyeonKingTheme {
        CapturedImageScreen(
            imagePath = "",
            onRetake = {},
            onSearchClick = {},
        )
    }
}