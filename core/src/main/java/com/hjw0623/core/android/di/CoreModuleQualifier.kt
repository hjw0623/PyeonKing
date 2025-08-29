package com.hjw0623.core.android.di

import kotlin.annotation.AnnotationRetention.RUNTIME

import javax.inject.Qualifier
@Qualifier
@Retention(RUNTIME)
annotation class IoDispatcher

@Qualifier
@Retention(RUNTIME)
annotation class ApplicationScope