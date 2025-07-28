package com.hjw0623.data.remote

import android.util.Log
import com.hjw0623.core.constants.Api
import com.hjw0623.core.domain.AuthManager
import com.hjw0623.data.service.PyeonKingApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object PyeonKingApiClient {
    private const val BASE_URL = Api.BASE_URL

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val authInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()

        val authHeader = originalRequest.header("Authorization")

        if (authHeader == "required") {
            val token = AuthManager.accessToken
            Log.d("accessToken", token)
            val newRequest = originalRequest.newBuilder()
                .removeHeader("Authorization")
                .header("Authorization", "Bearer $token")
                .build()
            chain.proceed(newRequest)
        } else {
            chain.proceed(originalRequest)
        }
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()
    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }
    val pyeonKingApiService: PyeonKingApiService by lazy {
        retrofit.create(PyeonKingApiService::class.java)
    }
}
