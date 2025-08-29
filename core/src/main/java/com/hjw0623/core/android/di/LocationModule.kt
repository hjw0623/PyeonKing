package com.hjw0623.core.android.di

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {

    @Provides
    @Singleton
    fun provideLocationObserver(
        @ApplicationContext context: Context
    ): LocationObserver = AndroidLocationObserver(context)
}