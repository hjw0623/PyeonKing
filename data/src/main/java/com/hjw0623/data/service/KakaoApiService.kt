package com.hjw0623.data.service

import com.hjw0623.data.model.PoiResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoApiService {

    @GET("v2/local/search/keyword.json")
    suspend fun searchKeyword(
        @Header("Authorization") apiKey: String,
        @Query("query") query: String,
        @Query("x") x: String,             // 경도
        @Query("y") y: String,             // 위도
        @Query("radius") radius: Int
    ): PoiResponse
}