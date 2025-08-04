package com.hjw0623.presentation.screen.search.viewmodel

import android.content.Context
import androidx.camera.view.LifecycleCameraController
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjw0623.core.business_logic.model.search.search_result.SearchResultNavArgs
import com.hjw0623.core.business_logic.model.search.search_result.SearchResultSource
import com.hjw0623.core.constants.Error
import com.hjw0623.core.util.takePictureAndSave
import com.hjw0623.presentation.screen.search.camera_search.ui.CameraScreenEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CameraSearchViewModel : ViewModel() {

    private val _hasCameraPermission = MutableStateFlow(false)
    val hasCameraPermission = _hasCameraPermission.asStateFlow()

    private val _capturedImagePath = MutableStateFlow<String?>(null)
    val capturedImagePath = _capturedImagePath.asStateFlow()

    private val _event = MutableSharedFlow<CameraScreenEvent>()
    val event = _event.asSharedFlow()

    fun onPermissionResult(isGranted: Boolean) {
        _hasCameraPermission.value = isGranted
    }

    fun onPhotoTaken(path: String) {
        _capturedImagePath.value = path
    }

    fun takePicture(context: Context, controller: LifecycleCameraController) {
        viewModelScope.launch {
            takePictureAndSave(context, controller)
                .catch { e ->
                    _event.emit(CameraScreenEvent.Error(Error.SAVE_PICTURE_ERROR + e.message))
                }
                .collect { path ->
                    _capturedImagePath.value = path
                }
        }
    }

    fun onRetakeClick() {
        _capturedImagePath.value = null
    }

    fun onSearchClick() {
        viewModelScope.launch {
            val path = _capturedImagePath.value
            if (path != null) {
                val navArgs = SearchResultNavArgs(
                    source = SearchResultSource.CAMERA,
                    passedQuery = "",
                    passedImagePath = path
                )
                _event.emit(CameraScreenEvent.NavigateToSearchResult(navArgs))
            } else {
                _event.emit(CameraScreenEvent.Error(Error.NO_IMAGE))
            }
        }
    }
}