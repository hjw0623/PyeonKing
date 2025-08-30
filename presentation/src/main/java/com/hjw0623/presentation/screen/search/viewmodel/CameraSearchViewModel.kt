package com.hjw0623.presentation.screen.search.viewmodel

import android.content.Context
import androidx.camera.view.LifecycleCameraController
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjw0623.core.android.util.takePictureAndSave
import com.hjw0623.core.constants.error.ErrorMessage
import com.hjw0623.core.domain.model.search.SearchResultNavArgs
import com.hjw0623.core.domain.model.search.SearchResultSource
import com.hjw0623.presentation.screen.search.camera_search.ui.CameraScreenEvent
import com.hjw0623.presentation.screen.search.camera_search.ui.CameraScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraSearchViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(CameraScreenState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<CameraScreenEvent>()
    val event = _event.asSharedFlow()

    fun onPermissionResult(isGranted: Boolean) {
        _state.update { it.copy(hasCameraPermission = isGranted) }
    }


    fun takePicture(context: Context, controller: LifecycleCameraController) {
        if (_state.value.isTakingPicture) return

        viewModelScope.launch {
            _state.update { it.copy(isTakingPicture = true) }

            takePictureAndSave(context, controller)
                .catch { e ->
                    _state.update { s -> s.copy(isTakingPicture = false) }
                    _event.emit(
                        CameraScreenEvent.Error(
                            ErrorMessage.ERROR_IMAGE_SAVE_FAILED + (e.message ?: "")
                        )
                    )
                }
                .collect { path ->
                    _state.update { it.copy(capturedImagePath = path, isTakingPicture = false) }
                }
        }
    }

    fun onRetakeClick() {
        if (_state.value.isTakingPicture) return
        _state.update { it.copy(capturedImagePath = null) }
    }

    fun onSearchClick() {
        val currentState = _state.value
        viewModelScope.launch {
            if (currentState.capturedImagePath != null) {
                val navArgs = SearchResultNavArgs(
                    source = SearchResultSource.CAMERA,
                    passedQuery = "",
                    passedImagePath = currentState.capturedImagePath
                )
                _event.emit(CameraScreenEvent.NavigateToSearchResult(navArgs))
            } else {
                _event.emit(CameraScreenEvent.Error(ErrorMessage.ERROR_IMAGE_NONE))
            }
        }
    }
}