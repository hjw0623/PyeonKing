package com.hjw0623.pyeonking

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.memory.MemoryCache
import com.hjw0623.core.business_logic.auth.AuthManager
import com.hjw0623.core.business_logic.repository.UserDataStoreRepository
import com.hjw0623.data.repository.UserDataStoreRepositoryImpl
import timber.log.Timber

const val COIL_MEMORY_CACHE_SIZE_PERCENT = 0.25
const val COIL_DISK_CACHE_DIR_NAME = "coil_file_cache"
const val COIL_DISK_CACHE_MAX_SIZE = 1024 * 1024 * 50

class PyeonKingApplication: Application(), SingletonImageLoader.Factory {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        pyeonKingApplication = this

        val userDataStoreRepository: UserDataStoreRepository = UserDataStoreRepositoryImpl(applicationContext)
        AuthManager.initialize(userDataStoreRepository)

    }
    companion object {
        private lateinit var pyeonKingApplication: PyeonKingApplication
        fun getPyeonKingApplication() = pyeonKingApplication
    }
    override fun newImageLoader(context: PlatformContext): ImageLoader = ImageLoader.Builder(context)
        .memoryCache {
            MemoryCache.Builder()
                .maxSizePercent(context, COIL_MEMORY_CACHE_SIZE_PERCENT)
                .build()
        }
        .diskCache {
            DiskCache.Builder()
                .directory(filesDir.resolve(COIL_DISK_CACHE_DIR_NAME))
                .maximumMaxSizeBytes(COIL_DISK_CACHE_MAX_SIZE.toLong())
                .build()
        }.build()
}

