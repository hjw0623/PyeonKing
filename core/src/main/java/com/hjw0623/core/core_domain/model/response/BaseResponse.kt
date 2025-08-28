package com.hjw0623.core.core_domain.model.response

data class BaseResponse<T>(
    val data: T,
    val message: String,
)