package com.hjw0623.pyeonking.camera.presentation

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.hjw0623.pyeonking.R
import com.hjw0623.pyeonking.camera.presentation.component.CapturedImageScreen
import com.hjw0623.pyeonking.camera.presentation.component.NoPermissionScreen
import com.hjw0623.pyeonking.camera.presentation.component.TackingPictureScreen
import com.hjw0623.pyeonking.camera.presentation.logic.takePictureAndSave
import com.hjw0623.pyeonking.core.presentation.designsystem.util.showToast
import com.hjw0623.pyeonking.core.util.hasCameraPermission
import com.hjw0623.pyeonking.ui.theme.PyeonKingTheme


@Composable
fun CameraScreenRoot(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var state by remember { mutableStateOf(CameraScreenState()) }

    val cameraController = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(CameraController.IMAGE_CAPTURE)
        }
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (!granted) {
            showToast(
                context = context,
                message = context.getString(R.string.camera_permission_required),
                duration = Toast.LENGTH_LONG
            )
        } else {
            state = state.copy(hasCameraPermission = true)
        }
    }

    LaunchedEffect(Unit) {
        val cameraGranted = hasCameraPermission(context)

        state = state.copy(
            hasCameraPermission = cameraGranted,
        )

        if (cameraGranted){
            state = state.copy(hasCameraPermission = true)
        } else {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }
    if (state.hasCameraPermission) {
        CameraScreen(
            modifier = modifier,
            cameraController = cameraController,
            onAction = { action ->
                when(action) {
                    CameraScreenAction.OnCaptureClick -> {
                        takePictureAndSave(
                            context, cameraController,
                            onImageSaved = { imagePath ->
                                state = state.copy(capturedImagePath = imagePath)
                            }
                        )
                    }
                    CameraScreenAction.OnBackClick -> {

                    }
                    CameraScreenAction.OnRetakeClick -> {

                    }
                    CameraScreenAction.OnSearchClick -> {

                    }
                    else -> Unit
                }
            },
            savedImagePath = state.capturedImagePath
        )
    } else {
        NoPermissionScreen(
            modifier = modifier,
            onRequestPermission = { action ->
                when (action) {
                    CameraScreenAction.OnRequestCameraPermission -> {
                        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                    else -> Unit
                }
            }
        )

    }
}

@Composable
fun CameraScreen(
    cameraController: LifecycleCameraController,
    onAction: (CameraScreenAction) -> Unit,
    modifier: Modifier = Modifier,
    savedImagePath: String?
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        if (savedImagePath != null) {
            CapturedImageScreen(
                imagePath = savedImagePath,
                onRetake = {
                    onAction(CameraScreenAction.OnRetakeClick)
                },
                onSearchClick = {
                    onAction(CameraScreenAction.OnSearchClick)
                },
                onBackClick = {
                    onAction(CameraScreenAction.OnBackClick)
                }
            )
        } else {
            TackingPictureScreen(
                cameraController = cameraController,
                onAction = onAction
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CameraScreenPreview() {
    PyeonKingTheme {
        CameraScreen(
            cameraController = LifecycleCameraController(LocalContext.current),
            onAction = {},
            savedImagePath = null
        )
    }
}
