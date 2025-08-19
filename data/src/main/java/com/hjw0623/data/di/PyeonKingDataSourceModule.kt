package com.hjw0623.data.di

import com.hjw0623.core.business_logic.repository.UserDataStoreRepository
import com.hjw0623.core.constants.Api
import com.hjw0623.data.service.KakaoApiService
import com.hjw0623.data.service.PyeonKingApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PyeonKingDataSourceModule {

    // -------- Moshi --------
    @Provides
    @Singleton
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    // -------- Interceptors --------
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Singleton
    fun provideAuthReplacingInterceptor(
        userStore: UserDataStoreRepository
    ): Interceptor = Interceptor { chain ->
        val originalRequst = chain.request()
        val required = originalRequst.header("Authorization") == "required"

        if (!required) {
            return@Interceptor chain.proceed(originalRequst)
        }

        val token = runBlocking { userStore.accessTokenFlow.firstOrNull() }
        val newReq = originalRequst.newBuilder()
            .removeHeader("Authorization")
            .apply {
                if (!token.isNullOrBlank()) {
                    header("Authorization", "Bearer $token")
                }
            }
            .build()

        chain.proceed(newReq)
    }

    // -------- OkHttpClient (with / without auth) --------
    @Provides
    @Singleton
    @Named("okhttp-auth")
    fun provideAuthOkHttpClient(
        authReplacingInterceptor: Interceptor,
        logging: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(authReplacingInterceptor)
            .addInterceptor(logging)
            .build()

    @Provides
    @Singleton
    @Named("okhttp-plain")
    fun providePlainOkHttpClient(
        logging: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

    // -------- Retrofit(s) --------
    @Provides
    @Singleton
    @Named("retrofit-pyeonking")
    fun providePyeonKingRetrofit(
        @Named("okhttp-auth") client: OkHttpClient,
        moshi: Moshi
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(Api.BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    @Provides
    @Singleton
    @Named("retrofit-kakao")
    fun provideKakaoRetrofit(
        @Named("okhttp-plain") client: OkHttpClient,
        moshi: Moshi
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(Api.KAKAO_BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    // -------- Services --------
    @Provides
    @Singleton
    fun providePyeonKingApiService(
        @Named("retrofit-pyeonking") retrofit: Retrofit
    ): PyeonKingApiService =
        retrofit.create(PyeonKingApiService::class.java)

    @Provides
    @Singleton
    fun provideKakaoApiService(
        @Named("retrofit-kakao") retrofit: Retrofit
    ): KakaoApiService =
        retrofit.create(KakaoApiService::class.java)
}