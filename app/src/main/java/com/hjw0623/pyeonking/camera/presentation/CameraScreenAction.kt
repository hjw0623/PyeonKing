package com.hjw0623.pyeonking.camera.presentation

sealed interface CameraScreenAction {
    data object OnCaptureClick : CameraScreenAction
    data object OnRequestCameraPermission : CameraScreenAction
    data object OnBackClick : CameraScreenAction
    data object OnSearchClick : CameraScreenAction
    data object OnRetakeClick : CameraScreenAction
}