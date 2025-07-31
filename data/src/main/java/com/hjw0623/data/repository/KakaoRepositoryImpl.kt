package com.hjw0623.data.repository

import com.hjw0623.core.domain.model.PoiInfo
import com.hjw0623.core.location.KakaoRepository
import com.hjw0623.data.model.mapper.toPoiInfoModelList
import com.hjw0623.data.remote.KakaoApiClient

class KakaoRepositoryImpl: KakaoRepository{

    val apiClient = KakaoApiClient.kakaoApi

    override suspend fun searchKeyword(
        apiKey: String,
        query: String,
        longitude: String,
        latitude: String,
        radius: Int
    ): List<PoiInfo> {
        val response = apiClient.searchKeyword(apiKey, query, longitude, latitude, radius)
        return response.documents.toPoiInfoModelList()
    }
}