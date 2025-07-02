package com.hjw0623.pyeonking.main_screen.camera.presentation

sealed interface CameraScreenAction {
    data object OnCaptureClick : CameraScreenAction
    data object OnRequestCameraPermission : CameraScreenAction
    data object OnSearchClick : CameraScreenAction
    data object OnRetakeClick : CameraScreenAction
}