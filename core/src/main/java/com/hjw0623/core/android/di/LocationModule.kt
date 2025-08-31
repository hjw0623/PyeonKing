package com.hjw0623.core.android.di

import android.content.Context
import com.hjw0623.core.android.location.AndroidLocationObserver
import com.hjw0623.core.domain.location.LocationObserver
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