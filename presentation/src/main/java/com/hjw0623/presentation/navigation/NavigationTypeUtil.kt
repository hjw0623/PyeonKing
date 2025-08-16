package com.hjw0623.presentation.navigation

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.net.Uri
import androidx.navigation.NavType
import kotlinx.serialization.json.Json


inline fun <reified T : Parcelable> parcelableType(
    isNullableAllowed: Boolean = false,
    json: Json = Json
): NavType<T> = object : NavType<T>(isNullableAllowed) {

    override fun get(bundle: Bundle, key: String): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, T::class.java)
        } else {
            @Suppress("DEPRECATION")
            bundle.getParcelable(key)
        }
    }

    override fun parseValue(value: String): T {
        return json.decodeFromString(Uri.decode(value))
    }

    override fun serializeAsValue(value: T): String {
        return Uri.encode(json.encodeToString(value))
    }

    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putParcelable(key, value)
    }
}


