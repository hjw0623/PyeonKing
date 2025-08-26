package com.hjw0623.presentation.di

import android.content.Context
import com.hjw0623.core.business_logic.location.LocationObserver
import com.hjw0623.presentation.location.AndroidLocationObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {

    @Provides
    @Singleton
    fun provideLocationObserver(
        @ApplicationContext context: Context
    ): LocationObserver = AndroidLocationObserver(context)
}