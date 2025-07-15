package com.hjw0623.core.data.model

data class BaseResponse<T>(
    val data: T,
    val message: String,
)

