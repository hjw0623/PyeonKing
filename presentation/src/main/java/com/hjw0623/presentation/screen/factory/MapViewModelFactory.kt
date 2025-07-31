package com.hjw0623.presentation.screen.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hjw0623.core.location.AndroidLocationObserver
import com.hjw0623.data.repository.KakaoRepositoryImpl
import com.hjw0623.presentation.screen.product.viewmodel.MapViewModel

class MapViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MapViewModel(
                locationObserver = AndroidLocationObserver(context.applicationContext),
                kakaoRepository = KakaoRepositoryImpl()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
