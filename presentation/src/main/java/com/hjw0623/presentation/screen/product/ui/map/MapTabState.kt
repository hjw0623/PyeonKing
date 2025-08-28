package com.hjw0623.presentation.screen.product.ui.map

import com.google.android.gms.maps.model.LatLng
import com.hjw0623.core.core_domain.model.response.PoiInfo

data class MapTabState(
    val currentLocation: LatLng? = null,
    val poiList: List<PoiInfo> = emptyList(),
    val selectedPoi: PoiInfo? = null,
    val isFetchingLocation: Boolean = false,
    val isSearching: Boolean = false,
    val permissionDenied: Boolean = false,
    val permanentlyDenied: Boolean = false,
    val error: String? = null,
    val hasSearched: Boolean = false
)