package com.hjw0623.presentation.screen.product.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hjw0623.core.domain.location.LocationObserver
import com.hjw0623.core.core_network.response.PoiInfo
import com.hjw0623.core.domain.repository.KakaoRepository
import com.hjw0623.core.android.constants.Error
import com.hjw0623.core.ui.util.getBrandQuery
import com.hjw0623.presentation.screen.product.ui.map.MapTabState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject
import com.google.android.gms.maps.model.LatLng as GmsLatLng

@HiltViewModel
class MapViewModel @Inject constructor(
    private val locationObserver: LocationObserver,
    private val kakaoRepository: KakaoRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(MapTabState())
    val state = _state.asStateFlow()

    fun selectedPoi(poi: PoiInfo) {
        _state.update { it.copy(selectedPoi = poi) }
    }

    fun fetchCurrentLocation() {
        if (_state.value.isFetchingLocation || _state.value.currentLocation != null) return

        viewModelScope.launch {
            _state.update { it.copy(isFetchingLocation = true, error = null) }
            try {
                val loc = withTimeoutOrNull(5000) {
                    locationObserver.observeLocation(2000).firstOrNull()
                }
                if (loc == null) {
                    _state.update { it.copy(error = Error.FAILED_TO_GET_LOCATION) }
                    return@launch
                }
                _state.update {
                    it.copy(
                        currentLocation = GmsLatLng(
                            loc.latitude, loc.longitude
                        )
                    )
                }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message ?: Error.FAILED_TO_GET_LOCATION) }
            } finally {
                _state.update { it.copy(isFetchingLocation = false) }
            }
        }
    }

    fun searchNearbyPlaces(brand: String, radius: Int = 1000) {
        viewModelScope.launch {
            val loc = _state.value.currentLocation
            if (loc == null) {
                _state.update { it.copy(hasSearched = true, error = Error.NO_CURRENT_LOCATION) }
                return@launch
            }

            _state.update { it.copy(isSearching = true, error = null) }
            runCatching {
                kakaoRepository.searchKeyword(
                    query = getBrandQuery(brand),
                    longitude = loc.longitude.toString(),
                    latitude = loc.latitude.toString(),
                    radius = radius
                )
            }.onSuccess { result ->
                _state.update { it.copy(poiList = result, isSearching = false, hasSearched = true) }
            }.onFailure { e ->
                _state.update {
                    it.copy(
                        isSearching = false,
                        hasSearched = true,
                        error = e.message ?: Error.SEARCH_FAILED
                    )
                }
            }
        }
    }

    fun updatePermissionState(denied: Boolean, permanentlyDenied: Boolean) {
        _state.update { it.copy(permissionDenied = denied, permanentlyDenied = permanentlyDenied) }
    }
}