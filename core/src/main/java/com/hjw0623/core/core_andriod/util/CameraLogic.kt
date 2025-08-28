package com.hjw0623.core.core_andriod.util

import android.content.Context
import android.os.Environment
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.io.File

fun takePictureAndSave(
    context: Context,
    controller: LifecycleCameraController,
): Flow<String> = callbackFlow {
    val outputDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val fileName = "IMG_${System.currentTimeMillis()}.jpg"
    val photoFile = File(outputDirectory, fileName)

    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    controller.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                trySend(photoFile.absolutePath).isSuccess
                close()
            }

            override fun onError(exception: ImageCaptureException) {
                close(exception)
            }
        }
    )

    awaitClose {
        // 현재는 별도 정리할 리소스 없음. 필요 시 추후 추가
    }
}