package com.hjw0623.core.network.response

data class BaseResponse<T>(
    val data: T,
    val message: String,
)