package com.hjw0623.pyeonking.camera.presentation

data class CameraScreenState(
    val hasCameraPermission: Boolean = false,
    val capturedImagePath: String? = null
)
