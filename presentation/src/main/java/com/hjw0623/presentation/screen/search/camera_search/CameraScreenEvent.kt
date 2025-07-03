package com.hjw0623.presentation.screen.search.camera_search

import com.hjw0623.core.domain.search.search_result.SearchResultNavArgs

sealed interface CameraScreenEvent {
    data class Error(val error: String) : CameraScreenEvent
    data class NavigateToSearchResult(val searchResultNavArgs: SearchResultNavArgs) : CameraScreenEvent
}