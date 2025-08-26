package com.hjw0623.core.business_logic.location

import kotlinx.coroutines.flow.Flow

interface LocationObserver {
    fun observeLocation(interval: Long): Flow<GeoPoint>
}