package com.hjw0623.core.location

import com.hjw0623.core.domain.model.PoiInfo

interface KakaoRepository {
    suspend fun searchKeyword(
        apiKey: String,
        query: String,
        longitude: String,
        latitude: String,
        radius: Int = 1000
    ): List<PoiInfo>
}