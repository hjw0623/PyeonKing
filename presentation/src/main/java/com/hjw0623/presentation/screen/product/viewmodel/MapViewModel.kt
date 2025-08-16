package com.hjw0623.presentation.screen.product.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.hjw0623.core.BuildConfig
import com.hjw0623.core.business_logic.model.response.PoiInfo
import com.hjw0623.core.business_logic.repository.KakaoRepository
import com.hjw0623.core.constants.Error
import com.hjw0623.core.location.AndroidLocationObserver
import com.hjw0623.core.presentation.ui.getBrandQuery
import com.hjw0623.presentation.screen.product.ui.map.MapTabState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MapViewModel(
    private val locationObserver: AndroidLocationObserver,
    private val kakaoRepository: KakaoRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MapTabState())
    val state = _state.asStateFlow()

    fun selectedPoi(poi: PoiInfo) {
        _state.update { it.copy(selectedPoi = poi) }
    }

    fun fetchCurrentLocation() {
        viewModelScope.launch {
            _state.update { it.copy(isFetchingLocation = true, error = null) }
            try {
                val currentLocation = locationObserver.observeLocation(5000).first()
                _state.update {
                    it.copy(
                        currentLocation = LatLng(
                            currentLocation.latitude,
                            currentLocation.longitude
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
            try {
                val result = kakaoRepository.searchKeyword(
                    apiKey = BuildConfig.KAKAO_API_KEY,
                    query = getBrandQuery(brand),
                    longitude = loc.longitude.toString(),
                    latitude = loc.latitude.toString(),
                    radius = radius
                )
                _state.update {
                    it.copy(
                        poiList = result,
                        isSearching = false,
                        hasSearched = true,
                        error = null
                    )
                }
            } catch (e: Exception) {
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