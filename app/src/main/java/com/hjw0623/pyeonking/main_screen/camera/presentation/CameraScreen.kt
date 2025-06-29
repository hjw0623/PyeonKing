package com.hjw0623.pyeonking.main_screen.camera.presentation

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.hjw0623.pyeonking.R
import com.hjw0623.pyeonking.main_screen.camera.presentation.component.CapturedImageScreen
import com.hjw0623.pyeonking.main_screen.camera.presentation.component.NoPermissionScreen
import com.hjw0623.pyeonking.main_screen.camera.presentation.component.TackingPictureScreen
import com.hjw0623.pyeonking.main_screen.camera.presentation.logic.takePictureAndSave
import com.hjw0623.pyeonking.core.data.SearchResultNavArgs
import com.hjw0623.pyeonking.core.presentation.designsystem.util.showToast
import com.hjw0623.pyeonking.core.util.ObserveAsEvents
import com.hjw0623.pyeonking.core.util.hasCameraPermission
import com.hjw0623.pyeonking.search_result.data.SearchResultSource
import com.hjw0623.pyeonking.core.presentation.ui.theme.PyeonKingTheme
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

@Composable
fun CameraScreenRoot(
    onNavigateToSearchResult: (SearchResultNavArgs) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val eventFlow = remember { MutableSharedFlow<CameraScreenEvent>() }
    val cameraController = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(CameraController.IMAGE_CAPTURE)
        }
    }

    var state by remember { mutableStateOf(CameraScreenState()) }


    ObserveAsEvents(flow = eventFlow) { event ->
        when (event) {
            is CameraScreenEvent.NavigateToSearchResult -> {
                onNavigateToSearchResult(event.searchResultNavArgs)
            }

            is CameraScreenEvent.Error -> {
                showToast(context, event.error)
            }
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            state = state.copy(hasCameraPermission = true)
        } else {
            showToast(context, context.getString(R.string.camera_permission_required))
        }
    }

    LaunchedEffect(Unit) {
        if (hasCameraPermission(context)) {
            state = state.copy(hasCameraPermission = true)
        } else {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    if (state.hasCameraPermission) {
        CameraScreen(
            modifier = modifier,
            cameraController = cameraController,
            savedImagePath = state.capturedImagePath,
            onAction = { action ->
                when (action) {
                    CameraScreenAction.OnCaptureClick -> {
                        takePictureAndSave(context, cameraController) { path ->
                            state = state.copy(capturedImagePath = path)
                        }
                    }

                    CameraScreenAction.OnRetakeClick -> {
                        state = state.copy(capturedImagePath = null)
                    }

                    is CameraScreenAction.OnSearchClick -> {
                        scope.launch {
                            val path = state.capturedImagePath
                            if (path != null) {
                                eventFlow.emit(
                                    CameraScreenEvent.NavigateToSearchResult(
                                        SearchResultNavArgs(
                                            source = SearchResultSource.CAMERA,
                                            passedQuery = "",
                                            passedImagePath = path
                                        )
                                    )
                                )
                            } else {
                                eventFlow.emit(CameraScreenEvent.Error("이미지가 없습니다."))
                            }
                        }
                    }

                    CameraScreenAction.OnRequestCameraPermission -> {
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                }
            },
        )
    } else {
        NoPermissionScreen(
            modifier = modifier,
            onRequestPermission = {
                permissionLauncher.launch(Manifest.permission.CAMERA)
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
    if (savedImagePath != null) {
        CapturedImageScreen(
            modifier = modifier.fillMaxSize(),
            imagePath = savedImagePath,
            onRetake = {
                onAction(CameraScreenAction.OnRetakeClick)
            },
            onSearchClick = {
                onAction(CameraScreenAction.OnSearchClick)
            }
        )
    } else {
        TackingPictureScreen(
            modifier = modifier.fillMaxSize(),
            cameraController = cameraController,
            onAction = onAction
        )
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
