package com.hjw0623.presentation.screen.search.camera_search

data class CameraScreenState(
    val hasCameraPermission: Boolean = false,
    val capturedImagePath: String? = null
)
