package com.hjw0623.data.di

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.BINARY

@Qualifier
@Retention(BINARY)
annotation class AuthOkHttp

@Qualifier
@Retention(BINARY)
annotation class PlainOkHttp

@Qualifier
@Retention(BINARY)
annotation class PyeonKingRetrofit

@Qualifier
@Retention(BINARY)
annotation class KakaoRetrofit

@Qualifier
@Retention(BINARY)
annotation class KakaoAuthHeader