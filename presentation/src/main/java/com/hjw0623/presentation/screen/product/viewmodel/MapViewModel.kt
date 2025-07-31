package com.hjw0623.presentation.screen.product.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.hjw0623.core.BuildConfig
import com.hjw0623.core.domain.model.PoiInfo
import com.hjw0623.core.location.AndroidLocationObserver
import com.hjw0623.core.location.KakaoRepository
import com.hjw0623.core.presentation.ui.getBrandQuery
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MapViewModel(
    private val locationObserver: AndroidLocationObserver,
    private val kakaoRepository: KakaoRepository
) : ViewModel() {
    private val _poiList = MutableStateFlow<List<PoiInfo>>(emptyList())
    val poiList = _poiList.asStateFlow()

    private val _selectedPoi = MutableStateFlow<PoiInfo?>(null)
    val selectedPoi = _selectedPoi.asStateFlow()

    private val _currentLocation = MutableStateFlow<LatLng?>(null)
    val currentLocation = _currentLocation.asStateFlow()

    fun selectedPoi(poi: PoiInfo) {
        _selectedPoi.value = poi
    }

    fun fetchCurrentLocation() {
        viewModelScope.launch {
            locationObserver.observeLocation(5000).collectLatest { location ->
                _currentLocation.value = LatLng(location.latitude, location.longitude)
            }
        }
    }

    fun searchNearbyPlaces(brand: String, radius: Int = 1000) {
        viewModelScope.launch {
            val location = currentLocation.value

            val lat = location?.latitude?.toString()
            val lon = location?.longitude?.toString()
            val brandName = getBrandQuery(brand)

            if (lat != null && lon != null) {
                val result = kakaoRepository.searchKeyword(
                    apiKey = BuildConfig.KAKAO_API_KEY,
                    query = brandName,
                    longitude = lon,
                    latitude = lat,
                    radius = radius
                )
                _poiList.value = result
            }

        }
    }
}