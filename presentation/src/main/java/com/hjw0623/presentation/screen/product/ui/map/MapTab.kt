package com.hjw0623.presentation.screen.product.ui.map

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.hjw0623.core.ui.util.getBrandQuery
import com.hjw0623.presentation.R
import com.hjw0623.presentation.screen.product.ui.map.component.EmptyResultOverlay
import com.hjw0623.presentation.screen.product.ui.map.component.ErrorOverlay
import com.hjw0623.presentation.screen.product.ui.map.component.MapTitleOverlay
import com.hjw0623.presentation.screen.product.ui.map.component.PermissionOverlay
import com.hjw0623.presentation.screen.product.viewmodel.MapViewModel
import com.hjw0623.core.android.util.findActivity
import com.hjw0623.presentation.util.getMarkerIconByBrand
import kotlinx.coroutines.launch


@Composable
fun MapTab(
    modifier: Modifier = Modifier,
    brandName: String
) {
    val context = LocalContext.current
    val mapViewModel: MapViewModel = hiltViewModel()

    val state by mapViewModel.state.collectAsStateWithLifecycle()
    val cameraPositionState = rememberCameraPositionState()
    val scope = rememberCoroutineScope()

    fun hasLocationPermission(): Boolean {
        val fine = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val coarse = ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        return fine || coarse
    }

    val activity = remember(context) { context.findActivity() }

    val settingsLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (hasLocationPermission()) {
            mapViewModel.updatePermissionState(denied = false, permanentlyDenied = false)
            mapViewModel.fetchCurrentLocation()
        } else {
            mapViewModel.updatePermissionState(denied = true, permanentlyDenied = true)
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.values.any { it }
        if (granted) {
            mapViewModel.updatePermissionState(denied = false, permanentlyDenied = false)
            mapViewModel.fetchCurrentLocation()
        } else {
            val permanentlyDenied =
                activity != null &&
                        !ActivityCompat.shouldShowRequestPermissionRationale(
                            activity, Manifest.permission.ACCESS_FINE_LOCATION
                        ) &&
                        !ActivityCompat.shouldShowRequestPermissionRationale(
                            activity, Manifest.permission.ACCESS_COARSE_LOCATION
                        )
            mapViewModel.updatePermissionState(denied = true, permanentlyDenied = permanentlyDenied)
        }
    }

    LaunchedEffect(Unit) {
        if (hasLocationPermission()) {
            mapViewModel.updatePermissionState(denied = false, permanentlyDenied = false)
            mapViewModel.fetchCurrentLocation()
        } else {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    LaunchedEffect(state.currentLocation, brandName) {
        val currentLocation = state.currentLocation ?: return@LaunchedEffect
        if (!state.hasSearched) {
            mapViewModel.searchNearbyPlaces(brandName)
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(currentLocation.latitude, currentLocation.longitude),
                    15f
                )
            )
        }
    }

    var mapReady by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(600.dp),
        contentAlignment = Alignment.Center
    ) {
        if (state.permissionDenied) {
            PermissionOverlay(
                permanentlyDenied = state.permanentlyDenied,
                onRequest = {
                    permissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                },
                onOpenSettings = {
                    val intent = Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", context.packageName, null)
                    )
                    settingsLauncher.launch(intent)
                }
            )
            return@Box
        }

        if (state.currentLocation == null || state.isFetchingLocation) {
            CircularProgressIndicator()
            return@Box
        }

        val brandNameKr = getBrandQuery(brandName)

        GoogleMap(
            modifier = Modifier.matchParentSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(isMyLocationEnabled = hasLocationPermission()),
            onMapLoaded = { mapReady = true }
        ) {
            val markerIcon = remember(brandName, mapReady) {
                if (mapReady) getMarkerIconByBrand(context, brandName) else null
            }

            state.poiList.forEach { poi ->
                val pos = LatLng(poi.latitude, poi.longitude)
                Marker(
                    state = MarkerState(pos),
                    title = poi.placeName,
                    snippet = poi.addressName,
                    icon = markerIcon,
                    onClick = {
                        mapViewModel.selectedPoi(poi)
                        scope.launch {
                            cameraPositionState.animate(
                                CameraUpdateFactory.newLatLngZoom(pos, 15f)
                            )
                        }
                        false
                    }
                )
            }
        }

        if (state.error != null && !state.isSearching) {
            ErrorOverlay(
                message = state.error!!,
                onRetry = { mapViewModel.searchNearbyPlaces(brandName) }
            )
        } else if (state.hasSearched && !state.isSearching && state.poiList.isEmpty()) {
            EmptyResultOverlay(
                brandName = brandName,
                onRetry = { mapViewModel.searchNearbyPlaces(brandName) }
            )
        }

        if (state.isSearching) {
            Box(
                Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
            ) {
                CircularProgressIndicator()
            }
        }

        MapTitleOverlay(
            title = stringResource(R.string.map_title_nearby, brandNameKr),
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(12.dp)
        )
    }
}