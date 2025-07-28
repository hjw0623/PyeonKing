package com.hjw0623.core.domain.model

data class BaseResponse<T>(
    val data: T,
    val message: String,
)

