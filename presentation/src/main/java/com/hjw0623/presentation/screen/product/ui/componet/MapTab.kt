package com.hjw0623.presentation.screen.product.ui.componet

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.hjw0623.presentation.R
import com.hjw0623.presentation.screen.factory.MapViewModelFactory
import com.hjw0623.presentation.screen.product.viewmodel.MapViewModel
import com.hjw0623.presentation.util.getMarkerIconByBrand

@Composable
fun MapTab(
    modifier: Modifier = Modifier,
    brandName: String
) {
    val context = LocalContext.current
    val mapViewModel: MapViewModel = viewModel(factory = MapViewModelFactory(context))

    val poiList by mapViewModel.poiList.collectAsStateWithLifecycle()
    val selectedPoi by mapViewModel.selectedPoi.collectAsStateWithLifecycle()
    val currentLocation by mapViewModel.currentLocation.collectAsStateWithLifecycle()


    val cameraPositionState = rememberCameraPositionState()

    /** 권한 요청 런처 */
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.values.any { it }
        if (granted) {
            mapViewModel.fetchCurrentLocation()
            mapViewModel.searchNearbyPlaces(brandName)
        } else {
            Toast.makeText(
                context,
                context.getString(R.string.error_need_location_permission),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    /** 초기 위치 권한 체크 */
    LaunchedEffect(Unit) {
        val hasFineLocation = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasCoarseLocation = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (!hasFineLocation && !hasCoarseLocation) {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        } else {
            mapViewModel.fetchCurrentLocation()
            mapViewModel.searchNearbyPlaces(brandName)
        }
    }

    /** 현재 위치 변경 시 카메라 이동 + 장소 재검색 */
    LaunchedEffect(currentLocation) {
        currentLocation?.let { location ->
            mapViewModel.searchNearbyPlaces(brandName)
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(location.latitude, location.longitude),
                    15f
                )
            )
        }
    }

    /** 지도 또는 로딩 상태 */
    if (currentLocation != null) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(600.dp)
        ) {
            GoogleMap(
                modifier = Modifier.matchParentSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(isMyLocationEnabled = true)
            ) {
                poiList.forEach { poi ->
                    Marker(
                        state = MarkerState(LatLng(poi.latitude, poi.longitude)),
                        title = poi.placeName,
                        icon = getMarkerIconByBrand(context, brandName),
                        onClick = {
                            mapViewModel.selectedPoi(poi)
                            cameraPositionState.position =
                                CameraPosition.fromLatLngZoom(
                                    LatLng(poi.latitude, poi.longitude),
                                    15f
                                )
                            false
                        }
                    )
                }
            }
        }
    } else {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(600.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}