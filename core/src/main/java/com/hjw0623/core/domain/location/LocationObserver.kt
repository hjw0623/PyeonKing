package com.hjw0623.core.domain.location

import kotlinx.coroutines.flow.Flow

interface LocationObserver {
    fun observeLocation(interval: Long): Flow<GeoPoint>
}