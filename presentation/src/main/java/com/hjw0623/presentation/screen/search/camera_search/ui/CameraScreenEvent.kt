package com.hjw0623.presentation.screen.search.camera_search.ui

import com.hjw0623.core.business_logic.model.search.search_result.SearchResultNavArgs

sealed interface CameraScreenEvent {
    data class Error(val error: String) : CameraScreenEvent
    data class NavigateToSearchResult(val searchResultNavArgs: SearchResultNavArgs) : CameraScreenEvent
}