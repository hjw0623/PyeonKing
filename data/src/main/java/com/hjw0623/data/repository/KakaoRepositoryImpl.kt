package com.hjw0623.data.repository

import com.hjw0623.core.network.response.PoiInfo
import com.hjw0623.core.domain.repository.KakaoRepository
import com.hjw0623.data.di.KakaoAuthHeader
import com.hjw0623.data.model.mapper.toPoiInfoModelList
import com.hjw0623.data.service.KakaoApiService
import javax.inject.Inject

class KakaoRepositoryImpl @Inject constructor(
    private val kakaoApi: KakaoApiService,
    @KakaoAuthHeader private val kakaoAuthHeader: String
) : KakaoRepository {

    override suspend fun searchKeyword(
        query: String,
        longitude: String,
        latitude: String,
        radius: Int
    ): List<PoiInfo> {
        val response = kakaoApi.searchKeyword(
            apiKey = kakaoAuthHeader,
            query = query,
            x = longitude,
            y = latitude,
            radius = radius
        )
        return response.documents.toPoiInfoModelList()
    }
}