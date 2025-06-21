package com.hjw0623.pyeonking.camera.presentation.component

import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hjw0623.pyeonking.camera.presentation.CameraScreenAction
import com.hjw0623.pyeonking.ui.theme.PyeonKingTheme


@Composable
fun TackingPictureScreen(
    cameraController: LifecycleCameraController,
    onAction: (CameraScreenAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Color.Black)
            )

            CameraPreview(
                controller = cameraController,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                CaptureButton(
                    onClick = {
                        onAction(CameraScreenAction.OnCaptureClick)
                    },
                    modifier = Modifier.size(80.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun ScreenPreview() {
    PyeonKingTheme {
        TackingPictureScreen(
            cameraController = LifecycleCameraController(LocalContext.current),
            onAction = {}
        )
    }
}