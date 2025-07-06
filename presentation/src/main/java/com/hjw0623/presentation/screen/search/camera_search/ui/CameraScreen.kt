package com.hjw0623.presentation.screen.search.camera_search.ui

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hjw0623.core.domain.search.camera_search.takePictureAndSave
import com.hjw0623.core.domain.search.search_result.SearchResultNavArgs
import com.hjw0623.core.presentation.designsystem.components.showToast
import com.hjw0623.core.presentation.designsystem.theme.PyeonKingTheme
import com.hjw0623.core.presentation.ui.ObserveAsEvents
import com.hjw0623.core.presentation.ui.hasCameraPermission
import com.hjw0623.core.presentation.ui.rememberThrottledOnClick
import com.hjw0623.presentation.R
import com.hjw0623.presentation.screen.factory.CameraSearchViewModelFactory
import com.hjw0623.presentation.screen.search.camera_search.ui.component.CapturedImageScreen
import com.hjw0623.presentation.screen.search.camera_search.ui.component.NoPermissionScreen
import com.hjw0623.presentation.screen.search.camera_search.ui.component.TackingPictureScreen
import com.hjw0623.presentation.screen.search.viewmodel.CameraSearchViewModel

@Composable
fun CameraScreenRoot(
    onNavigateToSearchResult: (SearchResultNavArgs) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val cameraSearchViewModelFactory = CameraSearchViewModelFactory()
    val viewModel: CameraSearchViewModel = viewModel(factory = cameraSearchViewModelFactory)

    val hasPermission by viewModel.hasCameraPermission.collectAsStateWithLifecycle()
    val capturedImagePath by viewModel.capturedImagePath.collectAsStateWithLifecycle()

    val cameraController = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(CameraController.IMAGE_CAPTURE)
        }
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        viewModel.onPermissionResult(granted)
        if (!granted) {
            showToast(context, context.getString(R.string.camera_permission_required))
        }
    }

    val throttledRetakeClick = rememberThrottledOnClick(onClick = viewModel::onRetakeClick)
    val throttledSearchClick = rememberThrottledOnClick(onClick = viewModel::onSearchClick)

    LaunchedEffect(Unit) {
        val isGranted = hasCameraPermission(context)
        viewModel.onPermissionResult(isGranted)
        if (!isGranted) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    ObserveAsEvents(flow = viewModel.event) { event ->
        when (event) {
            is CameraScreenEvent.NavigateToSearchResult -> {
                onNavigateToSearchResult(event.searchResultNavArgs)
            }

            is CameraScreenEvent.Error -> {
                showToast(context, event.error)
            }
        }
    }

    if (hasPermission) {
        CameraScreen(
            modifier = modifier,
            cameraController = cameraController,
            capturedImagePath = capturedImagePath,
            onCaptureClick = {
                takePictureAndSave(context, cameraController) { path ->
                    viewModel.onPhotoTaken(path)
                }
            },
            onRetakeClick = throttledRetakeClick,
            onSearchClick = throttledSearchClick
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
    capturedImagePath: String?,
    onCaptureClick: () -> Unit,
    onRetakeClick: () -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (capturedImagePath != null) {
        CapturedImageScreen(
            modifier = modifier.fillMaxSize(),
            imagePath = capturedImagePath,
            onRetake = onRetakeClick,
            onSearchClick = onSearchClick
        )
    } else {
        TackingPictureScreen(
            modifier = modifier.fillMaxSize(),
            cameraController = cameraController,
            onCaptureClick = onCaptureClick
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CameraScreenPreview() {
    PyeonKingTheme {
        CameraScreen(
            cameraController = LifecycleCameraController(LocalContext.current),
            capturedImagePath = null,
            onCaptureClick = {},
            onRetakeClick = {},
            onSearchClick = {}
        )
    }
}