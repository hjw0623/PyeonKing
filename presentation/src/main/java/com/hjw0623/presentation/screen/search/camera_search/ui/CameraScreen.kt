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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hjw0623.core.domain.model.search.SearchResultNavArgs
import com.hjw0623.core.ui.designsystem.components.showToast
import com.hjw0623.core.ui.designsystem.theme.PyeonKingTheme
import com.hjw0623.core.ui.util.ObserveAsEvents
import com.hjw0623.core.ui.util.hasCameraPermission
import com.hjw0623.core.ui.util.rememberThrottledOnClick
import com.hjw0623.presentation.R
import com.hjw0623.presentation.screen.search.camera_search.ui.component.CapturedImageScreen
import com.hjw0623.presentation.screen.search.camera_search.ui.component.NoPermissionScreen
import com.hjw0623.presentation.screen.search.camera_search.ui.component.TackingPictureScreen
import com.hjw0623.presentation.screen.search.viewmodel.CameraSearchViewModel

@Composable
fun CameraScreenRoot(
    modifier: Modifier = Modifier,
    cameraSearchViewModel: CameraSearchViewModel = hiltViewModel(),
    onNavigateToSearchResult: (SearchResultNavArgs) -> Unit
) {
    val context = LocalContext.current
    val state by cameraSearchViewModel.state.collectAsStateWithLifecycle()

    val cameraController = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(CameraController.IMAGE_CAPTURE)
        }
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        cameraSearchViewModel.onPermissionResult(granted)
        if (!granted) {
            showToast(context, context.getString(R.string.camera_permission_required))
        }
    }

    val throttledRetakeClick = rememberThrottledOnClick(
        onClick = cameraSearchViewModel::onRetakeClick
    )
    val throttledSearchClick = rememberThrottledOnClick(
        onClick = cameraSearchViewModel::onSearchClick
    )
    val throttledTakePicture = rememberThrottledOnClick {
        cameraSearchViewModel.takePicture(context, cameraController)
    }

    LaunchedEffect(Unit) {
        val isGranted = context.hasCameraPermission()
        cameraSearchViewModel.onPermissionResult(isGranted)
        if (!isGranted) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    ObserveAsEvents(flow = cameraSearchViewModel.event) { event ->
        when (event) {
            is CameraScreenEvent.NavigateToSearchResult -> {
                onNavigateToSearchResult(event.searchResultNavArgs)
            }

            is CameraScreenEvent.Error -> {
                showToast(context, event.error)
            }
        }
    }

    if (state.hasCameraPermission) {
        CameraScreen(
            modifier = modifier,
            state = state,
            cameraController = cameraController,
            onCaptureClick = throttledTakePicture,
            onRetakeClick = throttledRetakeClick,
            onSearchClick = throttledSearchClick
        )
    } else {
        NoPermissionScreen(
            modifier = modifier,
            permissionString = context.getString(R.string.camera_permission_required),
            onRequestPermission = {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }
        )
    }
}


@Composable
fun CameraScreen(
    cameraController: LifecycleCameraController,
    state: CameraScreenState,
    onCaptureClick: () -> Unit,
    onRetakeClick: () -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (state.hasCapturedImage) {
        CapturedImageScreen(
            modifier = modifier.fillMaxSize(),
            imagePath = state.capturedImagePath!!,
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
            onCaptureClick = {},
            onRetakeClick = {},
            onSearchClick = {},
            state = CameraScreenState(),
        )
    }
}