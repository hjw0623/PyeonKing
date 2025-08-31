package com.hjw0623.core.network.common

data class BaseResponse<T>(
    val data: T,
    val message: String,
)