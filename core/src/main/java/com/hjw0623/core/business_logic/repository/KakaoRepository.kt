package com.hjw0623.core.business_logic.repository

import com.hjw0623.core.business_logic.model.response.PoiInfo

interface KakaoRepository {
    suspend fun searchKeyword(
        query: String,
        longitude: String,
        latitude: String,
        radius: Int = 1000
    ): List<PoiInfo>
}