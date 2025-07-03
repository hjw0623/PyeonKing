package com.hjw0623.presentation.screen.search.camera_search

sealed interface CameraScreenAction {
    data object OnCaptureClick : CameraScreenAction
    data object OnRequestCameraPermission : CameraScreenAction
    data object OnSearchClick : CameraScreenAction
    data object OnRetakeClick : CameraScreenAction
}