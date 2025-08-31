package com.hjw0623.core.ui.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat

private fun Context.hasPermission(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}

fun Context.hasCameraPermission(): Boolean {
    return hasPermission(Manifest.permission.CAMERA)
}
fun ComponentActivity.shouldShowLocationPermissionRationale(): Boolean {
    return  shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
}
fun Context.hasLocationPermission(): Boolean {
    return hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)
}

