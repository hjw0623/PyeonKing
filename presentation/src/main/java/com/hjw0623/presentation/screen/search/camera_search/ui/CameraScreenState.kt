package com.hjw0623.presentation.screen.search.camera_search.ui

data class CameraScreenState(
    val hasCameraPermission: Boolean = false,
    val capturedImagePath: String? = null,
    val isTakingPicture: Boolean = false,
) {
    val hasCapturedImage: Boolean get() = capturedImagePath != null
}