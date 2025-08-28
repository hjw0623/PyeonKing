package com.hjw0623.core.core_network.network

sealed class DataResourceResult<out T> {
    data object DummyConstructor : DataResourceResult<Nothing>()
    data object Loading : DataResourceResult<Nothing>()
    data class Success<out T>(
        val data: T
    ) : DataResourceResult<T>()
    data class Failure(
        val exception: Throwable
    ): DataResourceResult<Nothing>()
}