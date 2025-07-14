package com.hjw0623.core.domain.search.camera_search

import android.content.Context
import android.os.Environment
import android.widget.Toast
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import com.hjw0623.core.R
import com.hjw0623.core.presentation.designsystem.components.showToast

import java.io.File

fun takePictureAndSave(
    context: Context,
    controller: LifecycleCameraController,
    onImageSaved: (String) -> Unit,
) {
    val outputDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val fileName = "IMG_${System.currentTimeMillis()}.jpg"
    val photoFile = File(outputDirectory, fileName)

    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    controller.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                onImageSaved(photoFile.absolutePath)
            }

            override fun onError(exception: ImageCaptureException) {
                showToast(
                    context,
                    context.getString(R.string.error_save_photo_failed, exception.message),
                    Toast.LENGTH_LONG
                )
            }
        })
}