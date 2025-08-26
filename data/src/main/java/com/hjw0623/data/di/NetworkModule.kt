package com.hjw0623.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Singleton
    fun provideAuthReplacingInterceptor(
        tokenProvider: TokenProvider
    ): Interceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val required = originalRequest.header("Authorization") == "required"

        if (!required) {
            return@Interceptor chain.proceed(originalRequest)
        }

        val token = tokenProvider.getToken()
        val newRequest = originalRequest.newBuilder()
            .removeHeader("Authorization")
            .apply {
                if (!token.isNullOrBlank()) {
                    header("Authorization", "Bearer $token")
                }
            }
            .build()

        chain.proceed(newRequest)
    }

    @Provides
    @Singleton
    @AuthOkHttp
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
    @PlainOkHttp
    fun providePlainOkHttpClient(
        logging: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
}