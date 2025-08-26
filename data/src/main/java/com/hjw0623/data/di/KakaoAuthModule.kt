package com.hjw0623.data.di

import com.hjw0623.core.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KakaoAuthModule {

    @Provides
    @Singleton
    @KakaoAuthHeader
    fun provideKakaoAuthHeader(): String = BuildConfig.KAKAO_API_KEY
}