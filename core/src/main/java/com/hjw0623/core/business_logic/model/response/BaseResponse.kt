package com.hjw0623.core.business_logic.model.response

data class BaseResponse<T>(
    val data: T,
    val message: String,
)