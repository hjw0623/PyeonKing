package com.hjw0623.data.di

import com.hjw0623.data.service.KakaoApiService
import com.hjw0623.data.service.PyeonKingApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Provides
    @Singleton
    fun providePyeonKingApiService(
        @PyeonKingRetrofit retrofit: Retrofit
    ): PyeonKingApiService =
        retrofit.create(PyeonKingApiService::class.java)

    @Provides
    @Singleton
    fun provideKakaoApiService(
        @KakaoRetrofit retrofit: Retrofit
    ): KakaoApiService =
        retrofit.create(KakaoApiService::class.java)
}