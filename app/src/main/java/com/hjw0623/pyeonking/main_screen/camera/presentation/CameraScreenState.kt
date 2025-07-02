package com.hjw0623.pyeonking.main_screen.camera.presentation

data class CameraScreenState(
    val hasCameraPermission: Boolean = false,
    val capturedImagePath: String? = null
)
