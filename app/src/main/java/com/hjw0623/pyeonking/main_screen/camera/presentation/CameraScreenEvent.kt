package com.hjw0623.pyeonking.main_screen.camera.presentation

import com.hjw0623.pyeonking.core.data.SearchResultNavArgs

sealed interface CameraScreenEvent {
    data class Error(val error: String) : CameraScreenEvent
    data class NavigateToSearchResult(val searchResultNavArgs: SearchResultNavArgs) : CameraScreenEvent
}