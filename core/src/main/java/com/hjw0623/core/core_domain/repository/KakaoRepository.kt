package com.hjw0623.core.core_domain.repository

import com.hjw0623.core.core_network.response.PoiInfo

interface KakaoRepository {
    suspend fun searchKeyword(
        query: String,
        longitude: String,
        latitude: String,
        radius: Int = 1000
    ): List<PoiInfo>
}